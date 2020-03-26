package com.kiyosuke.corona_grapher.di

import com.kiyosuke.corona_grapher.ui.MainViewModel
import com.kiyosuke.corona_grapher.ui.locations.detail.LocationDetailViewModel
import com.kiyosuke.corona_grapher.ui.locations.list.LocationListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {

    viewModel { MainViewModel(get()) }
    viewModel { LocationDetailViewModel(get()) }
    viewModel { LocationListViewModel(get()) }
}