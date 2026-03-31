package com.example.kotlin_backend.dto.response

import com.example.kotlin_backend.entity.enums.TaskPriority
import com.example.kotlin_backend.entity.enums.TaskStatus
import java.time.LocalDate

data class TaskResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val status: TaskStatus,
    val priority: TaskPriority,
    val dueDate: LocalDate?,
    val projectId: Long,
    val assignedUserId: Long?,
    val assignedUserName: String?
)