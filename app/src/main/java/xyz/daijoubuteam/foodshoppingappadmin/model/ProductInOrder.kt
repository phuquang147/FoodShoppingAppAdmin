package xyz.daijoubuteam.foodshoppingappadmin.model

import android.os.Parcelable
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ProductInOrder(
    @DocumentId
    val id: String? = null,
    val price: Double? = null,
    val productId: @RawValue DocumentReference? = null,
    val quantity: Int? = null,
    val productImg: String? = null,
    val productName: String? = null,
    val productPrice: Double? = null,
) : Parcelable