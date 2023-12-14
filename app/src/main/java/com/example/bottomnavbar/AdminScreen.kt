package com.example.bottomnavbar


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.Locale

class AdminScreen : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ordersAdapter: CommandAdapter
    private lateinit var database: FirebaseDatabase
    //private lateinit var userId: String //we donot need this because we are fetching for all users

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_screen)

        val contentTextHistory: TextView = findViewById(R.id.contentTextHistory)
        val idTotalOrders: TextView = findViewById(R.id.id_total_orders)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize Firebase components
        database = FirebaseDatabase.getInstance()
        val auth = FirebaseAuth.getInstance()
        //userId = auth.currentUser?.uid ?: ""

        // Query orders for the current date
        val currentDate: Double = System.currentTimeMillis().toDouble()
        val ordersRef = database.getReference("orders")// remove this to get all users.child(userId)
        val query = ordersRef.orderByChild("date").startAt(currentDate-86400000).endAt(currentDate ) // 86400000 milliseconds in a day

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orders = mutableListOf<Order>()
                for (orderSnapshot in snapshot.children) {
                    val order = orderSnapshot.getValue(Order::class.java)
                    order?.let { orders.add(it) }
                }

                ordersAdapter = CommandAdapter(orders)
                recyclerView.adapter = ordersAdapter

                // Update total orders count
                idTotalOrders.text = orders.size.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        // Set the title with the current date
        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentDate)
        contentTextHistory.text = "Admin Orders for to day : $formattedDate"
    }
}
