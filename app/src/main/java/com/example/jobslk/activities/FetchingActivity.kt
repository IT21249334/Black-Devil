package com.example.main.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.main.R
import com.example.main.adapters.cusAdapter
import com.example.main.models.CustomerModel
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var cusRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var cusList: ArrayList<CustomerModel>
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        cusRecyclerView = findViewById(R.id.rvCustomer)
        cusRecyclerView.layoutManager = LinearLayoutManager(this)
        cusRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        cusList = arrayListOf<CustomerModel>()

        getCusData()
    }

    private fun getCusData() {
        cusRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Customer")

        dbRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {
                cusList.clear()
                if (snapshot.exists()){
                    for (cusSnap in snapshot.children){
                        val cusData = cusSnap.getValue(CustomerModel::class.java)
                        cusList.add(cusData!!)
                    }
                    val mAdapter = cusAdapter(cusList)
                    cusRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListner(object : cusAdapter.onItemClickListner{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, customerProfile::class.java)

                            intent.putExtra("cusId", cusList[position].cusId)
                            intent.putExtra("cusName", cusList[position].cusName)
                            intent.putExtra("cJob", cusList[position].cJob)
                            intent.putExtra("salary", cusList[position].salary)
                            intent.putExtra("email", cusList[position].email)
                            intent.putExtra("username", cusList[position].username)
                            intent.putExtra("password", cusList[position].password)
                            startActivity(intent)


                        }
                    })

                    cusRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}

