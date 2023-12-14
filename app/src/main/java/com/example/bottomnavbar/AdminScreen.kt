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
import androidx.appcompat.app.AlertDialog
import java.util.Date


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
                    val orderId = orderSnapshot.key // Get the order ID
                    val order = orderSnapshot.getValue(Order::class.java)

                    if (orderId != null && order != null) {
                        val orderWithId = order.copy(orderId = orderId) // Create a new instance of Order with orderId
                        orders.add(orderWithId)
                    }
                }
                
                ordersAdapter = CommandAdapter(orders) { clickedOrder ->
                    // Handle item click here, e.g., show a dialog
                    showStatusUpdateDialog(clickedOrder)
                }
                
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

    private fun showStatusUpdateDialog(order: Order) {
        val formattedDate = formatDate(order.date)
        val orderId:String=order.orderId

        // Use the formattedDate in the dialog title
        val dialogTitle = "Update order Status at : $formattedDate $orderId"

        // Implement the dialog to update the status here
        // You can use AlertDialog or a custom dialog based on your requirements
        // Display a dialog with options to update the status for the selected order
        // You can use AlertDialog.Builder for simplicity
        val statusOptions = arrayOf("Pending", "Delivered", "Canceled")

        AlertDialog.Builder(this)
            .setTitle(dialogTitle)
            .setItems(statusOptions) { dialog, which ->
                // Update the status based on the selected option (which)
                when (which) {
                    0 -> updateOrderStatus(orderId, "Pending")
                    1 -> updateOrderStatus(orderId, "Delivered")
                    2 -> updateOrderStatus(orderId, "Canceled")
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun formatDate(timestamp: Long?): String {
        timestamp?.let {
            val date = Date(it)
            val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            return format.format(date)
        }
        return ""
    }

    private fun updateOrderStatus(orderId: String, newStatus: String) {
        val orderRef = database.getReference("orders").child(orderId)

        orderRef.child("status").setValue(newStatus)
            .addOnSuccessListener {
                // Handle success, if needed
            }
            .addOnFailureListener {
                // Handle failure, if needed
            }
    }

}
