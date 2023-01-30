package com.example.recyclerviewtest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtest.databinding.FragmentRecyclerViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerViewFragment : Fragment(), OnClickListener {

    private lateinit var stationAdapter: StationAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var binding: FragmentRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        stationAdapter = StationAdapter(getStations(), this)
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

    private fun getStations(): MutableList<DatosEstacion>{
        var stations: List<DatosEstacion>? = listOf(DatosEstacion(null,null,null,null,null,null,null,null))
        val call = RetrofitSingleton.service.getData()
        call.enqueue(object : Callback<List<DatosEstacion>> {
            override fun onResponse(call: Call<List<DatosEstacion>>, response: Response<List<DatosEstacion>>) {
                if (response.isSuccessful) {
                    stations = response.body()
                    stations?.forEach(::println)
                } else {
                    println("Error getting data!")
                }
            }

            override fun onFailure(call: Call<List<DatosEstacion>>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }
        })

        if (stations != null) return stations!!.toMutableList()
        else return mutableListOf(DatosEstacion(null,null,null,null,null,null,null,null))
    }
}