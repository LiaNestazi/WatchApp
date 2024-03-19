package com.example.watchapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MainActivity3 : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val todayTxtView: TextView = findViewById(R.id.today_text)

            val calendar = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val currentDate =
                    calendar.time
            todayTxtView.text = sdf.format(currentDate)

            val btnBack: Button = findViewById(R.id.btn_back)
            btnBack.setOnClickListener {
                startActivity(Intent(this@MainActivity3, MainActivity2::class.java))
            }
            val btnNext: Button = findViewById(R.id.btn_next)
            btnNext.setOnClickListener {
                startActivity(Intent(this@MainActivity3, MainActivity4::class.java))
            }

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}