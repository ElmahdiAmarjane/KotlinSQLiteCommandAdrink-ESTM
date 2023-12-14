package com.example.bottomnavbar.fragments

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bottomnavbar.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale



/**
 * A simple [Fragment] subclass.
 * Use the [StarsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StarsFragment : Fragment() {

    private lateinit var starsTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_stars, container, false)
//        starsTextView = rootView.findViewById(R.id.starsTextView)
        val rotatingImageView: ImageView = rootView.findViewById(com.example.bottomnavbar.R.id.rotatingImageView)

        // Start the rotation animation
        startRotationAnimation(rotatingImageView)


        // Fetch and display the user's stars
        fetchUserStars()

        return rootView

    }

    private fun fetchUserStars() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            val usersRef = FirebaseDatabase.getInstance().getReference("Users").child(it).child("stars")

            usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userStars = snapshot.getValue(Double::class.java) ?: 0.0
                    updateStarsTextView(userStars)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        }
    }

    private fun updateStarsTextView(userStars: Double) {
        starsTextView.text = String.format(Locale.getDefault(), "%.2f", userStars)
    }

    fun startRotationAnimation(view: View) {
        val rotateAnimation = AnimatorInflater.loadAnimator(
            view.context, com.example.bottomnavbar.R.animator.rotate_animation
        ) as ObjectAnimator
        rotateAnimation.target = view
        rotateAnimation.start()
    }
}