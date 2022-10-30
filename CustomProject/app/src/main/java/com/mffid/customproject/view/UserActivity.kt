package com.mffid.customproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.mffid.customproject.R
import com.mffid.customproject.databinding.ActivityUserBinding
import com.mffid.customproject.model.UserModel
import com.mffid.customproject.service.UserAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private var userModels: ArrayList<UserModel>? = null
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private var compositeDisposable: CompositeDisposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        compositeDisposable = CompositeDisposable()
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView)

        userData()
    }

    fun userData(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(UserAPI::class.java)


        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))
    }

    private fun handleResponse(userList: List<UserModel>){
        userModels = ArrayList(userList)


        var lists = ArrayList(userList)
        val titleList:ArrayList<String> = ArrayList()
        for (list in lists) {
            println("Title: " + list.name)
            titleList.add(list.name)
        }
        val arrayAdapter = ArrayAdapter<String>(this, R.layout.dropdown_item, titleList)
        autoCompleteTextView.setAdapter(arrayAdapter)
        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val value = arrayAdapter.getItem(position) ?: ""
            for (list in lists) {
                if (list.name.contains(value)) {

                    userNameTextView.text = list.username
                    nameTextView.text = list.name
                    emailTextView.text = list.email
                    phoneTextView.text = list.phone
                    websiteTextView.text = list.website
                    break
                }

            }
        }



    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()
    }
}