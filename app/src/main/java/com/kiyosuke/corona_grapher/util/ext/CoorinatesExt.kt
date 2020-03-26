package com.kiyosuke.corona_grapher.util.ext

import com.google.android.gms.maps.model.LatLng
import com.kiyosuke.corona_grapher.model.Coordinates

fun Coordinates.toLatLng(): LatLng = LatLng(latitude, longitude)