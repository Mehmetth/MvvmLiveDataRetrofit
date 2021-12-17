package com.mpetek.mvvmretrofitsample.model

import com.google.gson.annotations.SerializedName

data class AllLeaguesData(@SerializedName("leagues")
                         var sports: List<LeagueData>)
data class LeagueData(

    @SerializedName("idLeague")
    var idLeague: String,
    @SerializedName("strLeague")
    var strLeague: String,
    @SerializedName("strSport")
    var strSport: String,
    @SerializedName("strLeagueAlternate")
    var strLeagueAlternate: String)