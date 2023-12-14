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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dbHelper = DatabaseHelper(requireContext())
        val homeView = inflater.inflate(R.layout.fragment_home, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val sendbutton = homeView.findViewById<AppCompatButton>(R.id.sendbutton)
        val commandeinput = homeView.findViewById<EditText>(R.id.commandeinput)
        val classroominput = homeView.findViewById<EditText>(R.id.classroominput)
        val fullnameinput = homeView.findViewById<EditText>(R.id.fullnameinput)

        sendbutton.setOnClickListener {
            val userId = firebaseAuth.currentUser?.uid
            userId?.let {
                val ordersRef = database.getReference("orders")
                val orderRef = ordersRef.push() // Generate a unique key for the order node

                orderRef.child("userId").setValue(userId)
                orderRef.child("commande").setValue(commandeinput.text.toString())
                orderRef.child("classroom").setValue(classroominput.text.toString())
                orderRef.child("fullname").setValue(fullnameinput.text.toString())
                orderRef.child("status").setValue("New") //the status can be new or pending or delivered or canceled
                orderRef.child("date").setValue(ServerValue.TIMESTAMP) // Add the order date

                // Update user's stars
                updateStars(userId)

                // After adding the order, navigate to the HistoryFragment
                val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, HistoriqueFragment())
                fragmentTransaction.addToBackStack(null) // Optional: Adds the transaction to the back stack
                fragmentTransaction.commit()
            }
        }

        return homeView
    }

    private fun updateStars(userId: String) {
        val usersRef = database.getReference("Users").child(userId).child("stars")

        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentStars = snapshot.getValue(Double::class.java) ?: 0.0
                val newStars = currentStars + 1.4

                // Update the user's stars in the database
                usersRef.setValue(newStars)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}
