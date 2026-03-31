package com.example.kotlin_backend.dto.response

data class ProjectResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val ownerId: Long,
    val ownerName: String
)