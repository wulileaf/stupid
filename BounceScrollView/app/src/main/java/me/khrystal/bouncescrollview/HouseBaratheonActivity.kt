package me.khrystal.bouncescrollview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.daasuu.ei.Ease
import kotlinx.android.synthetic.main.activity_house_baratheon.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 *
 * usage:
 * author: kHRYSTAL
 * create time: 18/6/25
 * update time:
 * email: 723526676@qq.com
 */
class HouseBaratheonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_baratheon)
        setSupportActionBar(mToolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        mInterpolatorNameTV.setOnClickListener {
            PickerFragment().show(supportFragmentManager, PickerFragment::class.java.simpleName)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun onInterpolatorSelected(ease: Ease) {
        mInterpolatorNameTV.text = String.format("Interpolator of bounce movement: %s", ease.name)
        mBounceScrollView.setBounceInterpolator { input -> EasingProvider.get(ease, input) }
    }

}