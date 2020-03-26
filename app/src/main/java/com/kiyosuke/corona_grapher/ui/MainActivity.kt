package com.kiyosuke.corona_grapher.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.kiyosuke.corona_grapher.R
import com.kiyosuke.corona_grapher.databinding.ActivityMainBinding
import com.kiyosuke.corona_grapher.databinding.LocationItemBinding
import com.kiyosuke.corona_grapher.model.Location
import com.kiyosuke.corona_grapher.util.ext.context
import com.kiyosuke.corona_grapher.util.ext.dataBinding
import com.kiyosuke.corona_grapher.util.ext.observeNonNull
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.databinding.BindableItem
import com.xwray.groupie.databinding.GroupieViewHolder
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModel()
    private val binding: ActivityMainBinding by dataBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = GroupAdapter<GroupieViewHolder<*>>()

        setupRecyclerView(adapter)

        viewModel.locations.observeNonNull(this) { locations ->
            val items = locations.map {
                LocationItem(
                    it,
                    ::requestLocationDetail
                )
            }
            adapter.update(items)
        }

        viewModel.locationLoadState.observeNonNull(this) { state ->
            Timber.d("state=$state")
        }

        Timber.d("onCreate")

        viewModel.refresh()
    }

    private fun setupRecyclerView(adapter: GroupAdapter<GroupieViewHolder<*>>) {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun requestLocationDetail(location: Location) {
        viewModel.requestDetail(location.id)
    }
}

// NOTE: API動作確認用アイテム
class LocationItem(
    private val location: Location,
    private val onClickListener: (Location) -> Unit
) : BindableItem<LocationItemBinding>(location.id.id) {

    override fun getLayout(): Int =
        R.layout.location_item

    override fun bind(viewBinding: LocationItemBinding, position: Int) {
        viewBinding.textCountry.text = location.country
        viewBinding.textPopulation.text = viewBinding.context.getString(
            R.string.population_count_format,
            location.countryPopulation ?: 0
        )
        viewBinding.textConfirmedCount.text = viewBinding.context.getString(
            R.string.confirmed_count_format,
            location.latest.confirmed
        )
        viewBinding.textDeathsCount.text = viewBinding.context.getString(R.string.deaths_count_format, location.latest.deaths)
        viewBinding.textRecoveredCount.text = viewBinding.context.getString(
            R.string.recovered_count_format,
            location.latest.recovered
        )

        viewBinding.root.setOnClickListener {
            onClickListener(location)
        }
    }

    override fun hasSameContentAs(other: Item<*>): Boolean {
        return (other as? LocationItem)?.location == location
    }
}