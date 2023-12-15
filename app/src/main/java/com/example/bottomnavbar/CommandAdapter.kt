package com.example.bottomnavbar

import android.graphics.Color
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

class CommandAdapter(private val orders: List<Order>,private val onItemClickListener: (Order) -> Unit) : RecyclerView.Adapter<CommandAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textCommand: TextView = itemView.findViewById(R.id.textCommand)
        val textClassroom: TextView = itemView.findViewById(R.id.textClassroom)
        val textDateCommand: TextView = itemView.findViewById(R.id.textDateCommand)
        val textfullname: TextView = itemView.findViewById(R.id.textfullname)
        val textCommandStatus: TextView = itemView.findViewById(R.id.textCommandStatus)

        init {
            textCommandStatus.setOnClickListener {
                val clickedOrder = orders[adapterPosition]
                onItemClickListener.invoke(clickedOrder)
            }
       }
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
        holder.textCommandStatus.text = currentOrder.status
        holder.textDateCommand.text = formatDate(currentOrder.date)

        // Set the textCommandStatus text and background color based on the order status
        holder.textCommandStatus.text = currentOrder.status
        when (currentOrder.status.lowercase(Locale.getDefault())) {
            "new" -> holder.textCommandStatus.setBackgroundColor(Color.GREEN)
            "pending" -> holder.textCommandStatus.setBackgroundColor(Color.parseColor("#FFA500")) // Orange
            "delivered" -> holder.textCommandStatus.setBackgroundColor(Color.BLUE)
            "canceled" -> holder.textCommandStatus.setBackgroundColor(Color.RED)
            else -> holder.textCommandStatus.setBackgroundColor(Color.TRANSPARENT)
        }
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

