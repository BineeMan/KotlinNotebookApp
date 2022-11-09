package com.example.tasklist

import androidx.room.*

@Dao
interface TaskDao {
    @get:Query("SELECT * from tasks")
    val all: List<TaskEntity>

    @Insert
    fun add(task: TaskEntity): Long

    @Update
    fun update(task: TaskEntity)

    @Delete
    fun delete(todo: TaskEntity)

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getById(id: Long): TaskEntity

    @Query("SELECT * FROM tasks WHERE title = :title")
    fun getByTitle(title: String): TaskEntity
}