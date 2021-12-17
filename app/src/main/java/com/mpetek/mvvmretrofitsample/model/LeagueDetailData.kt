package com.mpetek.mvvmretrofitsample.model

import com.google.gson.annotations.SerializedName

data class LeagueDetailData(@SerializedName("table")
                          var table: List<TeamData>)
data class TeamData(
    @SerializedName("idTeam")
    var idTeam: String,
    @SerializedName("strTeam")
    var strTeam: String,
    @SerializedName("strTeamBadge")
    var strTeamBadge: String,
    @SerializedName("strLeague")
    var strLeague: String,
    @SerializedName("strDescription")
    var strDescription: String)