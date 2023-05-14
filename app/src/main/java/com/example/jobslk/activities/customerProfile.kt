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
import com.example.main.models.CustomerModel
import com.google.firebase.database.FirebaseDatabase

class customerProfile : AppCompatActivity() {

    private lateinit var tvCusName: TextView
    private lateinit var tvCJob: TextView
    private lateinit var tvSalary: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvPassword: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_profile)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("cusid").toString(),
                intent.getStringExtra("cusName").toString(),
            )
        }
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("cusId").toString()
            )
        }
    }

    private fun deleteRecord(
        id :String
    )
    {
        val dbRef = FirebaseDatabase.getInstance().getReference("Customer").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Customer data deleted!",Toast.LENGTH_LONG).show()

            val intent = Intent(this,FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error->
            Toast.makeText(this,"Deleting ERR ${error.message}",Toast.LENGTH_LONG).show()
        }
    }
    private fun initView(){

        tvCusName = findViewById(R.id.textViewTextPersonName2)
        tvCJob = findViewById(R.id.textViewTextPersonName3)
        tvSalary = findViewById(R.id.textViewTextPersonName4)
        tvEmail = findViewById(R.id.textViewTextEmailAddress)
        tvUsername = findViewById(R.id.textViewTextPersonName5)
        tvPassword = findViewById(R.id.textViewTextPassword2)

        btnUpdate = findViewById(R.id.button10)
        btnDelete = findViewById(R.id.button11)
    }

        private fun setValuesToViews() {

            tvCusName.text = intent.getStringExtra("cusName")
            tvCJob.text = intent.getStringExtra("cJob")
            tvSalary.text = intent.getStringExtra("salary")
            tvEmail.text = intent.getStringExtra("email")
            tvUsername.text = intent.getStringExtra("username")
            tvPassword.text = intent.getStringExtra("password")

    }


    private fun openUpdateDialog(
        cusId: String,
        cusName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update_dialog, null)

        mDialog.setView(mDialogView)

        val etCusName = mDialogView.findViewById<EditText>(R.id.editTextTextPersonName2)
        val etCJob = mDialogView.findViewById<EditText>(R.id.editTextTextPersonName3)
        val etSalary = mDialogView.findViewById<EditText>(R.id.editTextTextPersonName4)
        val etEmail = mDialogView.findViewById<EditText>(R.id.editTextTextEmailAddress)
        val etUsername = mDialogView.findViewById<EditText>(R.id.editTextTextPersonName5)
        val etPassword = mDialogView.findViewById<EditText>(R.id.editTextTextPassword2)

        val btnUpdate = mDialogView.findViewById<Button>(R.id.button10)

        etCusName.setText(intent.getStringExtra("cusName").toString())
        etCJob.setText(intent.getStringExtra("cJob").toString())
        etSalary.setText(intent.getStringExtra("salary").toString())
        etEmail.setText(intent.getStringExtra("email").toString())
        etUsername.setText(intent.getStringExtra("username").toString())
        etPassword.setText(intent.getStringExtra("password").toString())

        mDialog.setTitle("updating $cusName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener {
            updateCusData(
                cusId,
                etCusName.text.toString(),
                etCJob.text.toString(),
                etSalary.text.toString(),
                etEmail.text.toString(),
                etUsername.text.toString(),
                etPassword.text.toString()
            )

            Toast.makeText(applicationContext, "Customer Data Updated", Toast.LENGTH_LONG).show()

            tvCusName.text = etCusName.text.toString()
            tvCJob.text = etCJob.text.toString()
            tvSalary.text = etSalary.text.toString()
            tvEmail.text = etEmail.text.toString()
            tvUsername.text = etUsername.text.toString()
            tvPassword.text = etPassword.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateCusData(
        id: String,
        name: String,
        cJob: String,
        salary: String,
        email: String,
        username: String,
        password: String,

        ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Customer").child(id)
        val cusInfo = CustomerModel(id, name, cJob, salary, email, username, password)
        dbRef.setValue(cusInfo)
    }
}