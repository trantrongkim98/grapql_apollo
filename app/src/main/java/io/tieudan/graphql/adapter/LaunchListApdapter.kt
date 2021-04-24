package io.tieudan.graphql.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.tieudan.graphql.LaunchListQuery
import io.tieudan.graphql.R

class LaunchListAdapter(private val launches: List<LaunchListQuery.Launch>) : RecyclerView.Adapter<LaunchListAdapter.LaunchListViewHolder>() {

    inner class LaunchListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)

        fun setText(t: String){
            textView.text = t
        }
    }
    var onEndOfListReached: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.launch_list_item,parent,false)
        return LaunchListViewHolder(view)
    }

    override fun getItemCount() = launches.size

    override fun onBindViewHolder(holder: LaunchListViewHolder, position: Int) {
        val launch = launches[position]
        holder.setText(launch.site!!)
        if (position == launches.size - 1) {
            onEndOfListReached?.invoke()
        }
    }
}