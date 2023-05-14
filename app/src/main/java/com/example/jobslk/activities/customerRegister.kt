package com.example.main.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.main.models.CustomerModel
import com.example.main.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class customerRegister : AppCompatActivity() {

    private lateinit var etCusName: EditText
    private lateinit var etCJob: EditText
    private lateinit var etSalary: EditText
    private lateinit var etEmail: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_register)

        etCusName = findViewById(R.id.editTextTextPersonName2)
        etCJob = findViewById(R.id.editTextTextPersonName3)
        etSalary = findViewById(R.id.editTextTextPersonName4)
        etEmail = findViewById(R.id.editTextTextEmailAddress)
        etUsername = findViewById(R.id.editTextTextPersonName5)
        etPassword = findViewById(R.id.editTextTextPassword2)
        btnRegister = findViewById(R.id.button10)

        dbRef = FirebaseDatabase.getInstance().getReference("Customer")

        btnRegister.setOnClickListener {
            saveCustomerData()

            val intent = Intent(this, FetchingActivity::class.java )
            startActivity(intent)
        }
    }

    private fun saveCustomerData() {

        val cusName = etCusName.text.toString()
        val cJob = etCJob.text.toString()
        val salary = etSalary.text.toString()
        val email = etEmail.text.toString()
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()


        if (cusName.isEmpty()) {
            etCusName.error = "please enter Customer Name"
        }
        if (cJob.isEmpty()) {
            etCJob.error = "please enter Current Job"
        }
        if (salary.isEmpty()) {
            etSalary.error = "please enter Salary"
        }
        if (email.isEmpty()) {
            etEmail.error = "please enter E-mail"
        }
        if (username.isEmpty()) {
            etUsername.error = "please enter Username"
        }
        if (password.isEmpty()) {
            etPassword.error = "please enter Password"
        }

        val cusId = dbRef.push().key!!

        val customer = CustomerModel(cusId, cusName, cJob, salary, email, username, password)

        dbRef.child(cusId).setValue(customer)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully",  Toast.LENGTH_LONG).show()

                etCusName.text.clear()
                etCJob.text.clear()
                etSalary.text.clear()
                etEmail.text.clear()
                etUsername.text.clear()
                etPassword.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}