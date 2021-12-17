package com.mpetek.mvvmretrofitsample.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.mpetek.mvvmretrofitsample.R
import com.mpetek.mvvmretrofitsample.model.ModelData
import com.mpetek.mvvmretrofitsample.model.AllSportsData
import kotlinx.android.synthetic.main.allsports_item_recyclerview.view.*

class AllSportsRVAdapter(var dataList: ArrayList<ModelData>): RecyclerView.Adapter<AllSportsRVAdapter.DataViewHolder> (), Filterable{
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
            .load(dataList[position].strSportIconGreen)
            .into(holder.view.icon)
        holder.view.title.text = dataList[position].strSport
        holder.view.subtitle.text=dataList[position].strFormat
        holder.view.descrption.text=dataList[position].strSportDescription

        holder.view.title.setOnClickListener {
            if(holder.view.descrption.visibility == View.VISIBLE)
            {
                holder.view.descrption.visibility = View.GONE
            }
            else
            {
                holder.view.descrption.visibility = View.VISIBLE
            }
        }
    }
    fun updateDataList(newDataList: AllSportsData) {
        dataList.clear()
        dataList.addAll(newDataList.sports)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                dataList = filterResults.values as ArrayList<ModelData>
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
                                it.strFormat.toLowerCase().contains(queryString)
                    }
                return filterResults
            }
        }
    }
}
