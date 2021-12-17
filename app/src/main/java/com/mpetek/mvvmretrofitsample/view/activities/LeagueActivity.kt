package com.mpetek.mvvmretrofitsample.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mpetek.mvvmretrofitsample.R
import com.mpetek.mvvmretrofitsample.adapter.LeagueDetailRVAdapter
import com.mpetek.mvvmretrofitsample.viewmodel.LeagueDetailModelView
import kotlinx.android.synthetic.main.activity_league.*

class LeagueActivity : AppCompatActivity() {

    private lateinit var viewModel : LeagueDetailModelView
    val recyclerViewAdapter= LeagueDetailRVAdapter(arrayListOf())
    var leaugeId = ""

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu.findItem(R.id.appSearchBar)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrEmpty())
                {
                    observeLiveData()
                }
                else
                {
                    recyclerViewAdapter.getFilter().filter(newText)
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league)

        leaugeId = intent?.extras?.getString("leaugeId").orEmpty()
        viewModel = ViewModelProviders.of(this).get(LeagueDetailModelView::class.java)
        viewModel.refreshData(leaugeId)

        recyclerView.layoutManager= LinearLayoutManager( this)
        recyclerView.adapter = recyclerViewAdapter
    }
    override fun onStart() {
        super.onStart()

        swipeRefreshLayout.setOnRefreshListener {
            recyclerView.visibility= View.GONE
            dataError.visibility= View.GONE
            dataLoading.visibility= View.VISIBLE
            viewModel.refreshData(leaugeId)
            swipeRefreshLayout.isRefreshing=false
        }

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.data.observe(this, Observer { data->
            data?.let {
                recyclerView.visibility= View.VISIBLE
                recyclerViewAdapter.updateDataList(data)
            }

        })
        viewModel.dataError.observe(this, Observer { error ->
            error?.let {
                if(it) {
                    dataError.visibility = View.VISIBLE
                } else {
                    dataError.visibility = View.GONE
                }
            }

        })
        viewModel.dataLoading.observe(this, Observer { loading->
            loading?.let {
                if (it) {
                    dataLoading.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    dataError.visibility = View.GONE
                } else {
                    dataLoading.visibility = View.GONE
                }
            }

        })
    }
}