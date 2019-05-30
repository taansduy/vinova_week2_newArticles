package com.example.asus.week2.Model
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Keywords (

	@SerializedName("name") val name : String,
	@SerializedName("value") val value : String,
	@SerializedName("rank") val rank : Int,
	@SerializedName("major") val major : String
):Serializable