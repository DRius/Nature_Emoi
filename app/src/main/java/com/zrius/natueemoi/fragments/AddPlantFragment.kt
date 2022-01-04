package com.zrius.natueemoi.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.zrius.natueemoi.MainActivity
import com.zrius.natueemoi.PlantModel
import com.zrius.natueemoi.PlantRepository
import com.zrius.natueemoi.PlantRepository.Singleton.downloadUri
import com.zrius.natueemoi.R
import java.util.*

class AddPlantFragment (private val context:MainActivity):Fragment() {

    private var uploadImage:ImageView? = null
    private var file:Uri?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_plant, container,false)

        //recuperer le button pour charger image
        val pickupImageView = view.findViewById<Button>(R.id.upload_button)


        uploadImage = view.findViewById<ImageView>(R.id.preview_image)
        pickupImageView.setOnClickListener {
            pikupImage()
        }

        //recuperer le button confirmer
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)

        confirmButton.setOnClickListener {
            sendForm(view)
        }

        return view
    }

    private fun sendForm(view : View){
        val repo = PlantRepository()
        val name_input = view.findViewById<EditText>(R.id.name_input)
        val description_input = view.findViewById<EditText>(R.id.description_input)
        val  loading = view.findViewById<ProgressBar>(R.id.loading)
        loading.visibility  = VISIBLE

        repo.uploadImage(file!!){
            val plantName = name_input.text.toString()
            val plantDescription = description_input.text.toString()
            val grow = view.findViewById<Spinner>(R.id.grow_spinner_input).selectedItem.toString()
            val water = view.findViewById<Spinner>(R.id.water_spinner_input).selectedItem.toString()
            val downloadImageUrl = downloadUri

            //creer un nouvel object PlantMode
            val plant = PlantModel(UUID.randomUUID().toString(), plantName,plantDescription,downloadImageUrl.toString(),false, grow ,water)

            repo.InsertPlant(plant){
                loading.visibility = GONE
                name_input.setText("")
                description_input.setText(" ")
                downloadUri = null
                uploadImage?.setImageURI(Uri.parse(" "))
            }
        }



    }

    private fun pikupImage(){
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"select Picture"),1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1000 && resultCode == Activity.RESULT_OK){
            //verifier si les donnees sont null
            if(data == null || data.data == null){
                return
            }else{
                //recuperer limage selectionner
                file = data.data

                //mettre a jour l'apercu
                uploadImage?.setImageURI(file)

            }
        }
    }
}