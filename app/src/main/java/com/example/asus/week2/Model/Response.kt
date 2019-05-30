package com.example.asus.week2.Model
import com.google.gson.annotations.SerializedName
data class Response (
	@SerializedName("docs") var docs : List<Docs>
)