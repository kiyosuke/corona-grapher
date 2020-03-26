package com.kiyosuke.corona_grapher.ui.locations.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kiyosuke.corona_grapher.R

class LocationListFragment : Fragment() {

    companion object {
        fun newInstance() =
            LocationListFragment()
    }

    private lateinit var viewModel: LocationListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.location_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LocationListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
