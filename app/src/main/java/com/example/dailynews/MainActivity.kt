package com.example.dailynews

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailynews.adapters.CategoryRVAdapter
import com.example.dailynews.adapters.NewsRVAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), NewsRVAdapter.NewsItemClicked,
    CategoryRVAdapter.CategoriesItemClicked {

    lateinit var articlesArrayList: ArrayList<Articles>
    lateinit var categoryRVModalArrayList: ArrayList<CategoryRVModal>
    lateinit var newsRVAdapter: NewsRVAdapter
    lateinit var categoryRVAdapter: CategoryRVAdapter
    var currentCategory: String = "All"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        articlesArrayList = ArrayList()
        categoryRVModalArrayList = ArrayList()
        newsRVAdapter = NewsRVAdapter(articlesArrayList, this,this)
        categoryRVAdapter = CategoryRVAdapter(categoryRVModalArrayList, this)
        idRVNews.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = newsRVAdapter
        }
        idRVCategories.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryRVAdapter
        }
        getCategories()
        getNews("All")
        newsRVAdapter.notifyDataSetChanged()
        idRefreshLayout.setOnRefreshListener {
            getNews(currentCategory)
            idRefreshLayout.isRefreshing = false
        }
    }

    private fun getCategories() {
        categoryRVModalArrayList.add(
            CategoryRVModal(
                "All",
                "https://images.unsplash.com/photo-1570961999607-df226979f156?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8YWxsfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60"
            )
        )
        categoryRVModalArrayList.add(
            CategoryRVModal(
                "Technology",
                "https://images.unsplash.com/photo-1488590528505-98d2b5aba04b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"
            )
        )
        categoryRVModalArrayList.add(
            CategoryRVModal(
                "Science",
                "https://media.istockphoto.com/photos/pharmacist-conducts-experiment-with-dried-hemp-picture-id1220314846?b=1&k=20&m=1220314846&s=170667a&w=0&h=VdMRQkc6bLQMiG7qXYZaCHso8k8wxmg3ixqc3kxPD_A="
            )
        )
        categoryRVModalArrayList.add(
            CategoryRVModal(
                "Sports",
                "https://media.istockphoto.com/photos/silhouette-action-sport-picture-id1272269793?b=1&k=20&m=1272269793&s=170667a&w=0&h=xie_NP8GQ6LFpiA0WLqoVUF7y2wyebpCJDQ4wJwPy40="
            )
        )
        categoryRVModalArrayList.add(
            CategoryRVModal(
                "General",
                "https://images.unsplash.com/photo-1637066272370-ad13c034cc2d?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxNHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60"
            )
        )
        categoryRVModalArrayList.add(
            CategoryRVModal(
                "Business",
                "https://images.unsplash.com/photo-1522202176988-66273c2fd55f?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fGJ1c2luZXNzfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60"
            )
        )
        categoryRVModalArrayList.add(
            CategoryRVModal(
                "Entertainment",
                "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8ZW50ZXJ0YWlubWVudHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60"
            )
        )
        categoryRVModalArrayList.add(
            CategoryRVModal(
                "Health",
                "https://images.unsplash.com/photo-1532938911079-1b06ac7ceec7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80"
            )
        )
        categoryRVAdapter.notifyDataSetChanged()
    }

    private fun getNews(category: String) {
        PBLoading.visibility = View.VISIBLE
        val apiKey: String = "7b730af12ff14816b75276435f604fe2"
        articlesArrayList.clear()
        var categoryUrl: String =
            "https://newsapi.org/v2/top-headlines?country=in&category=$category&apiKey=$apiKey"
        var url: String =
            "https://newsapi.org/v2/top-headlines?country=in&language=en&apiKey=$apiKey"
        val Base_URL = "https://newsapi.org/"
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Base_URL)
            .build()
        val retrofitApi = retrofit.create(RetrofitApi::class.java)
        val call: Call<NewsModal>
        if (category.equals("All")) {
            call = retrofitApi.getAllNews(url)
        } else {
            call = retrofitApi.getNewsByCategory(categoryUrl)
        }
        call.enqueue(object : Callback<NewsModal> {

            override fun onResponse(call: Call<NewsModal>, response: Response<NewsModal>) {
                var newsModal = response.body()
                PBLoading.visibility = View.GONE
                var articles = newsModal?.articles

                for (i in 0 until articles!!.size) {
                    articlesArrayList.add(
                        Articles(
                            articles[i].title,
                            articles[i].description,
                            articles[i].urlToImage,
                            articles[i].url,
                            articles[i].content
                        )
                    )
                }
                newsRVAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<NewsModal>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed to load News", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onItemClicked(item: Articles) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }

    override fun OnCategoryClicked(item: CategoryRVModal) {
        getNews(item.category)
        currentCategory = item.category
    }

}