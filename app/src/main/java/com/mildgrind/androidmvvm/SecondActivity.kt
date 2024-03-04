package com.mildgrind.androidmvvm
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class SecondActivity : AppCompatActivity() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        viewModel.setText("Hello Money")

        // Perform any initialization or setup logic here
        // For example, you could set the activity's title
        title = "Second Activity"


        // Create a vertical LinearLayout as the root layout
        val rootLayout = LinearLayout(this)
        rootLayout.orientation = LinearLayout.VERTICAL
        setContentView(rootLayout)

        // Create a RecyclerView and add it to the root layout
        recyclerView = RecyclerView(this)
        recyclerView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        rootLayout.addView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = NewsAdapter(emptyList()) // Initially, the adapter is empty
        recyclerView.adapter = adapter

        // You can also finish the activity immediately if needed
        //finish()
        viewModel.fetchNews{ newsList ->
            Log.i("MVVM", newsList.size.toString())
            runOnUiThread {
                recyclerView.adapter = NewsAdapter(newsList)
            }
        }
    }
}

class NewsAdapter(private val newsList: List<NewsArticle>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val news = newsList[position]
    holder.titleTextView.text = news.title
    holder.urlTextView.text = if (news.description.isNotEmpty()) {
        news.description
    } else {
        if (news.creator.isNotEmpty() && news.pubDate.isNotEmpty()) {
            "${news.creator} | ${news.pubDate}"
        } else {
            news.link
        }
    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val urlTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }
}