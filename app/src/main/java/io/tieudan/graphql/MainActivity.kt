package io.tieudan.graphql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import io.tieudan.graphql.adapter.LaunchListAdapter
import io.tieudan.graphql.graph.apolloClient
import kotlinx.coroutines.channels.Channel

class MainActivity : AppCompatActivity() {
    val launches = mutableListOf<LaunchListQuery.Launch>()

    override fun onCreate(savedInstanceState: Bundle?) {
        LaunchListQuery.Launch(__typename = "sadfasd",id = "asdfas",site = "asdfasdfsd",mission = null)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ad = LaunchListAdapter(launches)
        val rcv = findViewById<RecyclerView>(R.id.actMain_rcvLaunchList)
        rcv.apply {
            adapter = ad
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        ad.notifyDataSetChanged()
        val channel = Channel<Unit>(Channel.CONFLATED)
        channel.offer(Unit)
        ad.onEndOfListReached = {
            channel.offer(Unit)
        }

        // sử
        lifecycleScope.launchWhenResumed {

            var cursor: String? = null
            for (item in channel) {
                val response =  apolloClient(this@MainActivity).query(LaunchListQuery(cursor = Input.fromNullable(cursor))).await()

                val newLaunches = response.data?.launches?.launches?.filterNotNull()

                if (newLaunches != null) {
                    launches.addAll(newLaunches)
                    ad.notifyDataSetChanged()
                }

                cursor = response.data?.launches?.cursor
                if (response.data?.launches?.hasMore != true) {
                    break
                }
            }
        }
    }
}