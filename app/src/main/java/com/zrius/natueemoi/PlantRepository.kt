package com.zrius.natueemoi

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storageMetadata
import com.zrius.natueemoi.PlantRepository.Singleton.databaseRef
import com.zrius.natueemoi.PlantRepository.Singleton.downloadUri
import com.zrius.natueemoi.PlantRepository.Singleton.plantList
import com.zrius.natueemoi.PlantRepository.Singleton.storageReference
import java.util.*

class PlantRepository {

    object Singleton {

        //contenir le lien de image courant
         var downloadUri:Uri?=null

        private val BUCKET_URL = "gs://natureemoi-be6ba.appspot.com"

        //se connecter a notre espace de stokage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        //se connecter a la reference plants
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        //creer une list qui va contenir notre plantes
        val plantList = arrayListOf<PlantModel>()
    }

    fun updateData(callback: () -> Unit) {
        //absorber les donnees depuis la databaseref -> list
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                //retirer les anciennes
                plantList.clear()

                //recolter la liste
                for (ds in p0.children) {
                    //construire un object plant
                    val plant = ds.getValue(PlantModel::class.java)

                    if (plant != null) {
                        //ajout la plant a notre liste
                        plantList.add(plant)
                    }
                }

                //actionner le callback
                callback()
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })
    }

    //creer une fonction pour envoyer des fichier sur le storage
    fun uploadImage(file: Uri,callback: () -> Unit) {

        if (file != null) {
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if(!task.isSuccessful){
                    task.exception?.let { throw it }
                }

                return@Continuation ref.downloadUrl
            }).addOnCompleteListener { task -> if(task.isSuccessful){
                //recuperer url de limage en ligne
                downloadUri = task.result
                callback()
            }
            }
        }
    }

    // inserer une nouvelle plante en bd
    fun InsertPlant(plantModel: PlantModel,callback: () -> Unit) {
        databaseRef.child(plantModel.id).setValue(plantModel)
        callback()
    }


    //mettrre a jour l'object plant
    fun updatePlant(plantModel: PlantModel) {
        databaseRef.child(plantModel.id).setValue(plantModel)
    }

    //supprimerr une plant de la bd
    fun deletePlant(plantModel: PlantModel) {
        databaseRef.child(plantModel.id).removeValue()
    }


}