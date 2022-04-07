package com.inferno.mobile.bedon_waseet.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.inferno.mobile.bedon_waseet.BaseApplication
import com.inferno.mobile.bedon_waseet.R
import com.inferno.mobile.bedon_waseet.databinding.RealEstateItemBinding
import com.inferno.mobile.bedon_waseet.responses.RealEstate


interface RealEstateItemClick {
    fun onClick(estate: RealEstate)
}


class RealEstateAdapter(
    private val context: Context,
    private val estates: ArrayList<RealEstate>
) : RecyclerView.Adapter<EstateHolder>() {
    private lateinit var realEstateItemClickListener: RealEstateItemClick

    fun setItemClickListener(l: RealEstateItemClick) {
        realEstateItemClickListener = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateHolder {
        return EstateHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.real_estate_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EstateHolder, position: Int) {
        val estate = estates[position]
        Glide.with(context)
//            .load(BaseApplication.BASE_URL + estate.img_url)
            .load(R.drawable.img1)
            .centerCrop()
            .placeholder(AppCompatResources.getDrawable(context, R.drawable.real_estate_logo))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(holder.binding.imgRealEstate)
        holder.binding.estate = estate
        holder.binding.root.setOnClickListener {
            realEstateItemClickListener.onClick(estate)
        }


    }

    override fun getItemCount(): Int {
        return estates.size
    }
}

class EstateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: RealEstateItemBinding = RealEstateItemBinding.bind(itemView)
}