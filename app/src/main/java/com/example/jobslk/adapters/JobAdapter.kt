package com.example.jobslk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobslk.R
import com.example.jobslk.models.jobModel

class JobAdapter (
    private val jobList:ArrayList<jobModel>):
    RecyclerView.Adapter<JobAdapter.ViewHolder> (){

    private lateinit var mListner: onItemClickListner
    interface onItemClickListner{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(clickListner: onItemClickListner)
    {
        mListner = clickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_job_list_item, parent, false)
        return ViewHolder(itemView,mListner)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDon = jobList[position] //give current donor
        holder.cjjobname.text = currentDon.name //this part confusion
    }



    override fun getItemCount(): Int {
        return jobList.size
    }

    class ViewHolder (itemView: View,clickListner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {
        val cjjobname : TextView = itemView.findViewById(R.id.cjjobname)

        init {
            itemView.setOnClickListener{
                clickListner.onItemClick(adapterPosition)
            }
        }
    }

}
