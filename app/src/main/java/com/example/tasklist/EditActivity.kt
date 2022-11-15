package com.example.tasklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.room.Room

class EditActivity : AppCompatActivity() {
    var id: Long = 0
    lateinit var backgroundLayout: ConstraintLayout
    lateinit var windowLayout: ConstraintLayout

    private val list = mutableListOf<PersonEntity>()
    private val adapter = RecyclerAdapter(list)

    lateinit var db: PersonDatabase
    lateinit var dao: PersonDao

    companion object {
        const val RESULT_KEY = "RESULT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        db = Room.databaseBuilder(
            applicationContext,
            PersonDatabase::class.java,
            "personsDB")
            .allowMainThreadQueries()
            .build()
        dao = db.personDao()
        id = intent.getLongExtra(MainActivity.ITEM_ID_KEY, 0)
        val person = dao.getById(id)
        backgroundLayout = findViewById(R.id.background)
        windowLayout = findViewById(R.id.window)

        val editTextFirstName = findViewById<EditText>(R.id.editTextTextFirstName)
        val editTextSecondName = findViewById<EditText>(R.id.editTextTextSecondName)
        val editTextBirthDate = findViewById<EditText>(R.id.editTextTextBirthdateName)
        val editTextPhone = findViewById<EditText>(R.id.editTextTextPhoneNum)

        editTextFirstName.setText(person.firstName)
        editTextSecondName.setText(person.secondName)
        editTextBirthDate.setText(person.birthDate)
        editTextPhone.setText(person.phone)

        val buttonSave = findViewById<Button>(R.id.buttonSave)

        buttonSave.setOnClickListener {
            person.firstName = editTextFirstName.text.toString()
            person.secondName = editTextSecondName.text.toString()
            person.birthDate = editTextBirthDate.text.toString()
            person.phone = editTextPhone.text.toString()
            dao.update(person)
            finish()
        }

        val buttonCancel = findViewById<Button>(R.id.buttonCancel)
        buttonCancel.setOnClickListener {
            finish()
        }
    }
}