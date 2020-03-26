package com.kiyosuke.corona_grapher.di

import com.kiyosuke.corona_grapher.ui.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {

    viewModel { MainViewModel(get()) }
}