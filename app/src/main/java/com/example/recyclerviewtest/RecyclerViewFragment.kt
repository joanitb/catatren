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
        runBlocking {
            var stationList = emptyList<DatosEstacion>()
            withContext(Dispatchers.Default){
                stationList = getStations()
            }

            println("DEBUG: Holaaaaaaa")
            stationAdapter = StationAdapter(stationList, this@RecyclerViewFragment)
            linearLayoutManager = LinearLayoutManager(context)
            binding.recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                adapter = stationAdapter

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    private suspend fun getStations(): List<DatosEstacion>{
        var stations: List<DatosEstacion> = emptyList()
        val call = RetrofitSingleton.service.getData()
        call.enqueue(object : Callback<List<DatosEstacion>> {
            override fun onResponse(call: Call<List<DatosEstacion>>, response: Response<List<DatosEstacion>>) {
                if (response.isSuccessful) {
                    stations = response.body()!!
                    stations.forEach{ station -> println("DEBUG: $station") }
                } else stations = emptyList()
            }

            override fun onFailure(call: Call<List<DatosEstacion>>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
                stations = emptyList()
            }
        })

        println("DEBUG: Hola estoy aqui")
        return stations
    }
}