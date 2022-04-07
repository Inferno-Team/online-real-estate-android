package com.inferno.mobile.bedon_waseet.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.inferno.graphql.GraphqlQuery
import com.inferno.mobile.bedon_waseet.adapters.RealEstateAdapter
import com.inferno.mobile.bedon_waseet.adapters.RealEstateItemClick

import com.inferno.mobile.bedon_waseet.responses.RealEstate
import com.inferno.mobile.bedon_waseet.databinding.HomeFragmentBinding
import com.inferno.mobile.bedon_waseet.R
import com.inferno.mobile.bedon_waseet.ui.home.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        val bottom = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        if (bottom != null)
            bottom.visibility = View.VISIBLE
        val query: String = buildGetRealEstateRequest()
        println(query)
        viewModel.getAllRealEstate(query).observe(requireActivity(), this::realEstateResponse)
        return binding.root
    }

    private fun realEstateResponse(list: ArrayList<RealEstate>) {
        val adapter = RealEstateAdapter(requireContext(), list)
        adapter.setItemClickListener(object : RealEstateItemClick {
            override fun onClick(estate: RealEstate) {
                Toast.makeText(requireContext(), estate.location, Toast.LENGTH_SHORT).show()
            }
        })
        binding.rvRealEstate.adapter = adapter
    }

    private fun buildGetRealEstateRequest(): String {
        return GraphqlQuery.Builder()
            .startQuery()
            .addQuery(
                "real_estate:getAllRealStates",
                arrayOf(
                    "id",
                    "type",
                    "location",
                    "buy_type",
                    "log",
                    "lat",
                    "rate",
                    "budget",
                    "img_url",
                    GraphqlQuery.Query(
                        "user", selectedFields = arrayOf(
                            "user_name"
                        ), args = null
                    ),
                    GraphqlQuery.Query(
                        "rooms",
                        selectedFields = arrayOf(
                            "name",
                            GraphqlQuery.Query(
                                "images",
                                selectedFields = arrayOf("path"), args = null
                            )
                        ),
                        args = null
                    ),
                )
            )
            .end()
            .build()
    }
}