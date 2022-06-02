package com.example.randomuser.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DatabaseEmployee")
data class DatabaseEmployee (
    @PrimaryKey
    val id: Double,
    val name: String,
    val email: String,
    val gender: String,
    val status: String
)