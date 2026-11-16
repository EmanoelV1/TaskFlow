package com.example.taskflow.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.taskflow.data.NotificationHelper
import com.example.taskflow.data.model.Task
import com.example.taskflow.data.repository.AuthRepository
import com.example.taskflow.data.repository.TaskRepository
import com.example.taskflow.ui.components.AddDialog
import com.example.taskflow.ui.components.DeleteDialog
import com.example.taskflow.ui.components.TaskCard
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    authRepository: AuthRepository,
    taskRepository: TaskRepository,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val currentUser = authRepository.currentUser
    val userName = currentUser?.displayName?.takeIf { it.isNotBlank() }
        ?: currentUser?.email?.substringBefore("@")
        ?: "Usuário"

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {}
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    val tasks by remember(currentUser?.uid) {
        if (currentUser != null) {
            taskRepository.getUserTasks(currentUser.uid)
        } else {
            flowOf(emptyList())
        }
    }.collectAsState(initial = emptyList())

    var taskToDelete by remember { mutableStateOf<Task?>(null) }
    var taskToEdit by remember { mutableStateOf<Task?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Olá, $userName",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        authRepository.logout()
                        onLogout()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Sair"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Nova Tarefa")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            if (tasks.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Nenhuma tarefa cadastrada. Toque em + para começar.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(tasks, key = { it.id }) { task ->
                        TaskCard(
                            task = task,
                            onCheckedChange = { isChecked ->
                                scope.launch {
                                    taskRepository.toggleTaskCompleted(task.id, isChecked)
                                }
                            },
                            onClick = { taskToEdit = task },
                            onDeleteClick = { taskToDelete = task }
                        )
                    }
                }
            }
        }

        taskToDelete?.let { selectedTask ->
            DeleteDialog(
                task = selectedTask,
                onDismiss = { taskToDelete = null },
                onConfirmDelete = {
                    val id = selectedTask.id
                    taskToDelete = null
                    scope.launch {
                        taskRepository.deleteTask(id)
                    }
                }
            )
        }

        if (showAddDialog && currentUser != null) {
            AddDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { title, description, urgency, dueDate ->
                    showAddDialog = false
                    scope.launch {
                        taskRepository.addTask(
                            Task(
                                userId = currentUser.uid,
                                title = title,
                                description = description,
                                urgency = urgency.name,
                                dueDate = dueDate,
                                completed = false
                            )
                        )
                        if (dueDate.isNotBlank()) {
                            NotificationHelper.showTaskReminder(context, title, dueDate)
                        }
                    }
                }
            )
        }

        taskToEdit?.let { editingTask ->
            AddDialog(
                taskToEdit = editingTask,
                onDismiss = { taskToEdit = null },
                onConfirm = { title, description, urgency, dueDate ->
                    taskToEdit = null
                    scope.launch {
                        taskRepository.updateTask(
                            editingTask.copy(
                                title = title,
                                description = description,
                                urgency = urgency.name,
                                dueDate = dueDate
                            )
                        )
                    }
                }
            )
        }
    }
}