package com.example.recyclerviewtest

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtest.databinding.FragmentRecyclerViewBinding
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class RecyclerViewFragment : Fragment(), OnClickListener {

    private lateinit var stationAdapter: StationAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var binding: FragmentRecyclerViewBinding

    override fun onClick(station: DatosEstacion) {
        parentFragmentManager.setFragmentResult(
            "Station", bundleOf("Station" to station)
        )
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, DetailFragment())
            setReorderingAllowed(true)
            addToBackStack(null)
            commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var stationList = emptyList<DatosEstacion>()
        runBlocking {
            val apiInterface = ApiInterface.create().getData()
            apiInterface.enqueue( object : Callback<List<DatosEstacion>>{
                override fun onResponse(call: Call<List<DatosEstacion>>?, response: Response<List<DatosEstacion>>?) {
                    if(response?.body() != null) stationList = response.body()!!
                    stationAdapter.setStationListItems(response!!.body()!!)
                    stationAdapter = StationAdapter(stationList, this@RecyclerViewFragment)
                }

                override fun onFailure(call: Call<List<DatosEstacion>>?, t: Throwable?) {

                }
            })
        }
        println("DEBUG: Holaaaaaaa")
        stationAdapter = StationAdapter(stationList, this)
        linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = stationAdapter

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }
}