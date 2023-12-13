package com.example.bottomnavbar
//
//// CommandAdapter.kt
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.bottomnavbar.DatabaseHelper.CommandModel
//import com.example.bottomnavbar.R
//import android.widget.TextView
//
//class CommandAdapter(private val commands: List<CommandModel>) :
//    RecyclerView.Adapter<CommandAdapter.CommandViewHolder>() {
//
//    class CommandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val textCommand: TextView = itemView.findViewById(R.id.textCommand)
//        val textClassroom: TextView = itemView.findViewById(R.id.textClassroom)
//
//        val textDateCommand: TextView = itemView.findViewById(R.id.textDateCommand)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_command, parent, false)
//        return CommandViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: CommandViewHolder, position: Int) {
//        val currentCommand = commands[position]
//
//        holder.textCommand.text = currentCommand.command
//        holder.textClassroom.text = currentCommand.classroom
//
//        holder.textDateCommand.text =currentCommand.dateCommand
//    }
//
//    override fun getItemCount(): Int {
//        return commands.size
//    }
//}
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

