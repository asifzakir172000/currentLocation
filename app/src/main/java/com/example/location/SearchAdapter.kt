package com.example.location

import android.location.Address
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class SearchAdapter (private val data: List<Address>, val listener: SearchCallback) : RecyclerView.Adapter<SearchAdapter.ViewHolder>(){

//    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
//        LayoutContainer


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.search_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.title.text = item.getAddressLine(0)
        holder.card.setOnClickListener {
            listener.onText(position)
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.txtSearchText)
        var card: ConstraintLayout = view.findViewById(R.id.card)
//        var genre: TextView = view.findViewById(R.id.genre)
    }

    interface SearchCallback {
        fun onText(position: Int)
    }

}