package com.example.main.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.main.models.CompanyModel
import com.example.main.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class companyRegister : AppCompatActivity() {

    private lateinit var etComName: EditText
    private lateinit var etCountry: EditText
    private lateinit var etState: EditText
    private lateinit var etEmail: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_register)

        etComName = findViewById(R.id.editTextTextPersonName2)
        etCountry = findViewById(R.id.editTextTextPersonName3)
        etState = findViewById(R.id.editTextTextPersonName4)
        etEmail = findViewById(R.id.editTextTextEmailAddress)
        etUsername = findViewById(R.id.editTextTextPersonName5)
        etPassword = findViewById(R.id.editTextTextPassword2)
        btnRegister = findViewById(R.id.button10)

        dbRef = FirebaseDatabase.getInstance().getReference("Company")

        btnRegister.setOnClickListener {
            saveCompanyData()

            val intent = Intent(this, FetchingActivity::class.java )
            startActivity(intent)
        }
    }

    private fun saveCompanyData() {

        val comName = etComName.text.toString()
        val country = etCountry.text.toString()
        val state = etState.text.toString()
        val email = etEmail.text.toString()
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()

       if(comName.isEmpty() || country.isEmpty() || state.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
           if (comName.isEmpty()) {
               etComName.error = "please enter Company Name"
           }
           if (country.isEmpty()) {
               etCountry.error = "please enter Country"
           }
           if (state.isEmpty()) {
               etState.error = "please enter State"
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
           Toast.makeText(this, "Enter valid details", Toast.LENGTH_LONG).show()

       }else if( password.length < 6){
           etPassword.error = "Enter valid password"
           Toast.makeText(this, "Enter valid password", Toast.LENGTH_LONG).show()
       }
        val comId = dbRef.push().key!!

        val company = CompanyModel(comId, comName, country, state, email, username, password)

        dbRef.child(comId).setValue(company)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully",  Toast.LENGTH_LONG).show()

                etComName.text.clear()
                etCountry.text.clear()
                etState.text.clear()
                etEmail.text.clear()
                etUsername.text.clear()
                etPassword.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}