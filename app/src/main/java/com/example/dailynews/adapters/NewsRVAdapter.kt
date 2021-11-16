package com.example.dailynews.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.dailynews.Articles
import com.example.dailynews.R
import com.squareup.picasso.Picasso

class NewsRVAdapter(
    private var ArticlesArraylist: ArrayList<Articles>,
    private var listener: NewsItemClicked,var context:Context
) :
    RecyclerView.Adapter<NewsRVAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_rv_item, parent, false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(ArticlesArraylist[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        var articles: Articles = ArticlesArraylist[position]
        holder.titleTV.text = articles.title
        holder.subtitleTV.text = articles.description
        Picasso.get().load(articles.urlToImage).into(holder.newsIV)
        holder.shareButton.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type="text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey checkout this news ${articles.url}")
            context.startActivity(shareIntent)
        }
    }

    override fun getItemCount(): Int {
        return ArticlesArraylist.size
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById(R.id.idTVNewsHeading)
        val subtitleTV: TextView = itemView.findViewById(R.id.idTVSubTitle)
        val newsIV: ImageView = itemView.findViewById(R.id.idIVNews)
        val shareButton : ImageButton = itemView.findViewById(R.id.idShareButton)
    }

    interface NewsItemClicked {
        fun onItemClicked(item: Articles)
    }

}