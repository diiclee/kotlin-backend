package com.example.kotlin_backend.dto.request

import com.example.kotlin_backend.entity.enums.TaskStatus
import jakarta.validation.constraints.NotNull

data class ChangeTaskStatusRequest(
    @field:NotNull(message = "Status cannot be empty")
    val status: TaskStatus
)