package com.homework.tiktokexperience.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.homework.tiktokexperience.R
import com.homework.tiktokexperience.ui.card.CardBean
import com.homework.tiktokexperience.ui.card.TestAdapter
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
        initCard()
    }

    private fun initCard() {
        var cardBean = CardBean("",
            "https://picsum.photos/200/300",
            1,1,
            "标题",
            "https://picsum.photos/200/300",
            "用户名",
            9999
        )
        val listOf = listOf(cardBean, cardBean, cardBean, cardBean, cardBean)
        val recyclerView = findViewById<RecyclerView>(R.id.card_recyclerview).apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            adapter = TestAdapter(context,listOf)
        }
    }

    private fun initTop() {
        //reacyclerview初始化
        val menuText = listOf("精选", "经验", "直播", "热点", "推荐", "商城", "本地")//可改
        val linearLayoutManager =
            LinearLayoutManager(this).apply { orientation = LinearLayoutManager.HORIZONTAL }
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
        view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                checkPosition()
            }

            private fun checkPosition() {
                // 获取最后一个完全可见的item位置
                val lastPosition: Int =
                    linearLayoutManager.findLastCompletelyVisibleItemPosition()
                val itemCount = linearLayoutManager.itemCount
                val isAtEnd = (lastPosition == itemCount - 1)
                if (isAtEnd) {
                    // 已到达末尾，隐藏箭头
                    this@MainActivity.findViewById<ImageView>(R.id.arrow).visibility = View.GONE
                } else {
                    // 未到达末尾，显示箭头
                    this@MainActivity.findViewById<ImageView>(R.id.arrow).visibility = View.VISIBLE
                }

            }
        })
        //侧面菜单监听
        findViewById<ImageView>(R.id.top_side_menu).apply {
            setOnClickListener {
                val drawerLayout =
                    this@MainActivity.findViewById<View>(R.id.drawer_layout) as DrawerLayout
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
            }
        }
        //搜索按钮设置
        findViewById<ImageView>(R.id.top_search).apply {}
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