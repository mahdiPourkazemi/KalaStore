package com.pourkazemi.mahdi.kalastore.data.model

import android.os.Parcel
import android.os.Parcelable

data class Kala(
    val id: Int,
    val name:String,
    val price:String,
    val description:String,
    val image:List<String>
    ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "null",
        parcel.readString() ?: "null",
        parcel.readString() ?:"null",
        parcel.createStringArrayList() ?: listOf("null")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeString(description)
        parcel.writeStringList(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Kala> {
        override fun createFromParcel(parcel: Parcel): Kala {
            return Kala(parcel)
        }

        override fun newArray(size: Int): Array<Kala?> {
            return arrayOfNulls(size)
        }
    }
}
