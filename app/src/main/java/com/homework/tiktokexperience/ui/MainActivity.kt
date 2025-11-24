package com.homework.tiktokexperience.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.homework.tiktokexperience.R

class MainActivity : AppCompatActivity() {
    init {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val viewGroup = findViewById<View>(R.id.bottom_navi_menu) as ViewGroup
        for (i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)
            if (child.id == R.id.home) {
                child.isSelected = true
            }
            if (child.id != R.id.add) {
                child.setOnClickListener(BottomOnClickListener)
            }
        }

    }

    object BottomOnClickListener : View.OnClickListener {
        override fun onClick(v: View) {
            val group = v.parent as ViewGroup
            for (i in 0 until group.childCount) {
                val child = group.getChildAt(i)
                if (child.id != R.id.add) {
                    child.isSelected = false
                }
            }
            v.isSelected = true
        }
    }
}