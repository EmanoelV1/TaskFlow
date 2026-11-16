package com.example.taskflow.data.model

import androidx.compose.ui.graphics.Color
import com.example.taskflow.ui.theme.UrgencyHighRed
import com.example.taskflow.ui.theme.UrgencyLowGreen
import com.example.taskflow.ui.theme.UrgencyMediumYellow
import com.google.firebase.firestore.DocumentId

enum class UrgencyLevel(val label: String, val color: Color) {
    BAIXA("Baixa", UrgencyLowGreen),
    MEDIA("Média", UrgencyMediumYellow),
    URGENTE("Urgente", UrgencyHighRed);

    companion object {
        fun fromString(value: String): UrgencyLevel {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: BAIXA
        }
    }
}

data class Task(
    @DocumentId
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val urgency: String = UrgencyLevel.BAIXA.name,
    val dueDate: String = "",
    val completed: Boolean = false
)