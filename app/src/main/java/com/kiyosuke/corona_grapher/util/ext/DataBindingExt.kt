package com.kiyosuke.corona_grapher.util.ext

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified V : ViewDataBinding> AppCompatActivity.dataBinding(): Lazy<V> {
    return object : Lazy<V> {
        private var holder: V? = null

        override val value: V
            get() = holder ?: run {
                val binding =
                    DataBindingUtil.bind<V>(findViewById<ViewGroup>(android.R.id.content)[0])!!
                holder = binding
                binding
            }

        override fun isInitialized(): Boolean = holder != null
    }
}

inline fun <reified V : ViewDataBinding> Fragment.dataBinding(): ReadOnlyProperty<Fragment, V> {
    return object : ReadOnlyProperty<Fragment, V> {
        private var holder: V? = null

        override fun getValue(thisRef: Fragment, property: KProperty<*>): V {
            return holder ?: DataBindingUtil.bind<V>(view!!)!!.also {
                holder = it
            }
        }
    }
}

val ViewDataBinding.context: Context get() = this.root.context