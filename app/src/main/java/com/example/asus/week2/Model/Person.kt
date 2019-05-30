package com.example.asus.week2.Model
import com.google.gson.annotations.SerializedName

data class Person (

	@SerializedName("firstname") var firstname : String,
	@SerializedName("middlename") var middlename : String,
	@SerializedName("lastname") var lastname : String,
	@SerializedName("qualifier") var qualifier : String,
	@SerializedName("title") var title : String,
	@SerializedName("role") var role : String,
	@SerializedName("organization") var organization : String,
	@SerializedName("rank") var rank : Int
)