package com.mildgrind.androidmvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        val fragmentOne = FragmentOne()
        val fragmentTwo = FragmentTwo()

        fragmentTransaction.add(R.id.fragment_container, fragmentOne)
        fragmentTransaction.add(R.id.fragment_container, fragmentTwo)

        fragmentTransaction.commit()
    }
}