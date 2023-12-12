package com.example.bottomnavbar.fragments

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.bottomnavbar.R


/**
 * A simple [Fragment] subclass.
 * Use the [StarsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StarsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(com.example.bottomnavbar.R.layout.fragment_stars, container, false)

        val rotatingImageView: ImageView = rootView.findViewById(com.example.bottomnavbar.R.id.rotatingImageView)

        // Start the rotation animation
        startRotationAnimation(rotatingImageView)

        return rootView

    }

    fun startRotationAnimation(view: View) {
        val rotateAnimation = AnimatorInflater.loadAnimator(
            view.context, com.example.bottomnavbar.R.animator.rotate_animation
        ) as ObjectAnimator
        rotateAnimation.target = view
        rotateAnimation.start()
    }
}