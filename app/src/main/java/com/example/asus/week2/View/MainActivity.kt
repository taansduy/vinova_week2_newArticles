package com.example.asus.week2.View

import android.app.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.Context
import com.example.asus.week2.Adapter.Article_Adapter
import com.example.asus.week2.Model.Docs
import com.example.asus.week2.Presenter.ISearchArticles
import com.example.asus.week2.Presenter.SearchArticlePresenter
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.content.Intent
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.*
import com.example.asus.week2.Utils.GridItemDecoration
import com.example.asus.week2.Utils.onItemClickListener
import android.graphics.BitmapFactory
import com.example.asus.week2.R
import com.example.asus.week2.Utils.EndlessScrollListener
import com.example.asus.week2.Utils.onSaveFilterListener
import kotlinx.android.synthetic.main.abc_tooltip.*
import java.util.*
import android.net.NetworkInfo
import android.net.ConnectivityManager
import com.example.asus.week2.Utils.NetworkChangeReceiver
import android.content.IntentFilter
import android.view.WindowManager
import android.graphics.Bitmap
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.app.Activity
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable


class MainActivity : AppCompatActivity(), ISearchArticles.View {
    private var presenter: ISearchArticles.Presenter
    private var  myadapter: Article_Adapter?=null
    private var mLayoutManager: LinearLayoutManager? = null
    private var dialog :ProgressDialog?=null
    private var mquery:String?=""
    private var mDate:String?=""
    private var mSort:String?=""
    private var mNewDesk:String?=""
    private var networkChangeReceiver: NetworkChangeReceiver? = null
    init{
        presenter=SearchArticlePresenter(this);
    }

    override fun setPresenter(presenter: ISearchArticles.Presenter) {
        this.presenter=presenter
    }

    override fun onResponse(articles: List<Docs>?,type:Int) {
        if (articles == null || articles.size==0)
        {
           if(myadapter?.itemCount==0)
           {
               var d= AlertDialog.Builder(this@MainActivity)
               d.setMessage("Không có bài báo nào phù hợp với tìm kiếm của bạn")
               d.show()
           }
            else
           {
               myadapter?.removeLastItem()
           }
            pullRefresh.isRefreshing=false
            return
        }

        if(type==1)
        {
            myadapter?.updateSource(articles)
            rcv.scrollToPosition(0)
        }
        else
        {
            myadapter?.addAll(articles)
            myadapter?.removeLastItem()
        }
        dialog?.dismiss()

        pullRefresh.isRefreshing=false
    }

    override fun onFailure(error:String) {
        dialog?.dismiss()
        pullRefresh.isRefreshing=false
        if(error.equals("429"))
        {
            var d= AlertDialog.Builder(this@MainActivity)
            d.setTitle("Thông báo")
            d.setMessage("Bạn đang thao tác quá nhanh. Vui lòng sống chậm lại để cảm nhận các bài báo.")
            d.show()
        }
        else
        {
            var d= AlertDialog.Builder(this@MainActivity)
            d.setTitle("Thông báo")
            d.setMessage("Vui lòng kết nối Internet để tiếp tục trải nghiệm.")
            d.show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        intentFilter.priority = 100
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, intentFilter)

        myadapter = Article_Adapter(this)
        mLayoutManager = LinearLayoutManager(this)
        presenter.getArticles("newest",0)
        for(i in 1..10)
        {
            myadapter?.add(null)
        };
        rcv.adapter=this.myadapter
        rcv.layoutManager = mLayoutManager
        //This will for default android divider
        rcv.addItemDecoration(GridItemDecoration(5, 1))
        var itemClickListener=object: onItemClickListener {
            override fun onItemClick(item: Docs) {
                val url = item.web_url
                val builder = CustomTabsIntent.Builder()
                builder.setToolbarColor(ContextCompat.getColor(this@MainActivity,
                    R.color.colorPrimary
                ));
                builder.addDefaultShareMenuItem();
                var bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_action_share)
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, item.web_url)
                val requestCode = 100
                val pendingIntent = PendingIntent.getActivity(
                    this@MainActivity,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                builder.setActionButton(bitmap, "Share Link", pendingIntent, true);
                val customTabsIntent = builder.build()

                customTabsIntent.launchUrl(this@MainActivity, Uri.parse(url))
            }
        }
        myadapter?.setItemClickListener(itemClickListener)
        var scrollListener = object : EndlessScrollListener(mLayoutManager!!,0) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                for(i in 1..10)
                {
                    myadapter?.add(null)
                };
                if(page<=100) presenter.getArticles(mquery!!,mDate,mSort,mNewDesk,page)

            }
        }
        rcv.addOnScrollListener(scrollListener)
        pullRefresh.setOnRefreshListener {
            if(!mquery.equals("") && mquery!=null)
            {
                presenter.getArticles(mquery!!,mDate,mSort,mNewDesk,0)
            }
            else
            {
                presenter.getArticles("newest",0)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.my_menu, menu)
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val search = menu.findItem(R.id.miSearch).actionView as SearchView
        search.setSearchableInfo(manager.getSearchableInfo(componentName))
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mquery=query
                myadapter?.updateSource(ArrayList<Docs>())
                presenter.getArticles(query!!,mDate,mSort,mNewDesk,0)
                hideKeyboard(this@MainActivity)
                for(i in 1..10)
                {
                    myadapter?.add(null)
                };
                return true
            }
            override fun onQueryTextChange(query: String): Boolean {
                return true

            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when (item.getItemId()) {
            R.id.miFilter -> {
                val fm = supportFragmentManager
                var dialog=Filter_Options(this@MainActivity,mDate,mSort,mNewDesk)
                dialog.setSaveListener(object: onSaveFilterListener{
                    override fun onItemClick(date: String, sort: String, newdesk: String) {
                        mDate=date
                        mSort=sort
                        mNewDesk=newdesk
                        dialog.dismiss()
                    }
                })
                dialog.show(fm,"Filter Option")
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(this.networkChangeReceiver!=null) {
            unregisterReceiver(this.networkChangeReceiver);
        }
    }
    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }


}
