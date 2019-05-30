package com.example.asus.week2.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Multimedia (

	@SerializedName("rank") @Expose val rank : Int,
	@SerializedName("subtype") @Expose val subtype : String,
	@SerializedName("caption") @Expose val caption : String,
	@SerializedName("credit") @Expose val credit : String,
	@SerializedName("type") @Expose val type : String,
	@SerializedName("url") @Expose val url : String,
	@SerializedName("height") @Expose val height : Int,
	@SerializedName("width") @Expose val width : Int,
	@SerializedName("subType") @Expose val subType : String,
	@SerializedName("crop_name") @Expose val crop_name : String
):Serializable