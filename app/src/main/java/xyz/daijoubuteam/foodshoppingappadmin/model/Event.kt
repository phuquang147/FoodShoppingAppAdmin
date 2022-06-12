package xyz.daijoubuteam.foodshoppingappadmin.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Event (
    @DocumentId
    val id: String? = null,
    val name: String? = null,
    val content: String? = null,
    val image: String? = null,
    val idEateryList: @RawValue ArrayList<DocumentReference>? = null,
    @Exclude
    var subscribed: Boolean = false,
): Parcelable