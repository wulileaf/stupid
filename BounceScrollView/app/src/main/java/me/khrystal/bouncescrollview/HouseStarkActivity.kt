package me.khrystal.bouncescrollview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.xw.repo.BubbleSeekBar
import kotlinx.android.synthetic.main.activity_house_stark.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 *
 * usage:
 * author: kHRYSTAL
 * create time: 18/6/25
 * update time:
 * email: 723526676@qq.com
 */
class HouseStarkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_stark)

        setSupportActionBar(mToolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        mBubbleSeekBar.setProgress(mBounceScrollView.damping)
        mBubbleSeekBar.onProgressChangedListener = object : BubbleSeekBar.OnProgressChangedListenerAdapter() {
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
                if (progress > 0) {
                    mBounceScrollView.damping = progressFloat
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}