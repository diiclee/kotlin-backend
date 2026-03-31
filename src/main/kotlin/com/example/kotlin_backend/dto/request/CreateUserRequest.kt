package com.example.kotlin_backend.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CreateUserRequest(

    @field:NotBlank(message = "Name cannot be empty")
    val name: String,

    @field:NotBlank(message = "Email cannot be empty")
    @field:Email(message = "Email must be valid")
    val email: String
)