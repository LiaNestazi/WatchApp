package com.example.watchapp

import android.content.Intent
import android.icu.util.TimeZone
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.watchapp.customviews.RoundClockView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())


            val utcClock: RoundClockView = findViewById(R.id.clock_utc)
            utcClock.layoutParams = LinearLayout.LayoutParams(600, 600)
            utcClock.setTimezone(TimeZone.getTimeZone("UTC"))
            val utcText: TextView = findViewById(R.id.text_utc)
            utcText.text = "UTC clock"

            val currClock: RoundClockView = findViewById(R.id.clock_curr)
            currClock.layoutParams = LinearLayout.LayoutParams(600, 600)
            val currText: TextView = findViewById(R.id.text_curr)
            currText.text = "Current clock"

            val kstClock: RoundClockView = findViewById(R.id.clock_kst)
            kstClock.layoutParams = LinearLayout.LayoutParams(600, 600)
            kstClock.setTimezone(TimeZone.getTimeZone("GMT+9:00"))
            val kstText: TextView = findViewById(R.id.text_kst)
            kstText.text = "KST clock"

            val btnBack: Button = findViewById(R.id.btn_back)
            btnBack.setOnClickListener {
                startActivity(Intent(this@MainActivity, MainActivity4::class.java))
            }
            val btnNext: Button = findViewById(R.id.btn_next)
            btnNext.setOnClickListener {
                startActivity(Intent(this@MainActivity, MainActivity2::class.java))
            }

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            insets
        }
    }
}