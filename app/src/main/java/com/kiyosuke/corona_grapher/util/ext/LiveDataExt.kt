package com.kiyosuke.corona_grapher.util.ext

import androidx.lifecycle.*

inline fun <T> LiveData<T>.observeNonNull(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    this.observe(owner, Observer { v ->
        if (v != null) {
            observer(v)
        }
    })
}

fun <T : Any> LiveData<T>.requireValue(): T = requireNotNull(this.value)

fun <T : Any> LiveData<T>.distinctUntilChanged(): LiveData<T> =
    Transformations.distinctUntilChanged(this)

inline fun <T : Any, LIVE1 : Any> combine(
    initialValue: T,
    liveData1: LiveData<LIVE1>,
    crossinline block: (T, LIVE1) -> T
): LiveData<T> {
    return MediatorLiveData<T>().apply {
        value = initialValue
        listOf(liveData1).forEach { liveData ->
            addSource(liveData) {
                val currentValue = value
                val liveData1Value = liveData1.value
                if (currentValue != null && liveData1Value != null) {
                    value = block(currentValue, liveData1Value)
                }
            }
        }
    }.distinctUntilChanged()
}

inline fun <T : Any, LIVE1 : Any, LIVE2 : Any> combine(
    initialValue: T,
    liveData1: LiveData<LIVE1>,
    liveData2: LiveData<LIVE2>,
    crossinline block: (T, LIVE1, LIVE2) -> T
): LiveData<T> {
    return MediatorLiveData<T>().apply {
        value = initialValue
        listOf(liveData1, liveData2).forEach { liveData ->
            addSource(liveData) {
                val currentValue = value
                val liveData1Value = liveData1.value
                val liveData2Value = liveData2.value
                if (currentValue != null && liveData1Value != null && liveData2Value != null) {
                    value = block(currentValue, liveData1Value, liveData2Value)
                }
            }
        }
    }.distinctUntilChanged()
}

inline fun <T : Any, LIVE1 : Any, LIVE2 : Any, LIVE3 : Any> combine(
    initialValue: T,
    liveData1: LiveData<LIVE1>,
    liveData2: LiveData<LIVE2>,
    liveData3: LiveData<LIVE3>,
    crossinline block: (T, LIVE1, LIVE2, LIVE3) -> T
): LiveData<T> {
    return MediatorLiveData<T>().apply {
        value = initialValue
        listOf(liveData1, liveData2, liveData3).forEach { liveData ->
            addSource(liveData) {
                val currentValue = value
                val liveData1Value = liveData1.value
                val liveData2Value = liveData2.value
                val liveData3Value = liveData3.value
                if (currentValue != null && liveData1Value != null && liveData2Value != null && liveData3Value != null) {
                    value = block(currentValue, liveData1Value, liveData2Value, liveData3Value)
                }
            }
        }
    }.distinctUntilChanged()
}

inline fun <T : Any, LIVE1 : Any, LIVE2 : Any, LIVE3 : Any, LIVE4 : Any> combine(
    initialValue: T,
    liveData1: LiveData<LIVE1>,
    liveData2: LiveData<LIVE2>,
    liveData3: LiveData<LIVE3>,
    liveData4: LiveData<LIVE4>,
    crossinline block: (T, LIVE1, LIVE2, LIVE3, LIVE4) -> T
): LiveData<T> {
    return MediatorLiveData<T>().apply {
        value = initialValue
        listOf(liveData1, liveData2, liveData3, liveData4).forEach { liveData ->
            addSource(liveData) {
                val currentValue = value
                val liveData1Value = liveData1.value
                val liveData2Value = liveData2.value
                val liveData3Value = liveData3.value
                val liveData4Value = liveData4.value
                if (currentValue != null && liveData1Value != null && liveData2Value != null && liveData3Value != null && liveData4Value != null) {
                    value = block(
                        currentValue,
                        liveData1Value,
                        liveData2Value,
                        liveData3Value,
                        liveData4Value
                    )
                }
            }
        }
    }.distinctUntilChanged()
}

inline fun <T : Any, LIVE1 : Any, LIVE2 : Any, LIVE3 : Any, LIVE4 : Any, LIVE5 : Any> combine(
    initialValue: T,
    liveData1: LiveData<LIVE1>,
    liveData2: LiveData<LIVE2>,
    liveData3: LiveData<LIVE3>,
    liveData4: LiveData<LIVE4>,
    liveData5: LiveData<LIVE5>,
    crossinline block: (T, LIVE1, LIVE2, LIVE3, LIVE4, LIVE5) -> T
): LiveData<T> {
    return MediatorLiveData<T>().apply {
        value = initialValue
        listOf(liveData1, liveData2, liveData3, liveData4, liveData5).forEach { liveData ->
            addSource(liveData) {
                val currentValue = value
                val liveData1Value = liveData1.value
                val liveData2Value = liveData2.value
                val liveData3Value = liveData3.value
                val liveData4Value = liveData4.value
                val liveData5Value = liveData5.value
                if (currentValue != null && liveData1Value != null && liveData2Value != null && liveData3Value != null && liveData4Value != null && liveData5Value != null) {
                    value = block(
                        currentValue,
                        liveData1Value,
                        liveData2Value,
                        liveData3Value,
                        liveData4Value,
                        liveData5Value
                    )
                }
            }
        }
    }.distinctUntilChanged()
}

inline fun <T : Any, LIVE1 : Any, LIVE2 : Any, LIVE3 : Any, LIVE4 : Any, LIVE5 : Any, LIVE6 : Any> combine(
    initialValue: T,
    liveData1: LiveData<LIVE1>,
    liveData2: LiveData<LIVE2>,
    liveData3: LiveData<LIVE3>,
    liveData4: LiveData<LIVE4>,
    liveData5: LiveData<LIVE5>,
    liveData6: LiveData<LIVE6>,
    crossinline block: (T, LIVE1, LIVE2, LIVE3, LIVE4, LIVE5, LIVE6) -> T
): LiveData<T> {
    return MediatorLiveData<T>().apply {
        value = initialValue
        listOf(
            liveData1,
            liveData2,
            liveData3,
            liveData4,
            liveData5,
            liveData6
        ).forEach { liveData ->
            addSource(liveData) {
                val currentValue = value
                val liveData1Value = liveData1.value
                val liveData2Value = liveData2.value
                val liveData3Value = liveData3.value
                val liveData4Value = liveData4.value
                val liveData5Value = liveData5.value
                val liveData6Value = liveData6.value
                if (currentValue != null && liveData1Value != null && liveData2Value != null && liveData3Value != null && liveData4Value != null && liveData5Value != null && liveData6Value != null) {
                    value = block(
                        currentValue,
                        liveData1Value,
                        liveData2Value,
                        liveData3Value,
                        liveData4Value,
                        liveData5Value,
                        liveData6Value
                    )
                }
            }
        }
    }.distinctUntilChanged()
}