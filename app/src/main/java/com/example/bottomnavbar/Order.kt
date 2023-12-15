package com.example.bottomnavbar

data class Order(
    val orderId:String="kk",
    val userId: String? = null,
    val commande: String? = null,
    val classroom: String? = null,
    val fullname: String? = null,
    var status: String="null",
    val date: Long? = null
)
