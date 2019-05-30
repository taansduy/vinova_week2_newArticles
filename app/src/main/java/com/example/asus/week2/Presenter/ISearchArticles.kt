package com.example.asus.week2.Presenter

import android.content.Context
import com.example.asus.week2.Model.Docs

interface ISearchArticles {
    interface View {
        fun setPresenter(presenter: Presenter)

        fun onResponse(articles: List<Docs>?,type:Int)

        fun onFailure(error:String)
    }

    interface Presenter {
        fun getArticles(query:String,date:String?,sort:String?,newDesk:String?,page:Int)
        fun getArticles(sort:String,page:Int)

    }
}