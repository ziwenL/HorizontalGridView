package com.ziwenl.horizontalgridview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ziwenl.horizontalgridview.utils.NetImageUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MainAdapter(NetImageUtil.getUrls(50))
        hgv.setAdapter(adapter)

        hgv.setOnClickItemListener { realPosition ->
            Toast.makeText(this, realPosition.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
