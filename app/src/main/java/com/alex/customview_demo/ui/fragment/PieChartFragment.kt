package com.alex.customview_demo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alex.customview_demo.R

/**
 * @author Alex
 * @date 2019/9/6.
 * GitHub：https://github.com/wangshuaialex
 */
class PieChartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return return LayoutInflater.from(context)
            .inflate(R.layout.fragment_piechart, container, false)

    }
}