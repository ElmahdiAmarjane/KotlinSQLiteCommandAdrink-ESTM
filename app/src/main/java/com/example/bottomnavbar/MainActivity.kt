package com.example.bottomnavbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.bottomnavbar.fragments.HistoriqueFragment
import com.example.bottomnavbar.fragments.HomeFragment
import com.example.bottomnavbar.fragments.ProductsFragment
import com.example.bottomnavbar.fragments.StarsFragment
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)
        bottomNavigation.add(
             CurvedBottomNavigation.Model( 1,"Home",R.drawable.home )
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model( 2,"Products",R.drawable.offers )
        )
        bottomNavigation.add(
                CurvedBottomNavigation.Model( 3,"Historique",R.drawable.section )
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model( 4,"Stars",R.drawable.more )
        )
        replaceFragment(HomeFragment())
        bottomNavigation.setOnClickMenuListener{
            when(it.id){
                1 ->{
                   replaceFragment(HomeFragment())
                }
                2 ->{
                    replaceFragment(ProductsFragment())
                }
                3 ->{
                    replaceFragment(HistoriqueFragment())
                }
                4 ->{
                    replaceFragment(StarsFragment())
                }
            }
        }

        bottomNavigation.show(1)


    }
    private fun replaceFragment(fragment: Fragment) {
          supportFragmentManager
              .beginTransaction()
              .replace(R.id.fragment_container,fragment)
              .commit()
    }

}