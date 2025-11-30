# TikTokExperience

一个模仿抖音首页瀑布流的 Android 作业 Demo 项目，通过本地 Mock 数据模拟推荐流和点赞交互。

## 功能特性

-  **瀑布流卡片列表**
    - 使用 `StaggeredGridLayoutManager` 实现图片瀑布流效果
    - 支持上拉加载更多 + 简单预加载

-  **下拉刷新 & 分页加载**
    - 使用 `SwipeRefreshLayout` 实现下拉刷新
    - 滚动到底部自动加载下一页数据

-  **点赞（爱心）功能**
    - 卡片内爱心按钮支持点击点赞/取消点赞
    - 通过网络请求（Mock）决定是否真正更新 UI
    - 点赞接口存在一定失败概率，用于模拟真实网络环境

-  **双击切换布局**
    - 在卡片列表区域双击屏幕
    - 一键在 **1 列大图** 和 **2 列瀑布流** 间切换

-  **顶部菜单栏**
    - 顶部横向菜单（如：精选、经验、直播、热点、推荐、商城、本地）
    - 滚动到末尾时隐藏右侧箭头提示

-  **侧滑抽屉菜单**
    - 使用 `DrawerLayout` 实现左侧侧滑菜单
    - 通过顶部左侧菜单图标打开侧边栏

-  **底部导航栏**
    - 仿抖音底部导航（首页、消息、我的等）
    - 点击切换选中状态（示例中未切换 Fragment，仅样式）

---

## 技术栈

- 语言：**Kotlin**
- 架构模式：**MVVM**
- UI：
    - `RecyclerView` + `StaggeredGridLayoutManager`
    - `SwipeRefreshLayout`
    - `DrawerLayout`
- 网络：
    - `Retrofit`
    - `OkHttp` + 自定义 `Interceptor`（`MockInterceptor`）
    - `GsonConverterFactory`
- 图片加载：
    - **Glide**（圆形头像 + 列表图片 + 预加载）


---

