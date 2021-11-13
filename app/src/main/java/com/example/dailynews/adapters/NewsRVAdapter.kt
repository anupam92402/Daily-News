package com.example.dailynews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dailynews.Articles
import com.example.dailynews.R
import com.squareup.picasso.Picasso

class NewsRVAdapter(private var ArticlesArraylist: ArrayList<Articles>) :
    RecyclerView.Adapter<NewsRVAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_rv_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        var articles : Articles = ArticlesArraylist[position]
        holder.titleTV.text = articles.title
        holder.subtitleTV.text = articles.description
        Picasso.get().load(articles.urlToImage).into(holder.newsIV)
    }

    override fun getItemCount(): Int {
        return ArticlesArraylist.size
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV : TextView = itemView.findViewById(R.id.idTVNewsHeading)
        val subtitleTV : TextView = itemView.findViewById(R.id.idTVSubTitle)
        val newsIV : ImageView = itemView.findViewById(R.id.idIVNews)
    }
}