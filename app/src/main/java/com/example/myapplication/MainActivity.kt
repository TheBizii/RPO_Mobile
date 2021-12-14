package com.example.myapplication

import MojeNastavitve
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_main.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle

    val firstFragment=MainFragment()
    val secondFragment=SecondFragment()
    val thirdFragment=ThirdFragment()
    val restavracijaFragment=RestavracijaFragment()
    val dostaveFragment=DostaveFragment()
    val kontaktiFragment=KontaktiFragment()
    val pravilaFragment=PravilaFragment()
    val vrsteHraneFragment=VrsteHraneFragment()
    val zgodovinaFragment=ZgodovinaFragment()

    var clicked = "Empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkTheme()

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
                "prijava" -> {
                    Intent(this, PrijavaActivity::class.java).also {
                        startActivity(it)
                    }
                }
                "pravila" -> setCurrentFragment(pravilaFragment)
                "kontakti" -> setCurrentFragment(kontaktiFragment)
                "profil" -> {
                    Intent(this, ProfilActivity::class.java).also {
                        startActivity(it)
                    }
                }
                "nastavitve" -> {
                    Intent(this, NastavitveActivity::class.java).also {
                        startActivity(it)
                    }
                }
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
                R.id.miPrijava-> {
                    Intent(this, PrijavaActivity::class.java).also {
                        startActivity(it)
                    }
                }
                R.id.miPravila->setCurrentFragment(pravilaFragment)
                R.id.miKontakti->setCurrentFragment(kontaktiFragment)
                R.id.miProfil-> {
                    Intent(this, ProfilActivity::class.java).also {
                        startActivity(it)
                    }
                }
                R.id.miNastavitve->{
                    Intent(this, NastavitveActivity::class.java).also {
                        startActivity(it)
                    }
                }
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

    fun setCurrentFragment(fragment:Fragment)=
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
        clicked = resources.getResourceEntryName(view.id)

        Intent(this, VrsteHraneActivity::class.java).also {
            it.putExtra("clicked", clicked)
            startActivity(it)
        }
    }

     fun checkTheme() {
        when (MojeNastavitve(this).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                delegate.applyDayNight()
            }
        }
    }
}