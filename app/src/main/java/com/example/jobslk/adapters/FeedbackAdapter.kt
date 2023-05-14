package com.example.jobslk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobslk.R
import com.example.jobslk.models.FeedbackModel


    class FeedbackAdapter(private val feedbackList:ArrayList<FeedbackModel>):RecyclerView.Adapter<FeedbackAdapter.ViewHolder> (){

        private lateinit var mListner: onItemClickListner
        interface onItemClickListner{

            fun onItemClick(position: Int)
        }

        fun setOnItemClickListner(clickListner: onItemClickListner)
        {
            mListner = clickListner
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_feedbacklist, parent, false)
            return ViewHolder(itemView,mListner)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentfeedback = feedbackList[position] //give current feedback
            holder.tvFeedbackName.text = currentfeedback.name //this part confusion
        }



        override fun getItemCount(): Int {
            return feedbackList.size
        }

        class ViewHolder (itemView: View, clickListner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {
            val tvFeedbackName : TextView = itemView.findViewById(R.id.tvfeedbackName)

            init {
                itemView.setOnClickListener{
                    clickListner.onItemClick(adapterPosition)
                }
            }
        }

    }

