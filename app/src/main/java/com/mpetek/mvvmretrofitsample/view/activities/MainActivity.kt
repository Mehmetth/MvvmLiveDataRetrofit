package com.mpetek.mvvmretrofitsample.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mpetek.mvvmretrofitsample.R
import kotlinx.android.synthetic.main.activity_main.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mpetek.mvvmretrofitsample.view.fragments.AllLeagues
import com.mpetek.mvvmretrofitsample.view.fragments.AllSports

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SelectedFragmentAndBar(0)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.all_sports -> openFragment(AllSports())
                R.id.all_leagues -> openFragment(AllLeagues())
            }
            true
        }

    }

    fun SelectedFragmentAndBar(fragment: Int){
        when (fragment) {
            0 ->{
                bottom_navigation.selectedItemId = R.id.all_sports
                openFragment(AllSports())
            }
            1 -> {
                bottom_navigation.selectedItemId = R.id.all_leagues
                openFragment(AllLeagues())
            }
        }
    }

    fun openFragment(fragment: Fragment?) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment!!)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed(){
        moveTaskToBack(true);
    }
}