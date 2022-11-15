package com.example.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    private val list: List<PersonEntity>
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    lateinit var onItemClick: (id: Int) -> Unit
    lateinit var onItemClickCall: (id: Int) -> Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list[position].firstName
        holder.buttonEdit.setOnClickListener {
            onItemClick(holder.adapterPosition)
        }

        holder.buttonCall.setOnClickListener {
            onItemClickCall(holder.adapterPosition)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.textView)
        val buttonEdit = itemView.findViewById<Button>(R.id.buttonShow)
        val buttonCall = itemView.findViewById<Button>(R.id.buttonCall)
    }
}