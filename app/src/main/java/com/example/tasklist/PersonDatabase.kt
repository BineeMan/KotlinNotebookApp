package com.example.tasklist

import androidx.room.*

@Database(entities = [PersonEntity::class], version = 3)
abstract class PersonDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}