package com.zrius.natueemoi

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zrius.natueemoi.adpter.PlantAdapter

class PlantPopup(private val adapter: PlantAdapter, private val currentPlant:PlantModel) : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_plant_detail)
        setupComponents()
        setupCloseButton()
        setupDeleteButton()
        setupStarButton()
    }

    private fun updateStar(starButton:ImageView){
        if (currentPlant.like){
            starButton.setImageResource(R.drawable.ic_like)
        }else{
            starButton.setImageResource(R.drawable.ic_unlike)
        }
    }

    private fun setupStarButton() {
    val starButton = findViewById<ImageView>(R.id.star_bottom)

        updateStar(starButton)
        starButton.setOnClickListener {
            currentPlant.like = !currentPlant.like
            val repo = PlantRepository()
            repo.updatePlant(currentPlant)
            updateStar(starButton)
        }
    }

    private fun setupDeleteButton() {
       findViewById<ImageView>(R.id.delete_button).setOnClickListener {
           val repo = PlantRepository()
           repo.deletePlant(currentPlant)
           dismiss()
       }
    }

    private fun setupCloseButton(){
        findViewById<ImageView>(R.id.close_button).setOnClickListener {
            dismiss()
        }
    }

    private fun setupComponents(){
        val plantImage = findViewById<ImageView>(R.id.image_plant)
        Glide.with(adapter.context).load(Uri.parse(currentPlant.imageUrl)).into(plantImage)

        findViewById<TextView>(R.id.popup_plant_name).text = currentPlant.name

        findViewById<TextView>(R.id.popup_plant_description_subtitle).text = currentPlant.description

        findViewById<TextView>(R.id.popup_plant_grow_subtitle).text = currentPlant.grow

        findViewById<TextView>(R.id.popup_plant_water_subtitle).text = currentPlant.water
    }
}