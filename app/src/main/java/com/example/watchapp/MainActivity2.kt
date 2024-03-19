package com.example.watchapp

import android.content.Intent
import android.icu.util.TimeZone
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.watchapp.customviews.RoundClockView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            val clock: RoundClockView = findViewById(R.id.clock)
            clock.layoutParams = LinearLayout.LayoutParams(700, 700)

            val btnFrance: Button = findViewById(R.id.france_btn)
            val btnRussia: Button = findViewById(R.id.russia_btn)
            val btnChina: Button = findViewById(R.id.china_btn)
            val btnJapan: Button = findViewById(R.id.japan_btn)

            btnFrance.setOnClickListener {
                clock.setTimezone(TimeZone.getTimeZone("GMT+1:00"))
            }
            btnRussia.setOnClickListener {
                clock.setTimezone(TimeZone.getTimeZone("GMT+3:00"))
            }
            btnChina.setOnClickListener {
                clock.setTimezone(TimeZone.getTimeZone("GMT+8:00"))
            }
            btnJapan.setOnClickListener {
                clock.setTimezone(TimeZone.getTimeZone("GMT+9:00"))
            }


            val btnBack: Button = findViewById(R.id.btn_back)
            btnBack.setOnClickListener {
                startActivity(Intent(this@MainActivity2, MainActivity::class.java))
            }
            val btnNext: Button = findViewById(R.id.btn_next)
            btnNext.setOnClickListener {
                startActivity(Intent(this@MainActivity2, MainActivity3::class.java))
            }

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}