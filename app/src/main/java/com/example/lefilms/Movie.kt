package com.example.lefilms

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id")var id: Int,
    @SerializedName("title")var title: String,
    @SerializedName("release_date")var releaseDate: String,
    @SerializedName("overview")var description: String,
    @SerializedName("poster_path")var poster: String
) : Parcelable