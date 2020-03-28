package com.kiyosuke.corona_grapher.ui.map

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import com.kiyosuke.corona_grapher.R
import com.kiyosuke.corona_grapher.databinding.FragmentMarkerTypeBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.databinding.GroupieViewHolder
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MarkerTypeDialogFragment : AppCompatDialogFragment() {

    private lateinit var section: MarkerTypeSection

    private val viewModel: MapViewModel by sharedViewModel(from = {
        ViewModelStoreOwner { requireParentFragment().viewModelStore }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_marker_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        section = MarkerTypeSection(::selectMarkerType)

        val binding = FragmentMarkerTypeBinding.bind(view)
        binding.markerTypeList.adapter = GroupAdapter<GroupieViewHolder<*>>().apply {
            add(section)
        }

        viewModel.markerType.observe(viewLifecycleOwner, Observer {
            section.currentSelection = it
        })
    }

    override fun onStart() {
        super.onStart()

        requireDialog().window?.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            attributes.gravity = Gravity.TOP or Gravity.END
            setBackgroundDrawable(null)
        }

        val margin = resources.getDimensionPixelSize(R.dimen.margin_normal)
        view?.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(margin, margin, margin, margin)
        }
    }

    private fun selectMarkerType(markerType: MarkerType) {
        viewModel.updateMarkerType(markerType)
        dismiss()
    }
}