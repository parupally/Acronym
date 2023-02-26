package com.example.acronym.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class to maintain list of acronyms.
 */

@Parcelize
data class AcronymItem(var sf: String?, var lfs: ArrayList<Lfs> = arrayListOf()) : Parcelable

@Parcelize
data class Vars(
    var lf: String?,
    var freq: Int?,
    var since: Int?
) : Parcelable

@Parcelize
data class Lfs(
    var lf: String,
    var freq: Int?,
    var since: Int?,
    var vars: ArrayList<Vars> = arrayListOf()
) : Parcelable
