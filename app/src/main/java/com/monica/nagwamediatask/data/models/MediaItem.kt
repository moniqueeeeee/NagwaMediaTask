package com.monica.nagwamediatask.data.models


import com.google.gson.annotations.SerializedName

data class MediaItem(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("url")
    var url: String? = null,
    var progress: Int = 0,
    var status: Status = Status.INITIAL
)

enum class Status(val value: Int) {
    ERROR(1),
    PENDING(2),
    DOWNLOADED(3),
    INITIAL(4)

}