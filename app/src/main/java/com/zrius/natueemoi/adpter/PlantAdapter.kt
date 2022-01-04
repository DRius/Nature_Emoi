package com.zrius.natueemoi.adpter

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zrius.natueemoi.*

class PlantAdapter(val context: MainActivity, private val plantList: List<PlantModel>, private val layoutId:Int) :RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    //boite pour rangger tout les composant a controler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName:TextView? = view.findViewById(R.id.name_item)
        val plantDescription:TextView? = view.findViewById(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //recuperer les information de la plant a cette position
        val currentPlant : PlantModel = plantList[position];


        //recuperer le repository
        val repo = PlantRepository()

        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)
        holder.plantName?.text = currentPlant.name
        holder.plantDescription?.text = currentPlant.description

        if(currentPlant.like){
            holder.starIcon.setImageResource(R.drawable.ic_like)
        }else{
            holder.starIcon.setImageResource(R.drawable.ic_unlike)
        }

        //rqjouter une interqction sur cette etoitle
        holder.starIcon.setOnClickListener {
            //inverse si le bouton est like ou non
            currentPlant.like = !currentPlant.like

            //mettre a jour l'object plant
            repo.updatePlant(currentPlant)
        }

        //interaction lors du click sur une plant
        holder.itemView.setOnClickListener {
            //affichege de la popup
            PlantPopup(this, currentPlant).show()
        }

    }

    override fun getItemCount(): Int {
        return plantList.size;
    }
}