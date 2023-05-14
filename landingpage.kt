package com.example.main.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.main.R

class landingpage : AppCompatActivity() {

    private lateinit var btnsignin: Button
    private lateinit var btnregisternow: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landingpage)

        btnsignin = findViewById(R.id.button8)
        btnregisternow = findViewById(R.id.button9)

        btnsignin.setOnClickListener {
            val intent = Intent(this, FetchingActivity::class.java )
            startActivity(intent)
        }
        btnregisternow.setOnClickListener {
            val intent = Intent(this, companyRegister::class.java )
            startActivity(intent)
        }
    }
}