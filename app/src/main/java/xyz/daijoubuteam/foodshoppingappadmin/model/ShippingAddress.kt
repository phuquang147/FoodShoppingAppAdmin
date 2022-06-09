package xyz.daijoubuteam.foodshoppingappadmin.model

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.GeoPoint
import java.io.Serializable
import java.util.*

data class ShippingAddress(
    var id: String? = UUID.randomUUID().toString().replace("-", "").uppercase(),
    var name: String? = "",
    var gender: Gender? = Gender.MALE,
    var phoneNumber: String? = "",
    var address: String? = "",
    var geoPointLocation: GeoPoint? = null,
    val defaultAddress: Boolean = false
): Serializable