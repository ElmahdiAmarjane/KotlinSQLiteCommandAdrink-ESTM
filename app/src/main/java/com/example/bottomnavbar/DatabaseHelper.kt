package com.example.bottomnavbar

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Date

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "sipswiftdb"
        const val DATABASE_VERSION = 1
    }

    // Define your table creation query
    private val CREATE_COMMANDS_TABLE =
        "CREATE TABLE commands (_id INTEGER PRIMARY KEY AUTOINCREMENT, command TEXT, classroom TEXT, fullName TEXT, datecommand TEXT);"

    override fun onCreate(db: SQLiteDatabase) {
        // Create the table when the database is created
        db.execSQL(CREATE_COMMANDS_TABLE)
    }

    fun insertCommand(command: String, classroom: String, fullName: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("command", command)
            put("classroom", classroom)
            put("fullName", fullName)

            // Get the current date and format it as a string
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val currentDate = sdf.format(Date())
            put("datecommand", currentDate)
        }

        db.insert("commands", null, values)
        db.close()
    }

    fun getAllCommands(): List<CommandModel> {
        val commands = mutableListOf<CommandModel>()
        val db = readableDatabase
        val cursor = db.query(
            "commands", null, null, null, null, null, null
        )

        while (cursor.moveToNext()) {
            val command = cursor.getString(cursor.getColumnIndexOrThrow("command"))
            val classroom = cursor.getString(cursor.getColumnIndexOrThrow("classroom"))
            val fullName = cursor.getString(cursor.getColumnIndexOrThrow("fullName"))
            val dateCommand = cursor.getString(cursor.getColumnIndexOrThrow("datecommand"))

            val commandModel = CommandModel(command, classroom, fullName, dateCommand)
            commands.add(commandModel)
        }

        cursor.close()
        db.close()
        return commands
    }

    data class CommandModel(val command: String, val classroom: String, val fullName: String, val dateCommand: String)





    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades here
        // Example: if (oldVersion < 2) {
        //   db.execSQL("ALTER TABLE commands ADD COLUMN new_column INTEGER;")
        // }
    }

}
