package com.example.jobapplication.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jobapplication.R

class ExperienceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = inflater.inflate(R.layout.experience, container, false)
//
//        // Set OnClickListener for edit button
//        view.findViewById<FloatingActionButton>(R.id.editExperience).setOnClickListener {
//            (activity as? ProfileActivity)?.showEditDialog()
//        }
//
//        return view
//    }
//
//    // Method to update details in the fragment
//    fun updateDetails(newDetails: String) {
//        // Update UI with new details
//    }
        return inflater.inflate(R.layout.experience, container, false)
    }
}