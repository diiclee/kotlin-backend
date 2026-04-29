package com.example.kotlin_backend.mapper

import com.example.kotlin_backend.dto.request.AddCommentRequest
import com.example.kotlin_backend.dto.response.CommentResponse
import com.example.kotlin_backend.entity.Comment
import com.example.kotlin_backend.entity.Task
import com.example.kotlin_backend.entity.User
import org.springframework.stereotype.Component

@Component
class CommentMapper {

    fun toEntity(request: AddCommentRequest, task: Task, author: User): Comment {
        return Comment(
            content = request.content,
            task = task,
            author = author
        )
    }

    fun toResponse(comment: Comment): CommentResponse {
        return CommentResponse(
            id = comment.id ?: throw IllegalStateException("Comment id was null after save"),
            content = comment.content,
            createdAt = comment.createdAt,
            taskId = comment.task.id ?: throw IllegalStateException("Task id was null"),
            authorId = comment.author.id ?: throw IllegalStateException("Author id was null"),
            authorName = comment.author.name
        )
    }
}