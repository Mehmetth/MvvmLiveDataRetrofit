package com.mpetek.mvvmretrofitsample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mpetek.mvvmretrofitsample.model.AllLeaguesData
import com.mpetek.mvvmretrofitsample.model.LeagueDetailData
import com.mpetek.mvvmretrofitsample.service.APIUrl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class LeagueDetailModelView : ViewModel() {
    private val dataApiService = APIUrl()
    private val disposable = CompositeDisposable()

    val data = MutableLiveData<LeagueDetailData>()
    val dataError = MutableLiveData<Boolean>()
    val dataLoading = MutableLiveData<Boolean>()

    fun refreshData(id: String){
        getDataFromApi(id)
    }

    private fun getDataFromApi(id: String){
        dataLoading.value= true

        disposable.add(
            dataApiService.getLeagueDetailData(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<LeagueDetailData>(){
                    override fun onSuccess(t: LeagueDetailData) {
                        data.value=t
                        dataError.value=false
                        dataLoading.value=false
                    }
                    override fun onError(e: Throwable) {
                        dataLoading.value=false
                        dataError.value=true
                        e.printStackTrace()
                    }
                })
        )
    }
}