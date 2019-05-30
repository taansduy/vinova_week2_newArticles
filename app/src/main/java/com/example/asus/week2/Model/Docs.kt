package com.example.asus.week2.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


public class Docs : Serializable{
	@SerializedName("web_url")
	@Expose 
	val web_url : String?=null

	@SerializedName("snippet") 
	@Expose val snippet : String?=null
	
	@SerializedName("lead_paragraph") 
	@Expose 
	val lead_paragraph : String?=null
	
	@SerializedName("abstract") 
	@Expose 
	val abstract : String?=null
			
	@SerializedName("source") 
	@Expose 
	val source : String?=null
			
	@SerializedName("multimedia") 
	@Expose 
	val multimedia : List<Multimedia>?=null
			
	@SerializedName("headline") 
	@Expose 
	val headline : Headline?=null
			
	@SerializedName("keywordswords") 
	@Expose 
	val keywordswords : List<Keywords>?=null
			
	@SerializedName("pub_date") 
	@Expose 
	val pub_date : String?=null
			
	@SerializedName("document_type")
	@Expose 
	val document_type : String?=null
			
	@SerializedName("news_desk") 
	@Expose 
	val news_desk : String?=null
			
	@SerializedName("section_name") 
	@Expose 
	val section_name : String?=null
			
	@SerializedName("type_of_material") 
	@Expose
	val type_of_material : String?=null
			
	@SerializedName("_id") 
	@Expose 
	val _id : String?=null
			
	@SerializedName("word_count") 
	@Expose 
	val word_count : Int?=null;
			
	@SerializedName("uri") 
	@Expose 
	val uri : String?=null
}

