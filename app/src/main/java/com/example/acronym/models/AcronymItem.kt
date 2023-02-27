package com.example.acronym.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Data class to maintain list of acronyms.
 */

@Parcelize
data class AcronymItem(
    @SerializedName("sf") var sf: String?,
    @SerializedName("lfs") var lfs: ArrayList<Lfs> = arrayListOf()
) : Parcelable

@Parcelize
data class Vars(
    @SerializedName("lf") var lf: String?,
    @SerializedName("freq") var freq: Int?,
    @SerializedName("since") var since: Int?
) : Parcelable

@Parcelize
data class Lfs(
    @SerializedName("lf") var lf: String?,
    @SerializedName("freq") var freq: Int?,
    @SerializedName("since") var since: Int?,
    @SerializedName("vars") var vars: ArrayList<Vars> = arrayListOf()
) : Parcelable
