package com.inferno.mobile.bedon_waseet.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inferno.mobile.bedon_waseet.repos.RealEstateRepo
import com.inferno.mobile.bedon_waseet.responses.RealEstate

class HomeViewModel : ViewModel() {
    private val repo = RealEstateRepo
    fun getAllRealEstate(query: String): LiveData<ArrayList<RealEstate>> {
        return repo.getAllRealEstate(query)
    }
}