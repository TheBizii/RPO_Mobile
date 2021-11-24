package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_main.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_restavracija.*

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    val firstFragment=MainFragment()
    val secondFragment=SecondFragment()
    val thirdFragment=ThirdFragment()
    val restavracijaFragment=RestavracijaFragment()
    val dostaveFragment=DostaveFragment()
    val kontaktiFragment=KontaktiFragment()
    val nastavitveFragment=NastavitveFragment()
    val pravilaFragment=PravilaFragment()
    val prijavaFragment=PrijavaFragment()
    val vrsteHraneFragment=VrsteHraneFragment()
    val zgodovinaFragment=ZgodovinaFragment()

    var clicked = "Empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCurrentFragment(firstFragment)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miRestavracije->setCurrentFragment(restavracijaFragment)
                R.id.miVrsteHrane->setCurrentFragment(vrsteHraneFragment)
                R.id.miZgodovina->setCurrentFragment(zgodovinaFragment)
                R.id.miDostave->setCurrentFragment(dostaveFragment)
                R.id.miPrijava->setCurrentFragment(prijavaFragment)
                R.id.miPravila->setCurrentFragment(pravilaFragment)
                R.id.miKontakti->setCurrentFragment(kontaktiFragment)
                R.id.miNastavitve->setCurrentFragment(nastavitveFragment)
            }
            true
        }

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(firstFragment)
                R.id.search->setCurrentFragment(secondFragment)
                R.id.basket->setCurrentFragment(thirdFragment)
            }
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

    fun imageClickRestavracija(view: android.view.View) {
        clicked = resources.getResourceEntryName(view.id)
//        setImageResource(clicked)
        setCurrentFragment(restavracijaFragment)
    }

    fun setImageResource(clicked: String){
        // TODO implement so that image changes

        when(clicked){
            "mcdonalds" -> {
                imgRestavracija.setImageResource(R.drawable.mcdonalds)
                imeRestavracijeText.text = "McDonalds"
            }
            "burgerking" -> {
                imgRestavracija.setImageResource(R.drawable.burgerking)
                imeRestavracijeText.text = "Burger King"
            }
            "kfc" -> {
                imgRestavracija.setImageResource(R.drawable.kfc)
                imeRestavracijeText.text = "KFC"

            }
            "chinese" -> {
                imgRestavracija.setImageResource(R.drawable.chinese)
                imeRestavracijeText.text = "Chinese restaurant"
            }
            "spar" -> {
                imgRestavracija.setImageResource(R.drawable.spar)
                imeRestavracijeText.text = "Spar restavracija"
            }
            "subway" -> {
                imgRestavracija.setImageResource(R.drawable.subway)
                imeRestavracijeText.text = "Subway"
            }
        }
    }

    fun imageClickVrsteHrane(view: android.view.View) {
        setCurrentFragment(vrsteHraneFragment)
    }
}