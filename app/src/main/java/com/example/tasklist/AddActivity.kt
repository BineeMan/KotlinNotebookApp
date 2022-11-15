package com.example.tasklist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.ColorUtils
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import androidx.room.Room

class AddActivity : AppCompatActivity() {
    var id = 0
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

        backgroundLayout = findViewById(R.id.background)
        windowLayout = findViewById(R.id.window)

        val itemText = intent.getStringExtra(MainActivity.ITEM_KEY)
        id = intent.getIntExtra(MainActivity.ITEM_ID_KEY, 0)

        val editTextFirstName = findViewById<EditText>(R.id.editTextTextFirstName)
        val editTextSecondName = findViewById<EditText>(R.id.editTextTextSecondName)
        val editTextBirthDate = findViewById<EditText>(R.id.editTextTextBirthdateName)
        val editTextPhone = findViewById<EditText>(R.id.editTextTextPhoneNum)
        dao = db.personDao()
        val buttonSave = findViewById<Button>(R.id.buttonSave)

        buttonSave.setOnClickListener {
            //editText.text.clear()
            val person = PersonEntity()
            person.firstName = editTextFirstName.text.toString()
            person.secondName = editTextSecondName.text.toString()
            person.phone = editTextPhone.text.toString()
            person.birthDate = editTextBirthDate.text.toString()

            person.id = dao.add(person)
            list.add(person)
            //adapter.notifyItemInserted(list.lastIndex)
            finish()
        }

        val buttonCancel = findViewById<Button>(R.id.buttonCancel)
        buttonCancel.setOnClickListener {
            finish()
        }

        setActivityStyle()
    }

    @SuppressLint("RestrictedApi")
    private fun setActivityStyle() {

        // Make the background full screen, over status bar
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        this.window.statusBarColor = Color.TRANSPARENT
        val winParams = this.window.attributes
        winParams.flags =
            winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        this.window.attributes = winParams

        // Fade animation for the background of Popup Window
        val alpha = 100 //between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            backgroundLayout.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()

        windowLayout.alpha = 0f
        windowLayout.animate().alpha(1f).setDuration(500)
            .setInterpolator(DecelerateInterpolator()).start()

        // Close window when you tap on the dim background
        backgroundLayout.setOnClickListener { onBackPressed() }
        windowLayout.setOnClickListener { /* Prevent activity from closing when you tap on the popup's window background */ }
    }

    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        // Fade animation for the background of Popup Window when you press the back button
        val alpha = 100 // between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            backgroundLayout.setBackgroundColor(
                animator.animatedValue as Int
            )
        }

        // Fade animation for the Popup Window when you press the back button
        windowLayout.animate().alpha(0f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()

        // After animation finish, close the Activity
        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                finish()
                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
    }

}