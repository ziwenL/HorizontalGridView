# HorizontalGridView <a href="https://blog.csdn.net/lzw398756924/article/details/105731426" rel="nofollow">博客地址</a>
<h3 align="center">基本介绍</h3>
<p align="center" blod=true >HorizontalGridView是一个仿照 直播间礼物列表/电商首页二级分类栏 实现横向滚动的自定view</p>

<h6>显示效果</h6>
<img  src="https://helloimg.com/images/2020/05/14/2020042415374746157e99a970e0331fd.gif?raw=true" alt="显示效果" />

<h6>功能说明</h6>
<ul>
<li>
<p>该 View 继承于LinearLayout,内部含有 ViewPager + TabLayout ，并通过将 RecyclerView 作为 pageView 填充到 ViewPager 中,实现 Grid + 横向滑动 效果</p>
</li>
<li>
<p>1.HorizontalGridView 高度应设为 wrap_content 由内容决定(因为已处理 ViewPager 高度默认为 match_parent 问题)</p>
</li>
<li>
<p>2.通过 pageDisplaysCount 设置每页展示总数，通过 pageSpanCount 设置每行展示数目</p>
</li>
<li>
<p>3.可通过将圆角 shape 资源设置到 indicatorBackground 属性中，实现带圆角的指示器效果（ SDK 低于24时无法获取 shape 资源内 solid 填充颜色，此时指示器颜色应通过 indicatorBackgroundColor 属性设置）</p>
</li>
<li>
<p>4.可通过将圆角 shape 资源设置到 tabBackground 属性中，实现带圆角效果的 tabLayout </p>
</li>
<li>
<p>5.通过 tabLayoutHeight 属性设置 tabLayout 高度和指示器高度(不设置或为0表示隐藏 tabLayout )，通过 tabLayoutWidth 属性设置 tabLayout 宽度（指示器宽度由 tabLayout 宽度和 ViewPager 页数共同决定）</p>
</li>
<li>
<p>6.通过 tabLayoutInterval 属性设置 tabLayout 与 ViewPager 的间隔距离</p>
</li>
<li>
<p>7.通过 HorizontalGridView.Adapter 对象 notifyDataSetChange() 方法通知更新内容</p>
</li>
<li>
<p>8.通过 HorizontalGridView 对象 setOnClickItemListener(HorizontalGridView.OnClickItemListener onClickItemListener) 方法设置item点击事件</p>
</li>
</ul>

<h6>使用说明</h6>
<ul>
<li>
<p>1.确定项目已<a href="https://github.com/ziwenL/HorizontalGridView/blob/master/app/build.gradle" rel="nofollow">导入 Google Material 包</a></p>
</li>
<li>
<p>2.复制类 <a href="https://github.com/ziwenL/HorizontalGridView/blob/master/app/src/main/java/com/ziwenl/horizontalgridview/widgets/HorizontalGridView.java" rel="nofollow">HorizontalGridView</a> 到项目中，并将其<a href="https://github.com/ziwenL/HorizontalGridView/blob/master/app/src/main/res/values/attrs.xml" rel="nofollow">自定义属性</a>复制至 attrs 中</p>
</li>
<li>
<p>3.布局中引用 HorizontalGridView ，同时设置 pageDisplaysCount 与 pageSpanCount ，其余自定义属性按需设置</p>
</li>
<li>
<p>4.自定义 Adapter 继承于 HorizontalGridView.Adapter<T> ( T 为业务数据 bean ) ， 重写构造方法、onBindItemLayout() 和 onBindViewHolder() 方法</p>
</li>
<li>
<p>5.在 onBindItemLayout() 方法中返回指定的 itemLayout.xml ，在 onBindViewHolder() 方法中设置显示 View 内容</p>
</li>
<li>
<p>6.通过 horizontalGridView.setAdapter() 方法传入该 Adapter</p>
</li>
<li>
<p>7.通过 adapter.getData() 方法获取数据集并对数据集进行操作</p>
</li>
<li>
<p>8.通过 adapter.notifyDataSetChange() 方法通知更新内容</p>
</li>
<li>
<p>9.通过 horizontalGridView.setOnClickItemListener() 方法添加item点击监听</p>
</li>
<li>
<p>具体可参考 <a href="https://github.com/ziwenL/HorizontalGridView/blob/master/app/src/main/java/com/ziwenl/horizontalgridview/MainActivity.kt" rel="nofollow">MainActivity</a> 、 <a href="https://github.com/ziwenL/HorizontalGridView/blob/master/app/src/main/res/layout/activity_main.xml" rel="nofollow">activity_main</a> 、 <a href="https://github.com/ziwenL/HorizontalGridView/blob/master/app/src/main/java/com/ziwenl/horizontalgridview/MainAdapter.kt" rel="nofollow">MainAdapter</a> 和 <a href="https://github.com/ziwenL/HorizontalGridView/blob/master/app/src/main/res/layout/item_test.xml" rel="nofollow">item_test</a> </p>
</li>
</ul>

<h3>About Me<h3>
<ul>
<li>
<p>Email: ziwen.lan@foxmail.com</p>
</li>
</ul>
