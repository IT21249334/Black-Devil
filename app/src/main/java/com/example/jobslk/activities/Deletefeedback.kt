package com.example.jobslk

import  android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.jobslk.models.FeedbackModel
import com.google.firebase.database.*


class   Deletefeedback : AppCompatActivity() {

    private lateinit var dname : TextView
    private lateinit var dfeedback : TextView
    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deletefeedback)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("feedbackId").toString(),
                intent.getStringExtra("feedbackname").toString()

            )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("feedbackId").toString()
            )
        }
    }
    private fun deleteRecord(
        id :String
    )
    {
        val dbRef = FirebaseDatabase.getInstance().getReference("Feedback").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Feedback data deleted!", Toast.LENGTH_LONG).show()

            val intent = Intent(this,feedbackform::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error->
            Toast.makeText(this,"Deleting ERR ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        dname = findViewById(R.id.fname) // replace with the actual id of the TextView in your layout file
        dfeedback = findViewById(R.id.ffeedback)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        dname.text= intent.getStringExtra("feedbackname")
        dfeedback.text= intent.getStringExtra("feedbackfeedback")

    }

    private fun openUpdateDialog(
        feedbackId:String,
       feedbacklocation:String
    )
    {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_updatefeedback,null)

        mDialog.setView(mDialogView)

        val fname = mDialogView.findViewById<TextView>(R.id.fname)
        val ffeedback = mDialogView.findViewById<TextView>(R.id.ffeedback)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        fname.setText(intent.getStringExtra("feedbackname").toString())
        ffeedback.setText(intent.getStringExtra("feedbackfeedback").toString())

        mDialog.setTitle("Updating $feedbacklocation Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateDonData(
                feedbackId,
                fname.text.toString(),
                ffeedback.text.toString(),
            )

            Toast.makeText(applicationContext,"Feedback data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to  our textviews

            dname.text=fname.text.toString()
            dfeedback.text=ffeedback.text.toString()

            alertDialog.dismiss()
        }


    }
    private fun updateDonData(
        id:String,
        name:String,
        feedback:String,
    )
    {
        val dbRef = FirebaseDatabase.getInstance().getReference("Feedback").child(id)    //we are not getting reference to the hall data base
        val feedbackinfo = FeedbackModel(id,name,feedback)
        dbRef.setValue(feedbackinfo)

    }

}


