package me.khrystal.eyepetizer.ui

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_find_detail.*
import me.khrystal.eyepetizer.R
import me.khrystal.eyepetizer.adapter.RankAdapter
import me.khrystal.eyepetizer.mvp.contract.FindDetailContract
import me.khrystal.eyepetizer.mvp.model.bean.HotBean
import me.khrystal.eyepetizer.mvp.presenter.FindDetailPresenter
import java.util.regex.Pattern

/**
 *
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/20
 * update time:
 * email: 723526676@qq.com
 */
class FindDetailActivity : AppCompatActivity(), FindDetailContract.View, SwipeRefreshLayout.OnRefreshListener {

    lateinit var mPresenter: FindDetailPresenter
    lateinit var mAdapter: RankAdapter
    lateinit var data: String

    var mIsRefresh: Boolean = false
    var mList: ArrayList<HotBean.ItemListBean.DataBean> = ArrayList();
    var mStart: Int = 10
    lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_detail)
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init()
        setToolbar()
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = RankAdapter(this, mList)
        recyclerView.adapter = mAdapter
        refreshLayout.setOnRefreshListener(this)
        mPresenter = FindDetailPresenter(this, this)
        mPresenter.requestData(name, "date")

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var layoutManager: LinearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
                var lastPositon = layoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPositon == mList.size - 1) {
                    if (data != null) {
                        mPresenter?.requestMoreData(mStart, name, "date")
                        mStart = mStart.plus(10)
                    }

                }
            }
        })
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        var bar = supportActionBar
        intent.getStringExtra("name")?.let {
            name = intent.getStringExtra("name")
            bar?.title = name
        }
        bar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onRefresh() {
        if (!mIsRefresh) {
            mIsRefresh = true
            mPresenter.requestData(name, "date")
        }
    }

    override fun setData(bean: HotBean) {
        val regEx = "[^0-9]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(bean.nextPageUrl as CharSequence?)
        data = m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()
        if (mIsRefresh) {
            mIsRefresh = false
            refreshLayout.isRefreshing = false
            if (mList.size > 0) {
                mList.clear()
            }

        }
        bean.itemList?.forEach {
            it.data?.let { it1 -> mList.add(it1) }
        }
        mAdapter.notifyDataSetChanged()
    }

}