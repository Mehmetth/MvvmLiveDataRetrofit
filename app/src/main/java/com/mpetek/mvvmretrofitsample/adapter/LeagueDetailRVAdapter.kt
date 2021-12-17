package com.mpetek.mvvmretrofitsample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.mpetek.mvvmretrofitsample.R
import com.mpetek.mvvmretrofitsample.model.AllLeaguesData
import com.mpetek.mvvmretrofitsample.model.LeagueData
import com.mpetek.mvvmretrofitsample.model.LeagueDetailData
import com.mpetek.mvvmretrofitsample.model.TeamData
import kotlinx.android.synthetic.main.allleagues_item_recyclerview.view.*
import kotlinx.android.synthetic.main.allleagues_item_recyclerview.view.icon
import kotlinx.android.synthetic.main.allsports_item_recyclerview.view.*

class LeagueDetailRVAdapter(var dataList: ArrayList<TeamData>): RecyclerView.Adapter<LeagueDetailRVAdapter.DataViewHolder> (),
    Filterable {
    class DataViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.allsports_item_recyclerview,parent,false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        Picasso.get()
            .load(dataList[position].strTeamBadge)
            .into(holder.view.icon)
        holder.view.title.text = dataList[position].strTeam
        holder.view.subtitle.text=dataList[position].strLeague
        holder.view.descrption.text=dataList[position].strDescription

        holder.view.rootView.setOnClickListener {
            println("onBindViewHolder ${dataList[position].idTeam}")
        }
    }
    fun updateDataList(newDataList: LeagueDetailData) {
        dataList.clear()
        dataList.addAll(newDataList.table)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                dataList = filterResults.values as ArrayList<TeamData>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString==null || queryString.isEmpty())
                    dataList
                else
                    dataList.filter {
                        it.strLeague.toLowerCase().contains(queryString) ||
                                it.strTeam.toLowerCase().contains(queryString)
                    }
                return filterResults
            }
        }
    }
}