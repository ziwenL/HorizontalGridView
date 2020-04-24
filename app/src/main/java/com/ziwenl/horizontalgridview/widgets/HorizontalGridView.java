package com.ziwenl.horizontalgridview.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ziwenl.horizontalgridview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : Ziwen Lan
 * Date : 2020/4/20
 * Time : 9:53
 * Introduction : 横向滑动列表view
 */
public class HorizontalGridView extends LinearLayout {
    private final int DEFAULT_PAGE_DISPLAYS_COUNT = 10;
    private final int DEFAULT_SPAN_COUNT = 5;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private List<RecyclerView> mDataViewList;
    private Adapter mAdapter;
    private AdapterDataObserver mAdapterDataObserver;
    /**
     * 每页显示数目
     */
    private int mPageDisplaysCount;
    /**
     * 每行显示数目
     */
    private int mSpanCount;

    private OnClickItemListener mOnClickItemListener;

    public HorizontalGridView(@NonNull Context context) {
        this(context, null);
    }

    public HorizontalGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalGridView, defStyleAttr, 0);
        Drawable indicatorDrawable = typedArray.getDrawable(R.styleable.HorizontalGridView_indicatorBackground);
        int indicatorBackgroundColor = typedArray.getColor(R.styleable.HorizontalGridView_indicatorBackgroundColor, Color.TRANSPARENT);
        int tabHeight = typedArray.getDimensionPixelOffset(R.styleable.HorizontalGridView_tabLayoutHeight, 0);
        int tabWidth = typedArray.getDimensionPixelOffset(R.styleable.HorizontalGridView_tabLayoutWidth, 0);
        int tabInterval = typedArray.getDimensionPixelOffset(R.styleable.HorizontalGridView_tabLayoutInterval, 0);
        Drawable tabBackground = typedArray.getDrawable(R.styleable.HorizontalGridView_tabBackground);

        mPageDisplaysCount = typedArray.getInt(R.styleable.HorizontalGridView_pageDisplaysCount, DEFAULT_PAGE_DISPLAYS_COUNT);
        mSpanCount = typedArray.getInt(R.styleable.HorizontalGridView_pageSpanCount, DEFAULT_SPAN_COUNT);
        typedArray.recycle();

        //init TabLayout
        mTabLayout = new TabLayout(context);
        LayoutParams tabParams = new LayoutParams(tabWidth, tabHeight);
        tabParams.topMargin = tabInterval;
        mTabLayout.setLayoutParams(tabParams);
        mTabLayout.setSelectedTabIndicatorHeight(tabHeight);
        mTabLayout.setSelectedTabIndicator(indicatorDrawable);
        if (indicatorDrawable instanceof ColorDrawable) {
            mTabLayout.setSelectedTabIndicatorColor(((ColorDrawable) indicatorDrawable).getColor());
        } else if (indicatorDrawable instanceof GradientDrawable
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && ((GradientDrawable) indicatorDrawable).getColor() != null) {
            mTabLayout.setSelectedTabIndicatorColor(((GradientDrawable) indicatorDrawable).getColor().getDefaultColor());
        } else {
            //SDK小于24时，无法获得shape内的颜色，此时使用indicatorBackgroundColor
            mTabLayout.setSelectedTabIndicatorColor(indicatorBackgroundColor);
        }
        mTabLayout.setBackground(tabBackground);
        mTabLayout.setVisibility(GONE);

        //init ViewPager
        mViewPager = new ViewPager(context);
        LayoutParams vpParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mViewPager.setLayoutParams(vpParams);
        mViewPager.setOverScrollMode(OVER_SCROLL_NEVER);
        mDataViewList = new ArrayList<>();
        mPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mDataViewList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(mDataViewList.get(position));
                return mDataViewList.get(position);
            }
        };
        mViewPager.setAdapter(mPagerAdapter);

        //init this LinearLayout
        mAdapterDataObserver = new AdapterDataObserver(mViewPager);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        addView(mViewPager);
        addView(mTabLayout);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //固定viewpager高度
        int vpHeight = 0;
        for (int i = 0; i < mViewPager.getChildCount(); i++) {
            View child = mViewPager.getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > vpHeight) {
                vpHeight = h;
            }
        }
        mViewPager.getLayoutParams().height = vpHeight;
        mViewPager.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(vpHeight, heightMode));

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * 更新数据
     */
    void notifyDataSetChange() {
        if (mAdapter == null) {
            return;
        }
        if (mAdapter.mData == null || mAdapter.mData.isEmpty()) {
            mDataViewList.clear();
            mPagerAdapter.notifyDataSetChanged();
            mTabLayout.setVisibility(GONE);
            return;
        } else {
            mAdapter.mData.size();
        }
        //总的页数向上取整
        List totalData = mAdapter.mData;
        int totalPage = (int) Math.ceil(totalData.size() * 1.0 / mPageDisplaysCount);
        int oldPage = mDataViewList.size();
        for (int i = 0; i < totalPage; i++) {
            //获取子列表
            List subList = new ArrayList<>();
            if ((i + 1) * mPageDisplaysCount > totalData.size()) {
                subList.addAll(totalData.subList(i * mPageDisplaysCount, totalData.size()));
            } else {
                subList.addAll(totalData.subList(i * mPageDisplaysCount, (i + 1) * mPageDisplaysCount));
            }
            if (mDataViewList.size() < i + 1) {
                GridLayoutManager manager = new GridLayoutManager(getContext(), mSpanCount);
                RecyclerView recyclerView = new RecyclerView(getContext());
                LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                recyclerView.setLayoutParams(layoutParams);
                recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
                recyclerView.setLayoutManager(manager);
                ChildAdapter childAdapter = new ChildAdapter(subList, i);
                recyclerView.setAdapter(childAdapter);
                mDataViewList.add(recyclerView);
            } else {
                ChildAdapter childAdapter = (ChildAdapter) mDataViewList.get(i).getAdapter();
                childAdapter.mData.clear();
                childAdapter.mData.addAll(subList);
                childAdapter.notifyDataSetChanged();
            }
        }
        if (totalPage < mDataViewList.size()) {
            mDataViewList = mDataViewList.subList(0, totalPage);
        }
        int current;
        if (totalPage >= oldPage) {
            current = mViewPager.getCurrentItem();
        } else {
            current = Math.min(mViewPager.getCurrentItem(), totalPage);
        }
        //不采用 ViewPagerAdapter.notifyDataSetChange 方法更新数据，防止页数减少时，还能看到已经被销毁的页面
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        //mViewPager.setOffscreenPageLimit(mDataViewList.size());
        mViewPager.setCurrentItem(current);
        mTabLayout.setVisibility(VISIBLE);
    }

    /**
     * 设置适配器,绑定观察者对象
     */
    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        mAdapter.setAdapterDataObserver(mAdapterDataObserver);
        notifyDataSetChange();
    }

    //----------  RecyclerViewAdapter  ----------

    private class ChildAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List mData;
        private int mPageNumber;
        private OnClickListener mOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                int realPosition = mPageNumber * mPageDisplaysCount + position;
                if (mOnClickItemListener != null) {
                    mOnClickItemListener.onClickItemListener(realPosition);
                }
            }
        };

        ChildAdapter(List data, int pageNumber) {
            mData = data;
            mPageNumber = pageNumber;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(mAdapter.getLayoutResource(), parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            //添加点击监听
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(mOnClickListener);
            int realPosition = mPageNumber * mPageDisplaysCount + position;
            //暴露数据
            mAdapter.onBindViewHolder(holder, mData.get(position), position, realPosition);

        }

        @Override
        public int getItemCount() {
            return mData == null || mData.isEmpty() ? 0 : mData.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    //----------  Adapter  ----------

    private class AdapterDataObserver {
        private ViewPager mViewPager;

        AdapterDataObserver(ViewPager viewPager) {
            mViewPager = viewPager;
        }

        void onChange() {
            notifyDataSetChange();
        }
    }

    public static abstract class Adapter<T> {
        private List<T> mData;
        private AdapterDataObserver mAdapterDataObserver;

        public Adapter(List<T> data) {
            mData = data;
        }

        /**
         * 绑定观察者
         */
        void setAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            mAdapterDataObserver = adapterDataObserver;
        }

        /**
         * 绑定itemLayout
         */
        @LayoutRes
        public abstract int onBindItemLayout();

        /**
         * 此方法应更新{@link ViewHolder＃itemView}的内容，以将项目反映在给定的位置
         *
         * @param holder          viewHolder
         * @param dto             data
         * @param currentPosition 于当前页面所在的位置
         * @param realPosition    于所有数据所在真实位置
         */
        public abstract void onBindViewHolder(@NonNull ViewHolder holder, @NonNull T dto, int currentPosition, int realPosition);

        @LayoutRes
        int getLayoutResource() {
            return onBindItemLayout();
        }

        /**
         * 更新数据
         */
        public final void notifyDataSetChange() {
            mAdapterDataObserver.onChange();
        }

        public List<T> getData() {
            return mData;
        }
    }

    //----------  监听  ----------

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        mOnClickItemListener = onClickItemListener;
    }

    public interface OnClickItemListener {
        void onClickItemListener(int realPosition);
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mViewPager.addOnPageChangeListener(onPageChangeListener);
    }
}
