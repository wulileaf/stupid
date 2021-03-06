package me.khrystal.bouncescrollview

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.daasuu.ei.Ease

/**
 *
 * usage:
 * author: kHRYSTAL
 * create time: 18/6/25
 * update time:
 * email: 723526676@qq.com
 */
class PickerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_picker, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.picker_rv)
        rv.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val tv = holder.itemView.findViewById<TextView>(R.id.picker_tv)
                tv.text = Ease.values()[position].name
                holder.itemView.setOnClickListener {
                    (context as HouseBaratheonActivity).onInterpolatorSelected(Ease.values()[holder.adapterPosition])
                    exitFragment()
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.item_picker, parent, false);
                return object : RecyclerView.ViewHolder(v) {}
            }

            override fun getItemCount(): Int {
                return Ease.values().size
            }

        }
        return view
    }

    private fun exitFragment() {
        val ft = (context as HouseBaratheonActivity).supportFragmentManager.beginTransaction()
        ft.remove(this)
        ft.commitAllowingStateLoss()
    }
}