package com.example.tasklist

import androidx.room.*

@Entity(tableName = "tasks")
class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var title: String = ""
    var isDone = false
}