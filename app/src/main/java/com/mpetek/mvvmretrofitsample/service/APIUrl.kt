package com.mpetek.mvvmretrofitsample.service

import com.mpetek.mvvmretrofitsample.model.AllLeaguesData
import com.mpetek.mvvmretrofitsample.model.AllSportsData
import com.mpetek.mvvmretrofitsample.model.LeagueDetailData
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class APIUrl {
    private val BASE_URL = "https://www.thesportsdb.com/api/v1/json/2/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(DataAPI::class.java)

    fun getAllSportsData() : Single<AllSportsData> {
        return api.getAllSports()
    }

    fun getAllLeaguesData() : Single<AllLeaguesData> {
        return api.getAllLeagues()
    }
    fun getLeagueDetailData(id: String) : Single<LeagueDetailData> {
            return api.getDetailLeagues(id)
    }
}