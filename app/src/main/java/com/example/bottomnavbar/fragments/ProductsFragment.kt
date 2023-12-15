package com.example.bottomnavbar.fragments


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavbar.CommandAdapter

import com.example.bottomnavbar.DatabaseHelper
import com.example.bottomnavbar.MenuAdapter
import com.example.bottomnavbar.R



class ProductsFragment : Fragment() {
    //  private lateinit var sharedViewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootview =inflater.inflate(R.layout.fragment_products, container, false)
        dbHelper = DatabaseHelper(requireContext())
        recyclerView = rootview.findViewById(R.id.recyclerproducts)
         val allselector = rootview.findViewById<AppCompatButton>(R.id.allselector)
         val drinkselector = rootview.findViewById<AppCompatButton>(R.id.drinkselector)
         val cakeselector = rootview.findViewById<AppCompatButton>(R.id.cakeselector)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())



      if(!dbHelper.hasRecords()){
          dbHelper.insertProduct("Normal coffee",10.0,"coffee",1)
          dbHelper.insertProduct("Coffee with milk",14.0,"coffee",2)
          dbHelper.insertProduct("Tea",5.0,"drink",3)
          dbHelper.insertProduct("Orange juice",18.0,"drink",4)
          dbHelper.insertProduct("Kiwi juice",20.0,"drink",5)
          dbHelper.insertProduct("Cocacola",8.0,"drink",6)
          dbHelper.insertProduct("Watter",3.0,"drink",7)
          dbHelper.insertProduct("Croissant",10.0,"cake",8)
          dbHelper.insertProduct("Mille feuilles",10.0,"cake",9)
      }
        loadProducts()
        allselector.setOnClickListener{
            menuAdapter = MenuAdapter(dbHelper.getAllProducts())
            recyclerView.adapter = menuAdapter

        }
        drinkselector.setOnClickListener{
            menuAdapter = MenuAdapter(dbHelper.getDrinkProducts())
            recyclerView.adapter = menuAdapter
        }
        cakeselector.setOnClickListener{
            menuAdapter = MenuAdapter(dbHelper.getCakeProducts())
            recyclerView.adapter = menuAdapter
        }


        return  rootview

    }
    private fun loadProducts() {
        val products = dbHelper.getAllProducts()
        menuAdapter = MenuAdapter(products)
        recyclerView.adapter = menuAdapter
    }

}