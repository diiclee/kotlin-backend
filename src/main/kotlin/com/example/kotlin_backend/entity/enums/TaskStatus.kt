package com.example.kotlin_backend.entity.enums

enum class TaskStatus {
    OPEN,
    IN_PROGRESS,
    COMPLETED;

    fun canTransitionTo(next: TaskStatus): Boolean = when (this) {
        OPEN -> next == IN_PROGRESS
        IN_PROGRESS -> next == COMPLETED
        COMPLETED -> false
    }
}