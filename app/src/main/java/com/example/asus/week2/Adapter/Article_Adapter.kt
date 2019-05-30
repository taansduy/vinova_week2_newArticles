package com.example.asus.week2.Adapter
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.asus.week2.Model.Docs
import com.example.asus.week2.R
import com.example.asus.week2.Utils.onItemClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_article_search_layout.*
import kotlinx.android.synthetic.main.loading_item_layout.*
import java.util.*


class Article_Adapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_ITEM = 1
    private val VIEW_LOADING = 0

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when(viewHolder)
        {
            is ViewHolder ->{
                (viewHolder as ViewHolder).bind(Articles[position])
            }
            is Loading ->{
                (viewHolder as Loading).bind()
            }
        }
    }

    private var Articles :MutableCollection<Docs?> =ArrayList()
    var itemClick: onItemClickListener?= null
    inner class ViewHolder( override val containerView: View): RecyclerView.ViewHolder(containerView),LayoutContainer{
        fun bind(article: Docs?) {
            containerView.setOnClickListener{
                itemClick!!.onItemClick(article!!)
            }
            var linkImage = "https://www.nytimes.com/"
            if(article!!.multimedia?.size!!>0)
            {
                linkImage += article.multimedia!![0].url
            }
            val options = RequestOptions()
                .centerInside()
                .error(R.drawable.no_image)
                .placeholder(R.drawable.loading).fitCenter()
            Glide.with(this@Article_Adapter.context.applicationContext ?: return)
                .load(linkImage)
                .fitCenter()
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(containerView.findViewById(R.id.imageView))
            tv_headline.text= article.headline?.main
        }
    }
    inner class Loading( override val containerView: View): RecyclerView.ViewHolder(containerView),LayoutContainer{

        fun bind() {
            Glide.with(context?.applicationContext ?: return).load("https://cdn-images-1.medium.com/max/1600/1*9EBHIOzhE1XfMYoKz1JcsQ.gif")
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(loading_img)
        }
    }
    fun setItemClickListener(clickListener: onItemClickListener ) {
        itemClick = clickListener
    }
    /* override fun onBindViewHolder(viewHolder: FilmAdapter.ViewHolder, position : Int) {
         viewHolder.bind(films[position])
     }*/

    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==VIEW_ITEM) {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_article_search_layout,
                    parent,
                    false
                )
            )
        }
        else{
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
        return if (Articles[position]!=null) VIEW_ITEM else VIEW_LOADING
    }

    fun addAll(mArticles:List<Docs>)
    {
        val lastSize=Articles.size
        Articles.addAll(mArticles)
        notifyItemRangeInserted(lastSize,mArticles.size)
        //notifyDataSetChanged()
    }
    fun add(mArticle:Docs?)
    {
        val lastSize=Articles.size
        Articles.add(mArticle)
        notifyItemRangeInserted(lastSize,1)
    }

    fun removeLastItem()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Articles.removeIf({it==null})
        }
        notifyDataSetChanged()
    }
    fun updateSource(mArticles:List<Docs>)
    {
        Articles.clear()
        Articles.addAll(mArticles)
        notifyDataSetChanged()
    }


}

private operator fun <E> MutableCollection<E>.get(position: Int): E? {
    return this.elementAt(position)

}
