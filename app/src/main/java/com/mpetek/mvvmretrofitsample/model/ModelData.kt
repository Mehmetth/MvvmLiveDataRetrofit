package com.mpetek.mvvmretrofitsample.model


import com.google.gson.annotations.SerializedName

data class AllSportsData(@SerializedName("sports")
                      var sports: List<ModelData>)
data class ModelData(

    @SerializedName("idSport")
    var idSport: String,
    @SerializedName("strSport")
    var strSport: String,
    @SerializedName("strFormat")
    var strFormat: String,
    @SerializedName("strSportThumb")
    var strSportThumb: String,
    @SerializedName("strSportIconGreen")
    var strSportIconGreen: String,
    @SerializedName("strSportDescription")
    var strSportDescription: String)
