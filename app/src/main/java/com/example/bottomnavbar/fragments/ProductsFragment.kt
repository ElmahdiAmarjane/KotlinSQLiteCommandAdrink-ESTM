package com.example.bottomnavbar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bottomnavbar.R


class ProductsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_products, container, false)

        val productTitle = rootView.findViewById<LinearLayout>(R.id.producttitle)

       //  Bring the view to the front
        productTitle.bringToFront()
        //hide textview when i scroll
        val nestedScrollView = rootView.findViewById<ScrollView>(R.id.scrollview1)
        /*val textViewToHide = rootView.findViewById<TextView>(R.id.choseproducttitle)
        nestedScrollView.viewTreeObserver.addOnScrollChangedListener(
            ViewTreeObserver.OnScrollChangedListener {
                if (nestedScrollView.scrollY > 0) {
                    // User is scrolling, hide the TextView
                    textViewToHide.visibility = View.GONE
                } else {
                    // User is at the top, show the TextView
                    textViewToHide.visibility = View.VISIBLE
                }
            }
        )*/
        return rootView
    }


}