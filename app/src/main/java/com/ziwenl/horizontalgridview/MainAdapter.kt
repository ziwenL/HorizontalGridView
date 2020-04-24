package com.ziwenl.horizontalgridview

import com.bumptech.glide.Glide
import com.ziwenl.horizontalgridview.widgets.HorizontalGridView
import kotlinx.android.synthetic.main.item_test.view.*

/**
 * Author : Ziwen Lan
 * Date : 2020/4/22
 * Time : 17:14
 * Introduction :
 */
class MainAdapter(data: List<String>) : HorizontalGridView.Adapter<String>(data) {
    override fun onBindItemLayout(): Int {
        return R.layout.item_test
    }

    override fun onBindViewHolder(
        holder: HorizontalGridView.ViewHolder,
        dto: String,
        currentPosition: Int,
        realPosition: Int
    ) {
        Glide.with(holder.itemView.context)
            .load(dto)
            .into(holder.itemView.iv_picture)
        holder.itemView.tv_content.text = "当前$realPosition"

    }
}