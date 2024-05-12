package com.example.jobapplication.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobapplication.R
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<JobData>()
    private lateinit var adaptor: JobAdaptor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        addDataToList()
        adaptor = JobAdaptor(mList)
        recyclerView.adapter = adaptor

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        return view
    }

    private fun filterList(query : String?){
        if (query != null){
            val filteredList = ArrayList<JobData>()
            for (i in mList){
                if (i.jobRole.toLowerCase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }

            if(filteredList.isEmpty()){
                Toast.makeText(requireContext(),"No data Found", Toast.LENGTH_SHORT).show()
            }
            else{
                adaptor.setFilteredList(filteredList)
            }
        }
    }

    private fun addDataToList() {
        mList.add(
            JobData(
                R.drawable.google,
                "Rs 1,00,000 - 1,50,000/Month",
                "Senior Project Manager",
                "Google  -",
                "Bangalore",
                "Full Time",
                "Office"

            )
        )
        mList.add(
            JobData(
                R.drawable.microsoft,
                "Rs 90,000 - 1,20,000/Month",
                "Software Engineer",
                "Microsoft  -",
                "Hyderabad",
                "Part Time",
                "Remote"
            )
        )
        mList.add(
            JobData(
                R.drawable.oracle,
                "Rs 70,000 - 1,00,000/Month",
                "AI/ML Engineer",
                "Oracle  -",
                "Mumbai",
                "Full Time",
                "Office"
            )
        )
        mList.add(
            JobData(
                R.drawable.ibm,
                "Rs 30,000 - 50,000/Month",
                "Web Developer",
                "IBM  -",
                "Noida",
                "Internship",
                "Remote"
            )
        )
        mList.add(
            JobData(
                R.drawable.amazon,
                "Rs 1,50,000 - 2,00,000/Month",
                "Devops Engineer",
                "Amazon  -",
                "Gurgaon",
                "Full Time",
                "Office"

            )
        )
        mList.add(
            JobData(
                R.drawable.mercedes,
                "Rs 25,000 - 50,000/Month",
                "Graphic Designer",
                "Mercedes  -",
                "Pune",
                "Full Time",
                "Remote"
            )
        )
        mList.add(
            JobData(
                R.drawable.facebook,
                "Rs 1,00,000 - 1,50,000/Month",
                "Mobile Developer",
                "Facebook  -",
                "California",
                "Full Time",
                "Office"

            )
        )



    }
}
