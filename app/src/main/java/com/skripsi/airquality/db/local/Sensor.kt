package com.skripsi.airquality.db.local

import android.os.Parcel
import android.os.Parcelable

data class Sensor(
    val name: String,
    val description: String,
    val iconResId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(

        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(iconResId)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Sensor> {
        override fun createFromParcel(parcel: Parcel): Sensor {
            return Sensor(parcel)
        }

        override fun newArray(size: Int): Array<Sensor?> {
            return arrayOfNulls(size)
        }
    }
}
