package com.mffid.customproject.service

import com.mffid.customproject.model.UserModel
import io.reactivex.Observable
import retrofit2.http.GET

interface UserAPI {

    @GET("users")
    fun getData() : Observable<List<UserModel>>
}