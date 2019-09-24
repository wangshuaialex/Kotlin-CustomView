package com.alex.customview_demo.ui.fragment

import androidx.fragment.app.Fragment
import android.content.Intent
import android.os.Bundle
import butterknife.ButterKnife
import android.view.ViewGroup
import android.view.LayoutInflater
import android.app.Activity
import android.view.View
import androidx.annotation.Nullable


/**
 * @author Alex
 * @date 2019/9/24.
 * GitHub：https://github.com/wangshuaialex
 */
public abstract class BaseFragment() : Fragment() {
     //var view: View? = null

    var mContext: Activity? = null

    /**
     * 是否初始化过布局
     */
    protected var isViewInitiated: Boolean = false
    /**
     * 当前界面是否可见
     */
    protected var isVisibleToUser: Boolean = false
    /**
     * 是否加载过数据
     */
    protected var isDataInitiated: Boolean = false


    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(getLayoutId(), container, false)
        ButterKnife.bind(this, view)
        mContext = activity

        this.initData()
        return view
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        //加载数据
        prepareFetchData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        if (isVisibleToUser) {
            prepareFetchData()
        }
    }

    fun prepareFetchData() {
        prepareFetchData(false)
    }

    /**
     * 判断懒加载条件
     *
     * @param forceUpdate 强制更新
     */
    fun prepareFetchData(forceUpdate: Boolean) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            loadData()
            isDataInitiated = true
        }
    }

    /**
     * 懒加载
     */
    protected abstract fun loadData()

    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 数据初始化操作
     */
    protected abstract fun initData()


    override fun onDestroy() {
        super.onDestroy()
        //this.view = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ButterKnife.bind(activity!!).unbind()
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    fun startActivity(clz: Class<*>) {
        startActivity(clz, null)
    }


    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent()
        intent.setClass(activity!!, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }


    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    fun startActivityForResult(
        cls: Class<*>, bundle: Bundle?,
        requestCode: Int
    ) {
        val intent = Intent()
        intent.setClass(activity!!, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }
}