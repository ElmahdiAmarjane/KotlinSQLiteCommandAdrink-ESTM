package com.example.bottomnavbar.fragments


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavbar.CommandAdapter

import com.example.bottomnavbar.DatabaseHelper
import com.example.bottomnavbar.R



class HistoriqueFragment : Fragment() {
  //  private lateinit var sharedViewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var commandAdapter: CommandAdapter
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootview =inflater.inflate(R.layout.fragment_historique, container, false)
         dbHelper = DatabaseHelper(requireContext())
        recyclerView = rootview.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        loadCommands()
        return  rootview
    }
    private fun loadCommands() {
        val commands = dbHelper.getAllCommands()
        commandAdapter = CommandAdapter(commands)
        recyclerView.adapter = commandAdapter
    }

}