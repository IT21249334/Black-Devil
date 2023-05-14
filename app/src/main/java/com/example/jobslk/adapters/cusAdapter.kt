package com.example.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.main.R
import com.example.main.models.CustomerModel

class cusAdapter(private val cusList: ArrayList<CustomerModel>) :
    RecyclerView.Adapter<cusAdapter.ViewHolder>(){

    private lateinit var mListner: onItemClickListner

    interface onItemClickListner {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(clickListner: onItemClickListner){
        mListner= clickListner
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cusAdapter.ViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_login,parent, false)
        return  ViewHolder(itemView, mListner)
    }

    override fun onBindViewHolder(holder: cusAdapter.ViewHolder, position: Int) {
     val currentCus = cusList[position]
        holder.cusUsername.text = currentCus.cusName
    }

    override fun getItemCount(): Int {
       return cusList.size
    }


     class ViewHolder(itemView: View, clickListner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {

        val cusUsername : TextView = itemView.findViewById(R.id.editTextTextPersonName2)

        init {
            itemView.setOnClickListener {
                clickListner.onItemClick(adapterPosition)
            }
        }
    }
}