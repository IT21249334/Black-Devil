package com.example.jobslk.activities

import android.content.ClipDescription
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.jobslk.R
import com.example.jobslk.models.jobModel
import com.google.firebase.database.*

class jobdetails : AppCompatActivity() {

    private lateinit var cjname : TextView
    private lateinit var cjtitle : TextView
    private lateinit var cjdescription : TextView
    private lateinit var cjrequirement : TextView
    private lateinit var cjsalary : TextView
    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobdetails)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("jobId").toString(),
                intent.getStringExtra("jobname").toString()

            )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("jobId").toString()
            )
        }
    }
    private fun deleteRecord(
        id :String
    )
    {
        val dbRef = FirebaseDatabase.getInstance().getReference("Job").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Job data deleted!", Toast.LENGTH_LONG).show()

            val intent = Intent(this,Fetching::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error->
            Toast.makeText(this,"Deleting ERR ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        cjname = findViewById(R.id.cjname) // replace with the actual id of the TextView in your layout file
        cjtitle = findViewById(R.id.cjtitle)
        cjdescription = findViewById(R.id.cjdescription)
        cjrequirement = findViewById(R.id.cjrequirement)
        cjsalary = findViewById(R.id.cjsalary)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        cjname.text= intent.getStringExtra("jobname")
        cjtitle.text= intent.getStringExtra("jobtitle")
        cjdescription.text= intent.getStringExtra("jobdescription")
        cjrequirement.text= intent.getStringExtra("jobrequirement")
        cjsalary.text= intent.getStringExtra("jobsalary")
    }

    private fun openUpdateDialog(
        jobId:String,
        jobname:String
    )
    {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_job_update,null)

        mDialog.setView(mDialogView)

        val cname = mDialogView.findViewById<EditText>(R.id.cname)
        val jtitle = mDialogView.findViewById<EditText>(R.id.jtitle)
        val jdescription = mDialogView.findViewById<EditText>(R.id.jdescription)
        val jrequirement = mDialogView.findViewById<EditText>(R.id.jrequirement)
        val jsalary = mDialogView.findViewById<EditText>(R.id.jsalary)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        cname.setText(intent.getStringExtra("jobname").toString())
        jtitle.setText(intent.getStringExtra("jobtitle").toString())
        jdescription.setText(intent.getStringExtra("jobdescription").toString())
        jrequirement.setText(intent.getStringExtra("jobrequirement").toString())
        jsalary.setText(intent.getStringExtra("jobsalary").toString())

        mDialog.setTitle("Updating $jobname Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateJobData(
                jobId,
                cjname.text.toString(),
                cjtitle.text.toString(),
                cjdescription.text.toString(),
                cjrequirement.text.toString(),
                cjsalary.text.toString(),
            )

            Toast.makeText(applicationContext,"Job data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to  our textviews

            cjname.text=cname.text.toString()
            cjtitle.text=jtitle.text.toString()
            cjdescription.text=jdescription.text.toString()
            cjrequirement.text=jrequirement.text.toString()
            cjsalary.text=jsalary.text.toString()

            alertDialog.dismiss()
        }


    }
    private fun updateJobData(
        id:String,
        name:String,
        title:String,
        description:String,
        requirement:String,
        salary:String,
    )
    {
        val dbRef = FirebaseDatabase.getInstance().getReference("Job").child(id)    //we are not getting reference to the hall data base
        val jobinfo = jobModel(id,name,title,description,requirement,salary)
        dbRef.setValue(jobinfo)
    }
}
//this is job details
