package com.example.kotlin_backend.dto.response

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val taskId: Long,
    val authorId: Long,
    val authorName: String
)