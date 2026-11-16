package com.example.taskflow.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.example.taskflow.data.model.Task

@Composable
fun DeleteDialog(
    task: Task,
    onDismiss: () -> Unit,
    onConfirmDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Confirmar exclusão") },
        text = {
            Text(text = "Deseja realmente excluir a tarefa \"${task.title}\"?")
        },
        confirmButton = {
            TextButton(onClick = onConfirmDelete) {
                Text(
                    text = "Excluir",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancelar")
            }
        }
    )
}