package com.mpetek.mvvmretrofitsample.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.mpetek.mvvmretrofitsample.R
import com.mpetek.mvvmretrofitsample.model.AllLeaguesData
import com.mpetek.mvvmretrofitsample.model.AllSportsData
import com.mpetek.mvvmretrofitsample.model.LeagueData
import com.mpetek.mvvmretrofitsample.model.ModelData
import com.mpetek.mvvmretrofitsample.view.activities.LeagueActivity
import kotlinx.android.synthetic.main.allleagues_item_recyclerview.view.*

class AllLeaguesRVAdapter(var activity: Activity,var dataList: ArrayList<LeagueData>): RecyclerView.Adapter<AllLeaguesRVAdapter.DataViewHolder> (),
    Filterable {
    class DataViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.allleagues_item_recyclerview,parent,false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.view.league.text = dataList[position].strLeague
        holder.view.sport.text=dataList[position].strSport
        holder.view.league_alternate.text=dataList[position].strLeagueAlternate

        holder.view.rootView.setOnClickListener {
            val intent = Intent(activity, LeagueActivity::class.java)
            intent.putExtra("leaugeId", dataList[position].idLeague)
            activity.startActivity(intent)
        }
    }
    fun updateDataList(newDataList: AllLeaguesData) {
        dataList.clear()
        dataList.addAll(newDataList.sports)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                dataList = filterResults.values as ArrayList<LeagueData>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString==null || queryString.isEmpty())
                    dataList
                else
                    dataList.filter {
                        it.strSport.toLowerCase().contains(queryString) ||
                                it.strLeague.toLowerCase().contains(queryString)
                    }
                return filterResults
            }
        }
    }
}