package com.mpetek.mvvmretrofitsample.view.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mpetek.mvvmretrofitsample.R
import com.mpetek.mvvmretrofitsample.adapter.AllLeaguesRVAdapter
import com.mpetek.mvvmretrofitsample.viewmodel.AllLeaguesViewModel
import kotlinx.android.synthetic.main.fragment_all_leagues.*
import kotlinx.android.synthetic.main.fragment_all_leagues.view.*

class AllLeagues : Fragment() {

    private lateinit var viewModel : AllLeaguesViewModel
    private lateinit var recyclerViewAdapter: AllLeaguesRVAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        activity!!.menuInflater.inflate(R.menu.menu, menu)

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
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_all_leagues, container, false)
        recyclerViewAdapter = AllLeaguesRVAdapter(activity!!,arrayListOf())
        viewModel = ViewModelProviders.of(this).get(AllLeaguesViewModel::class.java)
        viewModel.refreshData()

        recyclerView = rootView.recyclerView
        recyclerView.layoutManager= LinearLayoutManager( context)
        recyclerView.adapter = recyclerViewAdapter


        return rootView
    }
    override fun onStart() {
        super.onStart()

        swipeRefreshLayout.setOnRefreshListener {
            recyclerView.visibility=View.GONE
            dataError.visibility=View.GONE
            dataLoading.visibility=View.VISIBLE
            viewModel.refreshData()
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