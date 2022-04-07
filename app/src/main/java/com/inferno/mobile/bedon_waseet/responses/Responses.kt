package com.inferno.mobile.bedon_waseet.responses

import com.google.gson.annotations.SerializedName
import com.inferno.mobile.bedon_waseet.utils.UserType
import java.io.Serializable

data class SignResponse(
    @SerializedName("data")
    val data: DataResponse
) : Serializable

data class DataResponse(
    @SerializedName("response")
    val register: Response
) : Serializable

data class Response(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val message: String,
    @SerializedName("data")
    val data: String?,
    @SerializedName("type")
    val type: UserType,

    ) : Serializable

data class RealEstateResponse(
    @SerializedName("data")
    val data: RealEstateDataResponse
) : Serializable

data class RealEstateDetailsResponse(
    @SerializedName("data")
    val data: RealEstateDetailsDataResponse
) : Serializable

data class RealEstateDetailsDataResponse(
    @SerializedName("real_estate")
    val state: RealEstate
) : Serializable

data class RealEstateDataResponse(
    @SerializedName("real_estate")
    val states: ArrayList<RealEstate>
) : Serializable

data class RealEstate(
    @SerializedName("id")
    val id: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("img_url")
    val img_url: String?,
    @SerializedName("buy_type")
    val buy_type: String?,
    @SerializedName("log")
    val log: Float,
    @SerializedName("lat")
    val lat: Float,
    @SerializedName("rate")
    val rate: Float,
    @SerializedName("budget")
    val budget: Float,
    @SerializedName("rooms")
    val rooms: List<Room>?,
    @SerializedName("video")
    val video: String,
    @SerializedName("user")
    val user: User?
) : Serializable

data class Room(
    @SerializedName("name")
    val name: String,
    @SerializedName("images")
    val images: List<RoomImage>
) : Serializable

data class RoomImage(@SerializedName("path") val path: String) : Serializable

data class RoomVideo(@SerializedName("name") val name: String) : Serializable

data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("states")
    val states: List<RealEstate>
) : Serializable

data class AvatarResult(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: AvatarResultData,
) : Serializable

data class AvatarResultData(
    @SerializedName("name")
    val name: String,
    @SerializedName("mimetype")
    val mimetype: String,
    @SerializedName("size")
    val size: String,
) : Serializable

