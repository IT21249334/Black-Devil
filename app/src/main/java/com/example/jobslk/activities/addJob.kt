package com.example.jobslk.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.jobslk.R
import com.example.jobslk.models.jobModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class addJob : AppCompatActivity() {

    private lateinit var cname : EditText
    private lateinit var jdescription : EditText
    private lateinit var jrequirement : EditText
    private lateinit var jsalary : EditText
    private lateinit var jtitle : EditText
    private lateinit var submitjob : Button

    private lateinit var dbref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_job)

        cname = findViewById(R.id.cname)
        jdescription = findViewById(R.id.jdescription)
        jrequirement = findViewById(R.id.jrequirement)
        jsalary = findViewById(R.id.jsalary)
        jtitle = findViewById(R.id.jtitle)
        submitjob = findViewById(R.id.submitjob)

        dbref = FirebaseDatabase.getInstance().getReference("Job")

        submitjob.setOnClickListener {
            saveJobdata()
             val intent = Intent(this, Fetching::class.java)
            startActivity(intent)
        }
    }

    private fun saveJobdata()
    {
        //getting values
        val name = cname.text.toString()
        val description = jdescription.text.toString()
        val requirement = jrequirement.text.toString()
        val title = jtitle.text.toString()
        val salary = jsalary.text.toString()

        if(name.isEmpty())
        {
            cname.error = "please enter company name"
        }
        if(title.isEmpty())
        {
            jtitle.error = "please enter job title"
        }
        if(description.isEmpty())
        {
            jdescription.error = "please enter job description"
        }
        if(requirement.isEmpty())
        {
            jrequirement.error = "please enter job requirement"
        }
        if(salary.isEmpty())
        {
            jsalary.error = "please enter salary"
        }
        val jobId = dbref.push().key!!

        val job = jobModel(jobId,name,title,description,requirement,salary)

        dbref.child(jobId).setValue(job)
            .addOnCompleteListener{
                Toast.makeText(this,"inserted succesfully", Toast.LENGTH_LONG).show()

                cname.text.clear()
                jtitle.text.clear()
                jdescription.text.clear()
                jrequirement.text.clear()
                jsalary.text.clear()


            }.addOnFailureListener{err->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}
