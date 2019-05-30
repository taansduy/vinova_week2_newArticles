package com.example.asus.week2.Presenter

import android.content.Context
import com.example.asus.week2.Api.Api
import com.example.asus.week2.Model.Docs
import com.example.asus.week2.Model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchArticlePresenter(val view: ISearchArticles.View) : ISearchArticles.Presenter {
    override fun getArticles(sort: String, page: Int) {
        Api.createService().doQueryFilterSort(sort!!,page).enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>?, t: Throwable?) {
                view.onFailure(t.toString())

            }

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                if(response.body()?.toString()!!.contains("429") && response.body()?.toString()!!.contains("fault") )
                {
                    view.onFailure("429")
                }
                if (response.body() != null) {
                    if(page==0) view.onResponse(response.body()?.response!!.docs,1)
                    else view.onResponse(response.body()?.response!!.docs,2)

                }
                else{
                    view.onResponse(null,2)
                }
            }

        })
    }

    override fun getArticles(query: String, date: String?, sort: String?, newDesk: String?, page: Int) {
        if( !date.equals("")  && !sort.equals("") && !newDesk.equals(""))
        {
            Api.createService().doQueryFilterAll(query,date!!,sort!!,"news_desk:("+newDesk!!+")",page).enqueue(object : Callback<Result> {
                override fun onFailure(call: Call<Result>?, t: Throwable?) {
                    println(t.toString())

                }

                override fun onResponse(call: Call<Result>, response: Response<Result>) {
                    if (response.body() != null) {
                        if(page==0) view.onResponse(response.body()?.response!!.docs,1)
                        else view.onResponse(response.body()?.response!!.docs,2)

                    }
                    else{
                        view.onResponse(null,2)
                    }
                }

            })
        }
        else
        {
            if(!date.equals("") )
            {
                if(!sort.equals(""))
                {
                    Api.createService().doQueryFilterDateandSort(query,date!!,sort!!,page).enqueue(object : Callback<Result> {
                        override fun onFailure(call: Call<Result>?, t: Throwable?) {
                            println(t.toString())

                        }

                        override fun onResponse(call: Call<Result>, response: Response<Result>) {
                            if (response.body() != null) {
                                if(page==0) view.onResponse(response.body()?.response!!.docs,1)
                                else view.onResponse(response.body()?.response!!.docs,2)

                            }
                            else{
                                view.onResponse(null,2)
                            }
                        }

                    })
                }
                else if(!newDesk.equals("")) {
                    Api.createService().doQueryFilterDateandNDesk(query,date!!,"news_desk:("+newDesk!!+")",page).enqueue(object : Callback<Result> {
                        override fun onFailure(call: Call<Result>?, t: Throwable?) {
                            println(t.toString())

                        }

                        override fun onResponse(call: Call<Result>, response: Response<Result>) {
                            if (response.body() != null) {
                                if(page==0) view.onResponse(response.body()?.response!!.docs,1)
                                else view.onResponse(response.body()?.response!!.docs,2)

                            }
                            else{
                                view.onResponse(null,2)
                            }
                        }

                    })
                }
                else
                {
                    Api.createService().doQueryFilterDate(query,date!!,page).enqueue(object : Callback<Result> {
                        override fun onFailure(call: Call<Result>?, t: Throwable?) {
                            println(t.toString())

                        }

                        override fun onResponse(call: Call<Result>, response: Response<Result>) {
                            if (response.body() != null) {
                                if(page==0) view.onResponse(response.body()?.response!!.docs,1)
                                else view.onResponse(response.body()?.response!!.docs,2)

                            }
                            else{
                                view.onResponse(null,2)
                            }
                        }

                    })
                }
            }
            else if(!sort.equals(""))
            {
                if(!newDesk.equals("")) {
                    Api.createService().doQueryFilterSortandNDesk(query,sort!!,"news_desk:("+newDesk!!+")",page).enqueue(object : Callback<Result> {
                        override fun onFailure(call: Call<Result>?, t: Throwable?) {
                            println(t.toString())

                        }

                        override fun onResponse(call: Call<Result>, response: Response<Result>) {
                            if (response.body() != null) {
                                if(page==0) view.onResponse(response.body()?.response!!.docs,1)
                                else view.onResponse(response.body()?.response!!.docs,2)

                            }
                            else{
                                view.onResponse(null,2)
                            }
                        }

                    })
                }
                else
                {
                    Api.createService().doQueryFilterSort(query,sort!!,page).enqueue(object : Callback<Result> {
                        override fun onFailure(call: Call<Result>?, t: Throwable?) {
                            println(t.toString())

                        }

                        override fun onResponse(call: Call<Result>, response: Response<Result>) {
                            if (response.body() != null) {
                                if(page==0) view.onResponse(response.body()?.response!!.docs,1)
                                else view.onResponse(response.body()?.response!!.docs,2)

                            }
                            else{
                                view.onResponse(null,2)
                            }
                        }

                    })
                }
            }
            else if(!newDesk.equals(""))
            {
                Api.createService().doQueryFilterNDesk(query,"news_desk:("+newDesk!!+")",page).enqueue(object : Callback<Result> {
                    override fun onFailure(call: Call<Result>?, t: Throwable?) {
                        println(t.toString())

                    }

                    override fun onResponse(call: Call<Result>, response: Response<Result>) {
                        if (response.body() != null) {
                            if(page==0) view.onResponse(response.body()?.response!!.docs,1)
                            else view.onResponse(response.body()?.response!!.docs,2)

                        }
                        else{
                            view.onResponse(null,2)
                        }
                    }

                })
            }
            else{
                Api.createService().doQuery(query,page).enqueue(object : Callback<Result> {
                    override fun onFailure(call: Call<Result>?, t: Throwable?) {
                        println(t.toString())

                    }

                    override fun onResponse(call: Call<Result>, response: Response<Result>) {
                        if (response.body() != null) {
                            if(page==0) view.onResponse(response.body()?.response!!.docs,1)
                            else view.onResponse(response.body()?.response!!.docs,2)

                        }
                        else{
                            view.onResponse(null,2)
                        }
                    }

                })
            }
        }
    }

}