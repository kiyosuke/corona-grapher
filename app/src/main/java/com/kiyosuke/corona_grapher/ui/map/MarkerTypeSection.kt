package com.kiyosuke.corona_grapher.ui.map

import com.kiyosuke.corona_grapher.R
import com.kiyosuke.corona_grapher.databinding.ItemMarkerTypeBinding
import com.xwray.groupie.Section
import com.xwray.groupie.databinding.BindableItem

class MarkerTypeSection(
    callback: (MarkerType) -> Unit
) : Section() {

    var currentSelection: MarkerType? = null
        set(value) {
            if (field == value) {
                return
            }
            val previous = field
            if (previous != null) {
                notifyItemChanged(MarkerType.values().indexOf(previous))
            }
            field = value
            if (value != null) {
                notifyItemChanged(MarkerType.values().indexOf(value))
            }
        }

    init {
        val items = MarkerType.values().map {
            MarkerTypeItem(it, callback)
        }
        update(items)
    }

    inner class MarkerTypeItem(
        private val markerType: MarkerType,
        private val callback: (MarkerType) -> Unit
    ) : BindableItem<ItemMarkerTypeBinding>() {

        override fun getLayout(): Int = R.layout.item_marker_type

        override fun bind(viewBinding: ItemMarkerTypeBinding, position: Int) {
            viewBinding.markerType = markerType
            viewBinding.isChecked = markerType == currentSelection
            viewBinding.root.setOnClickListener {
                callback(markerType)
            }
        }
    }
}