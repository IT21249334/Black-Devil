package com.example.main.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.main.R
import com.example.main.models.CompanyModel
import com.google.firebase.database.FirebaseDatabase

class companyProfile : AppCompatActivity() {

    private lateinit var tvComName: TextView
    private lateinit var tvCountry: TextView
    private lateinit var tvState: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvPassword: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_profile)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("comid").toString(),
                intent.getStringExtra("comName").toString(),
            )
        }
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("comId").toString()
            )
        }
    }

    private fun deleteRecord(
        id :String
    )
    {
        val dbRef = FirebaseDatabase.getInstance().getReference("Company").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Company data deleted!",Toast.LENGTH_LONG).show()

            val intent = Intent(this,FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error->
            Toast.makeText(this,"Deleting ERR ${error.message}",Toast.LENGTH_LONG).show()
        }
    }
    private fun initView(){

        tvComName = findViewById(R.id.textViewTextPersonName2)
        tvCountry = findViewById(R.id.textViewTextPersonName3)
        tvState = findViewById(R.id.textViewTextPersonName4)
        tvEmail = findViewById(R.id.textViewTextEmailAddress)
        tvUsername = findViewById(R.id.textViewTextPersonName5)
        tvPassword = findViewById(R.id.textViewTextPassword2)

        btnUpdate = findViewById(R.id.button10)
        btnDelete = findViewById(R.id.button11)
    }

        private fun setValuesToViews() {

            tvComName.text = intent.getStringExtra("comName")
            tvCountry.text = intent.getStringExtra("country")
            tvState.text = intent.getStringExtra("state")
            tvEmail.text = intent.getStringExtra("email")
            tvUsername.text = intent.getStringExtra("username")
            tvPassword.text = intent.getStringExtra("password")

    }


    private fun openUpdateDialog(
        comId: String,
        comName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update_dialog, null)

        mDialog.setView(mDialogView)

        val etComName = mDialogView.findViewById<EditText>(R.id.editTextTextPersonName2)
        val etCountry = mDialogView.findViewById<EditText>(R.id.editTextTextPersonName3)
        val etState = mDialogView.findViewById<EditText>(R.id.editTextTextPersonName4)
        val etEmail = mDialogView.findViewById<EditText>(R.id.editTextTextEmailAddress)
        val etUsername = mDialogView.findViewById<EditText>(R.id.editTextTextPersonName5)
        val etPassword = mDialogView.findViewById<EditText>(R.id.editTextTextPassword2)

        val btnUpdate = mDialogView.findViewById<Button>(R.id.button10)

        etComName.setText(intent.getStringExtra("comName").toString())
        etCountry.setText(intent.getStringExtra("country").toString())
        etState.setText(intent.getStringExtra("state").toString())
        etEmail.setText(intent.getStringExtra("email").toString())
        etUsername.setText(intent.getStringExtra("username").toString())
        etPassword.setText(intent.getStringExtra("password").toString())

        mDialog.setTitle("updating $comName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener {
            updateComData(
                comId,
                etComName.text.toString(),
                etCountry.text.toString(),
                etState.text.toString(),
                etEmail.text.toString(),
                etUsername.text.toString(),
                etPassword.text.toString()
            )

            Toast.makeText(applicationContext, "Company Data Updated", Toast.LENGTH_LONG).show()

            tvComName.text = etComName.text.toString()
            tvCountry.text = etCountry.text.toString()
            tvState.text = etState.text.toString()
            tvEmail.text = etEmail.text.toString()
            tvUsername.text = etUsername.text.toString()
            tvPassword.text = etPassword.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateComData(
        id: String,
        name: String,
        country: String,
        state: String,
        email: String,
        username: String,
        password: String,

        ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Company").child(id)
        val comInfo = CompanyModel(id, name, country, state, email, username, password)
        dbRef.setValue(comInfo)
    }
}