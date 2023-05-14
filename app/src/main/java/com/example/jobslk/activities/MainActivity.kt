package com.example.main.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.main.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnjobseeker: Button
    private lateinit var btncompany: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnjobseeker = findViewById(R.id.button10)
       // btncompany = findViewById(R.id.button11)

        btnjobseeker.setOnClickListener {
            val intent = Intent(this, landingpage::class.java )
            startActivity(intent)

        }

    //  btncompany.setOnClickListener {
      //    val intent = Intent(this, landingpage::class.java )
       //     startActivity(intent)
      //  }
    }
}