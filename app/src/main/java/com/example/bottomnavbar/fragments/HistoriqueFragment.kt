package com.example.bottomnavbar.fragments
//
//
//import android.os.Bundle
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//
//import android.widget.TextView
//
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.bottomnavbar.CommandAdapter
//
//import com.example.bottomnavbar.DatabaseHelper
//import com.example.bottomnavbar.R
//
//
//
//class HistoriqueFragment : Fragment() {
//  //  private lateinit var sharedViewModel: SharedViewModel
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var commandAdapter: CommandAdapter
//    private lateinit var dbHelper: DatabaseHelper
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        var rootview =inflater.inflate(R.layout.fragment_historique, container, false)
//         dbHelper = DatabaseHelper(requireContext())
//        recyclerView = rootview.findViewById(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//
//        loadCommands()
//        return  rootview
//    }
//    private fun loadCommands() {
//        val commands = dbHelper.getAllCommands()
//        commandAdapter = CommandAdapter(commands)
//        recyclerView.adapter = commandAdapter
//    }
//
//}
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavbar.CommandAdapter
import com.example.bottomnavbar.Order // Assume you have a data class representing an order
import com.example.bottomnavbar.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HistoriqueFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var commandAdapter: CommandAdapter
    private lateinit var ordersRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_historique, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        firebaseAuth = FirebaseAuth.getInstance()
        val userId = firebaseAuth.currentUser?.uid
        userId?.let {
            ordersRef = FirebaseDatabase.getInstance().getReference("orders")
            loadCommands(it)
        }

        return rootView
    }

    private fun loadCommands(userId: String) {
        ordersRef.orderByChild("userId").equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val orders = mutableListOf<Order>()
                    for (orderSnapshot in snapshot.children) {
                        val order = orderSnapshot.getValue(Order::class.java)
                        order?.let { orders.add(it) }
                    }
                    commandAdapter = CommandAdapter(orders)
                    recyclerView.adapter = commandAdapter
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
    }
}
