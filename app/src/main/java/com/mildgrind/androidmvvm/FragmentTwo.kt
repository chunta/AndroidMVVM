package com.mildgrind.androidmvvm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class FragmentTwo : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_two, container, false)

        // Get a reference to the button
        val button = view.findViewById<Button>(R.id.button_fragment_two)

        // Change the text of the button
        button.text = "New Button Text"

        // Set click listener on the button
        button.setOnClickListener {
            // Navigate to SecondActivity
            val intent = Intent(requireActivity(), SecondActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
