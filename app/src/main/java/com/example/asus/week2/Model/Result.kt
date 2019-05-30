package com.example.asus.week2.Model

import com.google.gson.annotations.SerializedName


data class Result (

	@SerializedName("status") val status : String,
	@SerializedName("copyright") val copyright : String,
	@SerializedName("response") val response : Response
)