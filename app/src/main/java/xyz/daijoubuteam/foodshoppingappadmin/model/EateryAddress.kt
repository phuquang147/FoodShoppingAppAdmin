package xyz.daijoubuteam.foodshoppingappadmin.model

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.GeoPoint
import java.io.Serializable
import java.util.*

data class EateryAddress(
    var address: String? = "",
    var geoPointLocation: GeoPoint? = null
) : Serializable {
    @get:Exclude
    @set:Exclude
    var location: LatLng?
        get() = geoPointLocation?.let {
            LatLng(it.latitude, it.longitude)
        }
        set(value) = setGeoPointLocation(value)

    @Exclude
    private fun setGeoPointLocation(location: LatLng?) {
        location?.let {
            geoPointLocation = GeoPoint(location.latitude, location.longitude)
        }
    }
}