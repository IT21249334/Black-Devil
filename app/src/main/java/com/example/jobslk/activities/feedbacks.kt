package com.example.jobslk



import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.jobslk.models.FeedbackModel
import com.example.jobslk.adapters.FeedbackAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobslk.R
import com.google.firebase.database.*



class  feedbacks : AppCompatActivity() {

    private  lateinit var feedbackRecyclerView: RecyclerView
    private lateinit var  tvLoadingData: TextView
    private lateinit var feedbackList: ArrayList<FeedbackModel>
    private lateinit var dbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedbacks)

        feedbackRecyclerView = findViewById(R.id.rvdov)
        feedbackRecyclerView.layoutManager = LinearLayoutManager(this)
        feedbackRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

       feedbackList = arrayListOf<FeedbackModel>()

        getFeedbacksData()

    }

    private fun getFeedbacksData() {
        feedbackRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Feedbacks")

        dbRef.addValueEventListener(object : ValueEventListener {
              @SuppressLint("SuspiciosIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {
                feedbackList.clear()
                if (snapshot.exists()) {
                    for (feedbackSnap in snapshot.children) {
                        val feedbackData = feedbackSnap.getValue(FeedbackModel::class.java)
                        feedbackList.add(feedbackData!!)
                    }
                    val mAdapter = FeedbackAdapter(feedbackList)
                    feedbackRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListner(object : FeedbackAdapter.onItemClickListner {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@feedbacks, Deletefeedback::class.java)

                            //put extras
                            intent.putExtra("feedbackId", feedbackList[position].feedbackId)
                            intent.putExtra("feedbackname", feedbackList[position].name)
                            intent.putExtra("feedbackemail", feedbackList[position].email)
                            intent.putExtra("feedbackfeedback", feedbackList[position].feedback)
                            intent.putExtra("feedbackusername", feedbackList[position].username)
                            intent.putExtra("feedbackpassword", feedbackList[position].password)


                            startActivity(intent)
                        }

                    })

                    feedbackRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



    }
}

