package com.example.jobapplication.Fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobapplication.R

class JobAdaptor(private var mList: List<JobData>) :
    RecyclerView.Adapter<JobAdaptor.JobViewHolder>() {

    inner class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo: ImageView = itemView.findViewById(R.id.logo)
        val salary: TextView = itemView.findViewById(R.id.salarytxt)
        val jobRole: TextView = itemView.findViewById(R.id.jobroletxt)
        val companyLocation: TextView = itemView.findViewById(R.id.companyLocationtxt)
        val companyName: TextView = itemView.findViewById(R.id.companyNametxt)
        val employeeStatus: TextView = itemView.findViewById(R.id.employeeStatustxt)
        val jobMode: TextView = itemView.findViewById(R.id.jobModetxt)
    }

    fun setFilteredList(mList: List<JobData>){
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return JobViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val currentItem = mList[position]
        holder.logo.setImageResource(currentItem.logoResourceId)
        holder.salary.text = currentItem.salary
        holder.jobRole.text = currentItem.jobRole
        holder.companyName.text = currentItem.companyName
        holder.companyLocation.text = currentItem.companyLocation
        holder.employeeStatus.text = currentItem.employeeStatus
        holder.jobMode.text = currentItem.jobMode
    }
}
