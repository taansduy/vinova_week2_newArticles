package com.example.asus.week2.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Headline (

	@SerializedName("main") @Expose val main : String,
	@SerializedName("kicker") @Expose val kicker : String,
	@SerializedName("content_kicker") @Expose val content_kicker : String,
	@SerializedName("print_headline") @Expose val print_headline : String,
	@SerializedName("name") @Expose val name : String,
	@SerializedName("seo") @Expose val seo : String,
	@SerializedName("sub") @Expose val sub : String
): Serializable