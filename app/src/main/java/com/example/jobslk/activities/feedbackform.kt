package com.example.jobslk


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.jobslk.models.FeedbackModel
import com.example.jobslk.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.*


class feedbackform : AppCompatActivity() {


    private lateinit var fname : EditText
    private lateinit var femail : EditText
    private lateinit var fmessage : EditText
    private lateinit var fusername : EditText
    private lateinit var fpassword : EditText
    private lateinit var submit_area : Button

    private lateinit var dbref : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedbackform)

            fname = findViewById(R.id.fname)
            femail = findViewById(R.id.femail)
            fmessage = findViewById(R.id.fmessage)
            fusername = findViewById(R.id.fusername)
            fpassword = findViewById(R.id.fpassword)
            submit_area = findViewById(R.id.submit_area)

            dbref = FirebaseDatabase.getInstance().getReference("feedback")

            submit_area.setOnClickListener {
                saveFeedbackdata()

                val intent = Intent(this, feedbacks::class.java)
                startActivity(intent)
            }
        }

        private fun saveFeedbackdata()
        {
            //getting values
            val Name = fname.text.toString()
            val Email = femail.text.toString()
            val Message = fmessage.text.toString()
            val Username = fusername.text.toString()
            val Password = fpassword.text.toString()

            if(Name.isEmpty())
            {
                fname.error = "please enter Name"
            }
            if(Email.isEmpty())
            {
                femail.error = "please enter Email"
            }
            if(Message.isEmpty())
            {
                fmessage.error = "please enter Message"
            }
            if(Username.isEmpty())
            {
                fusername.error = "please enter Username"
            }
            if(Password.isEmpty())
            {
                fpassword.error = "please enter Password"
            }
            val feedbackId = dbref.push().key!!

            val feedback = FeedbackModel(feedbackId,Name,Email,Message,Username,Password)

            dbref.child(feedbackId).setValue(feedback)
                .addOnCompleteListener{
                    Toast.makeText(this,"inserted succesfully", Toast.LENGTH_LONG).show()

                    fname.text.clear()
                    femail.text.clear()
                    fmessage.text.clear()
                    fusername.text.clear()
                    fpassword.text.clear()


                }.addOnFailureListener{err->
                    Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
                }

    }
}