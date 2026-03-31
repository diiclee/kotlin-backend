package com.example.kotlin_backend.util

import com.example.kotlin_backend.exception.ResourceNotFoundException
import org.springframework.data.jpa.repository.JpaRepository

fun <T : Any, ID : Any> JpaRepository<T, ID>.findByIdOrThrow(id: ID, entityName: String): T =
    findById(id).orElseThrow { ResourceNotFoundException("$entityName with id $id not found") }