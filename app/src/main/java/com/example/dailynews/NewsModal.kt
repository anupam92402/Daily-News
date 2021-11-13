package com.example.dailynews

data class NewsModal (var totalResults:Int ,
                      var status:String,
                      var articles:ArrayList<Articles>
                      )