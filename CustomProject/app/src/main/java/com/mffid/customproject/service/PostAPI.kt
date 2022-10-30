package com.mffid.customproject.service

import com.mffid.customproject.model.PostModel
import io.reactivex.Observable
import retrofit2.http.GET

interface PostAPI {

    @GET("posts")
    fun getData() : Observable<List<PostModel>>
}