package com.homework.tiktokexperience

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.homework.tiktokexperience.ui.card.CardAdapter
import com.homework.tiktokexperience.ui.top.TopMenuAdapter
import com.homework.tiktokexperience.viewmodel.MainViewModel
import com.homework.tiktokexperience.viewmodel.State

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
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
//        var cardBean = CardBean("",
//            "https://picsum.photos/200/300",
//            1,1,
//            "标题",
//            "https://picsum.photos/200/300",
//            "用户名",
//            9999
//        )
//        val listOf = listOf(cardBean, cardBean, cardBean, cardBean, cardBean)
        val cardAdapter: CardAdapter
        val recyclerView = findViewById<RecyclerView>(R.id.card_recyclerview).apply {
            val layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            this.layoutManager = layoutManager
            cardAdapter = CardAdapter(context) { view, cardBean ->
                viewModel.touchLove(
                    view,
                    cardBean
                )
            }              // todo 点击加减爱心事件
            adapter = cardAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val intArray = IntArray(2)
                    layoutManager.findLastVisibleItemPositions(intArray)
                    if (intArray.maxOrNull() == cardAdapter.itemCount - 1) {
                        viewModel.loadData()
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }//todo滑动条件变化
            })
        }

        viewModel.loadData()//init
        Log.d("MainActivity", "initCard:viewMode_linit")
        viewModel.state.observe(this) {
            when (it) {
                is State.success -> {
                    Log.d("MainActivity", "initCard:state_success")
                    cardAdapter.submitList(it.items)
                    Log.d("MainActivity", "initCard:${it.items.toString()}")
                    findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout).isRefreshing = false
                }

                is State.Loading -> {//todo loading显示一定时常自动停止

                }

                is State.Error -> {
                    findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout).isRefreshing = false
                }
            }

        }
        findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout).setOnRefreshListener {
            viewModel.loadData(true)
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