package com.example.watchapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.watchapp.customviews.RoundClockView

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main4)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            val clock1: RoundClockView = findViewById(R.id.clock1)
            val clock2: RoundClockView = findViewById(R.id.clock2)
            val clock3: RoundClockView = findViewById(R.id.clock3)
            val clock4: RoundClockView = findViewById(R.id.clock4)

            clock1.setColors(
                backgroundColor = Color.BLACK,
                borderColor = Color.CYAN,
                hourHandColor = Color.CYAN,
                minuteHandColor = Color.CYAN,
                secondHandColor = Color.RED,
                numeralsColor = Color.CYAN
            )

            clock2.setColors(
                borderColor = Color.GREEN,
                numeralsColor = Color.GREEN,
                secondHandColor = Color.GREEN
            )

            clock3.setColors(
                backgroundColor = Color.GRAY,
                borderColor = Color.RED,
                hourHandColor = Color.RED,
                numeralsColor = Color.RED
            )
            clock4.setColors(
                borderColor = Color.GRAY,
                secondHandColor = Color.RED,
                numeralsColor = Color.GRAY
            )

            val btnBack: Button = findViewById(R.id.btn_back)
            btnBack.setOnClickListener {
                startActivity(Intent(this@MainActivity4, MainActivity3::class.java))
            }
            val btnNext: Button = findViewById(R.id.btn_next)
            btnNext.setOnClickListener {
                startActivity(Intent(this@MainActivity4, MainActivity::class.java))
            }


            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}