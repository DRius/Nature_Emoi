package com.zrius.natueemoi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zrius.natueemoi.fragments.AddPlantFragment
import com.zrius.natueemoi.fragments.CollectionFragment
import com.zrius.natueemoi.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragmant(HomeFragment(this),R.string.home_page_title)


        //importer le bottom navigtion
        val  navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_page ->{
                    loadFragmant(HomeFragment(this),R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.collection_page ->{
                    loadFragmant(CollectionFragment(this),R.string.collection_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_page ->{
                    loadFragmant(AddPlantFragment(this),R.string.add_plant_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {return@setOnNavigationItemSelectedListener false}
            }
        }



    }

    private fun loadFragmant(fragment: Fragment, title:Int) {
        //charger notre plant repository
        val repo = PlantRepository()

        findViewById<TextView>(R.id.id_title).text = resources.getString(title)

        //mettre a jour la list
        repo.updateData {
            //injecter le fragment dans notre boite(fragment_container)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_content, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}