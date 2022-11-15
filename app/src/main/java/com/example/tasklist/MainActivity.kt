package com.example.tasklist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room


class MainActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            PersonDatabase::class.java,
            "personsDB")
            .allowMainThreadQueries()
            .build()

        dao = db.personDao()
        list.addAll(dao.all)

        adapter.onItemClick = {
            // open ContactViewActivity
            val intent = Intent(this, ContactViewActivity::class.java)
            intent.putExtra(ITEM_KEY, list.get(it).firstName)
            intent.putExtra(ITEM_ID_KEY, list.get(it).id)
            startActivityForResult(intent, REQUEST_CODE)
        }

        adapter.onItemClickCall = {
            val phoneNo: String = list.get(it).phone
            if (!TextUtils.isEmpty(phoneNo)) {
                val dial = "tel:$phoneNo"
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(dial)))
            } else {
                Toast.makeText(this@MainActivity, "Enter a phone number", Toast.LENGTH_SHORT).show()
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val searchEditText = findViewById<EditText>(R.id.editTextTextPersonName)
        //val txt = findViewById<TextView>(R.id.textView2)

        searchEditText.addTextChangedListener {
            list.clear()
            adapter.notifyDataSetChanged()
            list.addAll(dao.getByName(searchEditText.text.toString()))
        }

        val buttonAdd = findViewById<Button>(R.id.button)

        buttonAdd.setOnClickListener {
            val title = searchEditText.text.toString()
            val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        list.clear()
        list.addAll(dao.all)
        adapter.notifyDataSetChanged()
    }
}

