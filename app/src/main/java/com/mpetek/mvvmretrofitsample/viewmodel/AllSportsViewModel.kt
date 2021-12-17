package com.mpetek.mvvmretrofitsample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mpetek.mvvmretrofitsample.model.AllSportsData
import com.mpetek.mvvmretrofitsample.service.APIUrl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class AllSportsViewModel: ViewModel() {
        private val dataApiService = APIUrl()
        private val disposable = CompositeDisposable()

        val data = MutableLiveData<AllSportsData>()
        val dataError = MutableLiveData<Boolean>()
        val dataLoading = MutableLiveData<Boolean>()
        fun refreshData(){
            getDataFromApi()
        }

    private fun getDataFromApi(){
        dataLoading.value= true

        disposable.add(
            dataApiService.getAllSportsData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<AllSportsData>(){
                    override fun onSuccess(t: AllSportsData) {
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