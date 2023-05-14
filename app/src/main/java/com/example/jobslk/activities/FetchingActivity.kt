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
import com.example.main.adapters.comAdapter
import com.example.main.models.CompanyModel
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var comRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var comList: ArrayList<CompanyModel>
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        comRecyclerView = findViewById(R.id.rvCompany)
        comRecyclerView.layoutManager = LinearLayoutManager(this)
        comRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        comList = arrayListOf<CompanyModel>()

        getComData()
    }

    private fun getComData() {
        comRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Company")

        dbRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {
                comList.clear()
                if (snapshot.exists()){
                    for (comSnap in snapshot.children){
                        val comData = comSnap.getValue(CompanyModel::class.java)
                        comList.add(comData!!)
                    }
                    val mAdapter = comAdapter(comList)
                    comRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListner(object : comAdapter.onItemClickListner{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, companyProfile::class.java)

                            intent.putExtra("comId", comList[position].comId)
                            intent.putExtra("comName", comList[position].comName)
                            intent.putExtra("country", comList[position].country)
                            intent.putExtra("state", comList[position].state)
                            intent.putExtra("email", comList[position].email)
                            intent.putExtra("username", comList[position].username)
                            intent.putExtra("password", comList[position].password)
                            startActivity(intent)


                        }
                    })

                    comRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}

