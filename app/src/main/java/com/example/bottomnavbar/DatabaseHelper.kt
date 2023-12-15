package com.example.bottomnavbar

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "sipswiftdb"
        const val DATABASE_VERSION = 2
    }


    private val CREATE_MENU_TABLE =
        "CREATE TABLE menu (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, price DOUBLE, type TEXT, imagesrc INTEGER );"

    override fun onCreate(db: SQLiteDatabase) {
        try {
            Log.d("DatabaseHelper", "onCreate called")
            // Create the table when the database is created
            db.execSQL(CREATE_MENU_TABLE)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error creating table", e)
        }
    }

    fun insertProduct(title: String, price: Double, type: String , imagesrc:Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("price", price)
            put("type", type)
            put("imagesrc", imagesrc)
        }

        db.insert("menu", null, values)
        db.close()
    }

    fun getAllProducts(): List<MenuModel> {
        val products = mutableListOf<MenuModel>()
        val db = readableDatabase
        val cursor = db.query(
            "menu", null, null, null, null, null, null
        )
        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            val price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"))
            val type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
            val imagesrc = cursor.getInt(cursor.getColumnIndexOrThrow("imagesrc"))

            val menuModel = MenuModel(title, price, type, imagesrc)
            products.add(menuModel)
        }

        cursor.close()
        db.close()
        return products
    }
    fun clearMenuTable() {
        val db = writableDatabase
        db.delete("menu", null, null)
        db.close()
    }
    fun hasRecords(): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM menu", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        db.close()
        return count > 0
    }
    fun getDrinkProducts(): List<MenuModel> {
        val products = mutableListOf<MenuModel>()
        val db = readableDatabase

        val cursor = db.query(
            "menu", null, "type = ?", arrayOf("drink"), null, null, null
        )

        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            val price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"))
            val type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
            val imagesrc = cursor.getInt(cursor.getColumnIndexOrThrow("imagesrc"))

            val menuModel = MenuModel(title, price, type, imagesrc)
            products.add(menuModel)
        }

        cursor.close()
        db.close()
        return products
    }
    fun getCakeProducts(): List<MenuModel> {
        val products = mutableListOf<MenuModel>()
        val db = readableDatabase

        val cursor = db.query(
            "menu", null, "type = ?", arrayOf("cake"), null, null, null
        )

        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            val price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"))
            val type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
            val imagesrc = cursor.getInt(cursor.getColumnIndexOrThrow("imagesrc"))

            val menuModel = MenuModel(title, price, type, imagesrc)
            products.add(menuModel)
        }

        cursor.close()
        db.close()
        return products
    }
    data class MenuModel(val title: String, val price: Double, val type: String, val imagesrc: Int)





    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades here
        // Example: if (oldVersion < 2) {
        //   db.execSQL("ALTER TABLE commands ADD COLUMN new_column INTEGER;")
        // }
    }

}
