package com.mpetek.mvvmretrofitsample.service

import com.mpetek.mvvmretrofitsample.model.AllLeaguesData
import com.mpetek.mvvmretrofitsample.model.AllSportsData
import com.mpetek.mvvmretrofitsample.model.LeagueDetailData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DataAPI {
    @GET("all_sports.php")
    fun getAllSports(): Single<AllSportsData>

    @GET("all_leagues.php")
    fun getAllLeagues(): Single<AllLeaguesData>

    @GET("lookuptable.php")
    fun getDetailLeagues(@Query("l") id: String): Single<LeagueDetailData>
}