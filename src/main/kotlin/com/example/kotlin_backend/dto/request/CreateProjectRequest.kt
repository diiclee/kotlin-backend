package com.example.kotlin_backend.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateProjectRequest(

    @field:NotBlank(message = "There must be a title")
    val title: String,

    val description: String? = null,

    @field:NotNull(message = "There must be an owner. Owner ID should not be empty!")
    val ownerId: Long
)