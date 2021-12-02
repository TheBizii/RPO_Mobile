package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_main.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*
import androidx.drawerlayout.widget.DrawerLayout

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

        val bundle: Bundle? = intent.extras

        if (bundle != null) {
            when(bundle.get("nav").toString()){
                "home" -> setCurrentFragment(firstFragment)
                "search" -> setCurrentFragment(secondFragment)
                "basket" -> setCurrentFragment(thirdFragment)
                "restavracija" -> setCurrentFragment(restavracijaFragment)
                "vrsteHrane" -> setCurrentFragment(vrsteHraneFragment)
                "zgodovina" -> setCurrentFragment(zgodovinaFragment)
                "dostave" -> setCurrentFragment(dostaveFragment)
                "prijava" -> setCurrentFragment(prijavaFragment)
                "pravila" -> setCurrentFragment(pravilaFragment)
                "kontakti" -> setCurrentFragment(kontaktiFragment)
                "nastavitve" -> setCurrentFragment(nastavitveFragment)
                else -> setCurrentFragment(firstFragment)
            }
        }
        else setCurrentFragment(firstFragment)

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

        Intent(this, RestavracijaActivity::class.java).also {
            it.putExtra("clicked", clicked)
            startActivity(it)
        }
    }

    fun imageClickVrsteHrane(view: android.view.View) {
        Intent(this, VrsteHraneActivity::class.java).also {
            startActivity(it)
        }
    }
}