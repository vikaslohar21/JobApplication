package com.example.jobapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExperienceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.experience, container, false)

        // Set OnClickListener for edit button
        view.findViewById<FloatingActionButton>(R.id.editExperience).setOnClickListener {
            (activity as? ProfileActivity)?.showEditDialog()
        }

        return view
    }

    // Method to update details in the fragment
    fun updateDetails(newDetails: String) {
        // Update UI with new details
    }
}