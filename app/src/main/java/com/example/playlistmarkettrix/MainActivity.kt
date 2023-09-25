package com.example.playlistmarkettrix

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSearch = findViewById<Button>(R.id.button_search)
        val btnLib = findViewById<Button>(R.id.button_library)
        val btnSettings = findViewById<Button>(R.id.button_settings)

        val imageClickListener: View.OnClickListener = object  : View.OnClickListener{
            override fun onClick(p0: View?) {
                Toast.makeText(this@MainActivity, "кнопка search", Toast.LENGTH_SHORT).show()
            }
        }

        btnSearch.setOnClickListener(imageClickListener)
        btnLib.setOnClickListener {
            Toast.makeText(this, "кнопка lib", Toast.LENGTH_SHORT).show()
        }
        btnSettings.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        Toast.makeText(this, "кнопка sett", Toast.LENGTH_SHORT).show()
    }
}