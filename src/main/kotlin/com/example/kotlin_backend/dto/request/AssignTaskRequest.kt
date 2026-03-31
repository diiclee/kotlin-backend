package com.example.kotlin_backend.dto.request

import jakarta.validation.constraints.NotNull

data class AssignTaskRequest(
    @field:NotNull(message = "User id cannot be empty")
    val userId: Long
)