package com.example.tasklist

import androidx.room.*

@Entity(tableName = "personsDB")
class PersonEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var firstName: String = ""
    var secondName: String = ""
    var birthDate: String = ""
    var phone: String = ""
    var isDone = false
}