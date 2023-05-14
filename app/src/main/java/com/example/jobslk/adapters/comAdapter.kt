package com.example.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.main.R
import com.example.main.models.CompanyModel

class comAdapter(private val comList: ArrayList<CompanyModel>) :
    RecyclerView.Adapter<comAdapter.ViewHolder>(){

    private lateinit var mListner: onItemClickListner

    interface onItemClickListner {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(clickListner: onItemClickListner){
        mListner= clickListner
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): comAdapter.ViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_login,parent, false)
        return  ViewHolder(itemView, mListner)
    }

    override fun onBindViewHolder(holder: comAdapter.ViewHolder, position: Int) {
     val currentCom = comList[position]
        holder.comUsername.text = currentCom.comName
    }

    override fun getItemCount(): Int {
       return comList.size
    }


     class ViewHolder(itemView: View, clickListner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {

        val comUsername : TextView = itemView.findViewById(R.id.editTextTextPersonName2)

        init {
            itemView.setOnClickListener {
                clickListner.onItemClick(adapterPosition)
            }
        }
    }
}