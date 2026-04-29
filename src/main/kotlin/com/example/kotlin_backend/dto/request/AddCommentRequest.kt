package com.example.kotlin_backend.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AddCommentRequest(

    @field:NotBlank(message = "Content cannot be empty")
    val content: String,

    @field:NotNull(message = "Author id cannot be empty")
    val authorId: Long
)