package com.example.bottomnavbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavbar.Order
import com.example.bottomnavbar.R
import java.text.SimpleDateFormat
import java.util.*

class CommandAdapter(private val orders: List<Order>) : RecyclerView.Adapter<CommandAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textCommand: TextView = itemView.findViewById(R.id.textCommand)
        val textClassroom: TextView = itemView.findViewById(R.id.textClassroom)
        val textDateCommand: TextView = itemView.findViewById(R.id.textDateCommand)
        val textfullname: TextView = itemView.findViewById(R.id.textfullname)
      //  val xmarkImageView: ImageView = itemView.findViewById(R.id.xmarkImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_command, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentOrder = orders[position]

        holder.textCommand.text = currentOrder.commande
        holder.textClassroom.text = currentOrder.classroom
        holder.textfullname.text = currentOrder.fullname
        holder.textDateCommand.text = formatDate(currentOrder.date)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    private fun formatDate(timestamp: Long?): String {
        timestamp?.let {
            val date = Date(it)
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return format.format(date)
        }
        return ""
    }
}

