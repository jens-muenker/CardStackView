package com.yuyakaido.android.cardstackview.sample

import androidx.recyclerview.widget.DiffUtil

class SpotDiffCallback(
    private val old: List<Spot>,
    private val new: List<Spot>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean =
        old[oldPosition].id == new[newPosition].id

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean =
        old[oldPosition] == new[newPosition]

}
