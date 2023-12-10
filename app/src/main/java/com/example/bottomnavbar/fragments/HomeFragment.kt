package com.example.bottomnavbar.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import com.example.bottomnavbar.R
import com.example.bottomnavbar.SharedViewModel
import com.example.bottomnavbar.DatabaseHelper



class HomeFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dbHelper = DatabaseHelper(requireContext())
        val homeView = inflater.inflate(R.layout.fragment_home, container, false)
        val sendbutton = homeView.findViewById<AppCompatButton>(R.id.sendbutton)
        val commandeinput = homeView.findViewById<EditText>(R.id.commandeinput)
        val classroominput = homeView.findViewById<EditText>(R.id.classroominput)
        val fullnameinput = homeView.findViewById<EditText>(R.id.fullnameinput)
        sendbutton.setOnClickListener {

            dbHelper.insertCommand(commandeinput.text.toString(),classroominput.text.toString()
            ,fullnameinput.text.toString())
        }


        // Inflate the layout for this fragment
        return homeView
    }


}