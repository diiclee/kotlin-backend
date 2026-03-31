package com.example.kotlin_backend.dto.request

import com.example.kotlin_backend.entity.enums.TaskPriority
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class UpdateTaskRequest(

    @field:NotBlank(message = "There must be a title")
    val title: String,

    val description: String? = null,

    @field:NotNull(message = "Priority must be set")
    val priority: TaskPriority,

    val dueDate: LocalDate? = null
)