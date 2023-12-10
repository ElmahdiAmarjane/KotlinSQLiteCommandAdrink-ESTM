package com.example.bottomnavbar

// CommandAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavbar.DatabaseHelper.CommandModel
import com.example.bottomnavbar.R
import android.widget.TextView

class CommandAdapter(private val commands: List<CommandModel>) :
    RecyclerView.Adapter<CommandAdapter.CommandViewHolder>() {

    class CommandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textCommand: TextView = itemView.findViewById(R.id.textCommand)
        val textClassroom: TextView = itemView.findViewById(R.id.textClassroom)
        val textFullName: TextView = itemView.findViewById(R.id.textFullName)
        val textDateCommand: TextView = itemView.findViewById(R.id.textDateCommand)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_command, parent, false)
        return CommandViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommandViewHolder, position: Int) {
        val currentCommand = commands[position]

        holder.textCommand.text = "Command: ${currentCommand.command}"
        holder.textClassroom.text = "Classroom: ${currentCommand.classroom}"
        holder.textFullName.text = "Full Name: ${currentCommand.fullName}"
        holder.textDateCommand.text = "Date: ${currentCommand.dateCommand}"
    }

    override fun getItemCount(): Int {
        return commands.size
    }
}
