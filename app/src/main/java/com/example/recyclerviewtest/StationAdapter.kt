package com.example.recyclerviewtest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.recyclerviewtest.databinding.ItemUserBinding

class StationAdapter(private var stations: List<DatosEstacion>, private val listener: OnClickListener): RecyclerView.Adapter<StationAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int{
        return stations.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val station = stations[position]
        with(holder){
            setListener(station)
            binding.stationName.text = station.Estacion
            binding.stationZone.text = station.zonaATMBarcelona
            Glide.with(context)
                .load(station.Foto)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.stationImage)

            when (station.Operadora){
                "FMB" -> {
                    Glide.with(context)
                        .load("https://i.imgur.com/2rfV8H9.png")
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(binding.operatorImage)
                }

                "FGC" -> {
                    Glide.with(context)
                        .load("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/FGC_logo_%282019%29.svg/2048px-FGC_logo_%282019%29.svg.png")
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(binding.operatorImage)
                }

                "RENFE" -> {
                    Glide.with(context)
                        .load("https://pbs.twimg.com/profile_images/1152191567219822592/fBeRl53I_400x400.png")
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(binding.operatorImage)
                }

                else -> {
                    Glide.with(context)
                        .load("https://upload.wikimedia.org/wikipedia/commons/thumb/b/bd/Toulouse_%22TER%22_symbol.svg/1024px-Toulouse_%22TER%22_symbol.svg.png")
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(binding.operatorImage)
                }
            }
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemUserBinding.bind(view)
        fun setListener(station: DatosEstacion){
            binding.root.setOnClickListener {
                listener.onClick(station)
            }
        }
    }

    fun setStationListItems(stationList: List<DatosEstacion>){
        this.stations = stationList;
    }
}