package com.example.jobslk.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobslk.R
import com.example.jobslk.models.jobModel
import com.google.firebase.database.*
import com.example.jobslk.adapters.JobAdapter

class Fetching : AppCompatActivity() {

    private  lateinit var jobRecyclerView: RecyclerView
    private lateinit var  cjLoadingData: TextView
    private lateinit var jobList: ArrayList<jobModel>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        jobRecyclerView = findViewById(R.id.rvdon)
        jobRecyclerView.layoutManager = LinearLayoutManager(this)
        jobRecyclerView.setHasFixedSize(true)
        cjLoadingData = findViewById(R.id.cjLoadingData)

        jobList = arrayListOf<jobModel>()

        getJobsData()

    }

    private fun getJobsData()
    {
        jobRecyclerView.visibility = View.GONE
        cjLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Job")

        dbRef.addValueEventListener(object : ValueEventListener{
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {
                jobList.clear()
                if(snapshot.exists())
                {
                    for(jobsnap in snapshot.children)
                    {
                        val jobData = jobsnap.getValue(jobModel::class.java)
                        jobList.add(jobData!!)
                    }
                    val mAdapter = JobAdapter(jobList)
                    jobRecyclerView.adapter= mAdapter

                    mAdapter.setOnItemClickListner(object:JobAdapter.onItemClickListner{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@Fetching,jobdetails::class.java)

                            //put extras
                            intent.putExtra("jobId",jobList[position].jobId)
                            intent.putExtra("jobname",jobList[position].name)
                            intent.putExtra("jobtitle",jobList[position].title)
                            intent.putExtra("jobdescription",jobList[position].description)
                            intent.putExtra("jobrequirement",jobList[position].requirement)
                            intent.putExtra("jobsalasry",jobList[position].salary)
                            startActivity(intent)
                        }

                    })

                    jobRecyclerView.visibility = View.VISIBLE
                    cjLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}

