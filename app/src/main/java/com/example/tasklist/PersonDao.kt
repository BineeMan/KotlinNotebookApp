package com.example.tasklist

import androidx.room.*


@Dao
interface PersonDao {
    @get:Query("SELECT * from personsDB")
    val all: List<PersonEntity>

    @get:Query("SELECT firstName, secondName from personsDB")
    val getFirstSecondNames: List<Name>

    @Insert
    fun add(task: PersonEntity): Long

    @Update
    fun update(task: PersonEntity)

    @Delete
    fun delete(todo: PersonEntity)

    @Query("SELECT * FROM personsDB WHERE id = :id")
    fun getById(id: Long): PersonEntity

    @Query("SELECT * FROM personsDB WHERE firstName LIKE '%' || :name || '%'")
    fun getByName(name: String): List<PersonEntity>

    @Query("SELECT * FROM personsDB WHERE firstName = :title")
    fun getByTitle(title: String): PersonEntity
}


class Name {
    @ColumnInfo(name = "firstName")
    var firstName: String? = null

    @ColumnInfo(name = "secondName")
    var secondName: String? = null
}