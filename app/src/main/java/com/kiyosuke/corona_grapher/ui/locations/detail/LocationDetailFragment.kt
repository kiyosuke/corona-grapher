package com.kiyosuke.corona_grapher.ui.locations.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kiyosuke.corona_grapher.R
import com.kiyosuke.corona_grapher.databinding.LocationDetailFragmentBinding
import com.kiyosuke.corona_grapher.util.ext.dataBinding

class LocationDetailFragment : Fragment(R.layout.location_detail_fragment) {

    private val binding: LocationDetailFragmentBinding by dataBinding()

    companion object {
        private const val KEY_ARGS =
            "com.kiyosuke.corona_grapher.ui.locations.detail.LocationDetailFragment.args"

        fun newInstance(args: LocationDetailArgs) = LocationDetailFragment().apply {
            arguments = Bundle().apply {
                putBundle(KEY_ARGS, args.toBundle())
            }
        }
    }

    private fun requireArgs(): LocationDetailArgs {
        val args = requireArguments().getBundle(KEY_ARGS)?.let { args ->
            LocationDetailArgs.fromBundle(args)
        }
        return requireNotNull(args)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
