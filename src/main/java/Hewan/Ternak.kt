package Hewan

import android.os.Parcel
import android.os.Parcelable

data class Ternak(
    var nama:String?,
    var jenis:String?,
    var usia:Int?,
    var deskripsi:String?,
) : Parcelable {
    constructor(membuat: Parcel) : this(
        membuat.readString(),
        membuat.readString(),
        membuat.readValue(Int::class.java.classLoader) as? Int,
        membuat.readString(),
    ) {
    }
    var addimage: String = ""

    override fun describeContents(): Int {
        return 0
    }
    override fun writeToParcel(membuat: Parcel, flags: Int) {
        membuat.writeString(nama)
        membuat.writeString(jenis)
        membuat.writeValue(usia)
        membuat.writeString(deskripsi)
    }

    companion object CREATOR : Parcelable.Creator<Ternak> {
        override fun createFromParcel(parcel: Parcel): Ternak {
            return Ternak(parcel)
        }

        override fun newArray(size: Int): Array<Ternak?> {
            return arrayOfNulls(size)
        }
    }
}