package com.homework.tiktokexperience.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.homework.tiktokexperience.R
import com.homework.tiktokexperience.ui.top.TopMenuAdapter

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
        initBottom()
        initTop()
    }

    private fun initTop() {
        val menuText = listOf("精选", "经验", "直播", "热点", "推荐", "商城", "本地")//可改
        val linearLayoutManager = LinearLayoutManager(this).apply { orientation = LinearLayoutManager.HORIZONTAL }
        val topMenuAdapter = TopMenuAdapter(menuText)
        val view = findViewById<RecyclerView>(R.id.top_recyclerview).apply {
            layoutManager = linearLayoutManager
            adapter = topMenuAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.left = 35
                    outRect.right = 35
                }
            })
        }
    }

    private fun initBottom() {
        //底部导航
        //todo 可以设置fragment进行切换
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