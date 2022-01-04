package com.zrius.natueemoi.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.zrius.natueemoi.MainActivity
import com.zrius.natueemoi.PlantModel
import com.zrius.natueemoi.PlantRepository.Singleton.plantList
import com.zrius.natueemoi.R
import com.zrius.natueemoi.adpter.PlantAdapter
import com.zrius.natueemoi.adpter.PlantItemDecoration

class HomeFragment(private val context: MainActivity) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_home,container,false)

        //create une liste qui va stocker ces planter


        //recupere le reclycler view
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recyclerView)
        horizontalRecyclerView.adapter = PlantAdapter(context,plantList.filter { !it.like },R.layout.item_horizontal_plant)

        //recupere le second reclycler view
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recyclerView)
        verticalRecyclerView.adapter = PlantAdapter(context,plantList,R.layout.item_vertical_plant)
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())

        return view;
    }
}