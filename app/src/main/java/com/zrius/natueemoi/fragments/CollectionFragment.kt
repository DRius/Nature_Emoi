package com.zrius.natueemoi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zrius.natueemoi.MainActivity
import com.zrius.natueemoi.PlantRepository.Singleton.plantList
import com.zrius.natueemoi.R
import com.zrius.natueemoi.adpter.PlantAdapter
import com.zrius.natueemoi.adpter.PlantItemDecoration

class CollectionFragment(private val context:MainActivity) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_collection,container,false)

        //recuperer ma reclycler view
        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collectio_recycler_list)
        collectionRecyclerView.adapter = PlantAdapter(context, plantList.filter {it.like},R.layout.item_vertical_plant)
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)
        collectionRecyclerView.addItemDecoration(PlantItemDecoration())

        return view
    }
}