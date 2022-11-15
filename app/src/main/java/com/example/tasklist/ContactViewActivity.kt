package com.example.tasklist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.ColorUtils
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import androidx.room.Room
import androidx.room.Update

class ContactViewActivity : AppCompatActivity() {
    var id: Long = 0
    lateinit var backgroundLayout: ConstraintLayout
    lateinit var windowLayout: ConstraintLayout
    lateinit var person: PersonEntity

    private val list = mutableListOf<PersonEntity>()
    private val adapter = RecyclerAdapter(list)

    lateinit var db: PersonDatabase
    lateinit var dao: PersonDao

    companion object {
        const val REQUEST_CODE = 1
        const val ITEM_KEY = "ITEM_KEY"
        const val ITEM_ID_KEY = "ITEM_ID_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_view)

        backgroundLayout = findViewById(R.id.background)
        windowLayout = findViewById(R.id.window)

        db = Room.databaseBuilder(
            applicationContext,
            PersonDatabase::class.java,
            "personsDB")
            .allowMainThreadQueries()
            .build()
        dao = db.personDao()

        //val itemText = intent.getStringExtra(MainActivity.ITEM_KEY)
        UpdateData()

        val buttonCancel = findViewById<Button>(R.id.buttonCancel)

        buttonCancel.setOnClickListener {
            finish()
        }

        val buttonEdit = findViewById<Button>(R.id.buttonModify)

        buttonEdit.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra(MainActivity.ITEM_ID_KEY, person.id)
            startActivityForResult(intent, MainActivity.REQUEST_CODE)
        }

        val buttonDelete = findViewById<Button>(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            dao.delete(person)
                finish()
            }

//        val buttonSave = findViewById<Button>(R.id.buttonSave)
//        buttonSave.setOnClickListener {
//            val returnIntent = Intent()
//            returnIntent.putExtra(RESULT_KEY, editText.text.toString())
//            returnIntent.putExtra(MainActivity.ITEM_ID_KEY, id)
//            setResult(Activity.RESULT_OK, returnIntent)
//            finish()
//        }


    }

    fun UpdateData() {
        id = intent.getLongExtra(MainActivity.ITEM_ID_KEY, 0)
        person = dao.getById(id)

        val editTextFirstName = findViewById<TextView>(R.id.textViewFirstName)
        val editTextSecondName= findViewById<TextView>(R.id.textViewSecondName)
        val editTextPhone = findViewById<TextView>(R.id.textViewTextPhoneNum)
        val editBirth = findViewById<TextView>(R.id.textViewBirthdateName)

        editTextFirstName.setText(person.firstName)
        editTextSecondName.setText(person.secondName)
        editTextPhone.setText(person.phone)
        editBirth.setText(person.birthDate)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UpdateData()
    }

}