package com.example.asus.week2.Adapter

import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.asus.week2.Model.Docs
import com.example.asus.week2.R
import com.example.asus.week2.Utils.onItemClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_article_no_image.*
import kotlinx.android.synthetic.main.item_article_search_layout.*
import kotlinx.android.synthetic.main.loading_item_layout.*
import java.util.*


class Article_Adapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_ITEM_IMG = 1
    private val VIEW_ITEM_NO_IMG = 2
    private val VIEW_LOADING = 0
    private var Articles: MutableCollection<Docs?> = ArrayList()
    var itemClick: onItemClickListener? = null

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            is ViewHolderImg -> {
                (viewHolder as ViewHolderImg).bind(Articles[position])
            }
            is ViewHolderNoImg->{
                (viewHolder as ViewHolderNoImg).bind(Articles[position])
            }
            is Loading -> {
                (viewHolder as Loading).bind()
            }
        }
    }


    inner class ViewHolderImg(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(article: Docs?) {
            containerView.setOnClickListener {
                itemClick!!.onItemClick(article!!)
            }
            var linkImage = "https://www.nytimes.com/"
            if (article!!.multimedia?.size!! > 0) {
                linkImage += article.multimedia!![0].url
            }
            val options = RequestOptions()
                .centerInside()
                .error(R.drawable.no_image).fitCenter()
            Glide.with(this@Article_Adapter.context.applicationContext ?: return)
                .load(linkImage)
                .fitCenter()
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
            tv_headline.text = article.headline?.main
            tv_date_public.text = article.pub_date?.substring(0, 10)
            tv_category.text =  article.document_type!!.substring(0,1).toUpperCase() + article.document_type!!.substring(1);
        }
    }

    inner class ViewHolderNoImg(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(article: Docs?) {
            containerView.setOnClickListener {
                itemClick!!.onItemClick(article!!)
            }
            tv_title.text = article?.headline?.main
            tv_brieft_description.text = article?.lead_paragraph
            tv_date_public1.text = article?.pub_date?.substring(0, 10)
            tv_category1.text = article?.document_type!!.substring(0,1).toUpperCase() + article?.document_type!!.substring(1)
        }
    }

    inner class Loading(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind() {
            container.startShimmer()
        }
    }

    fun setItemClickListener(clickListener: onItemClickListener) {
        itemClick = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_ITEM_IMG) {
            return ViewHolderImg(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_article_search_layout,
                    parent,
                    false
                )
            )
        } else if (viewType == VIEW_ITEM_NO_IMG) {
            return ViewHolderNoImg(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_article_no_image,
                    parent,
                    false
                )
            )
        } else {
            return Loading(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.loading_item_layout,
                    parent,
                    false
                )
            )
        }

    }

    override fun getItemCount(): Int {
        return Articles.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (Articles[position] == null) VIEW_LOADING
        else if (Articles[position]!!.multimedia!!.size == 0) VIEW_ITEM_NO_IMG
        else VIEW_ITEM_IMG
    }

    fun addAll(mArticles: List<Docs>) {
        val lastSize = Articles.size
        Articles.addAll(mArticles)
        notifyItemRangeInserted(lastSize, mArticles.size)
        //notifyDataSetChanged()
    }

    fun add(mArticle: Docs?) {
        val lastSize = Articles.size
        Articles.add(mArticle)
        notifyItemRangeInserted(lastSize, 1)
    }

    fun removeLastItem() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Articles.removeIf({ it == null })
        }
        notifyDataSetChanged()
    }

    fun updateSource(mArticles: List<Docs>) {
        Articles.clear()
        Articles.addAll(mArticles)
        notifyDataSetChanged()
    }


}

private operator fun <E> MutableCollection<E>.get(position: Int): E? {
    return this.elementAt(position)

}
