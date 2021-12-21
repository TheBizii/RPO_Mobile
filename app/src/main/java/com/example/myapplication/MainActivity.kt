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
import kotlinx.android.synthetic.main.activity_main.drawerLayout
import kotlinx.android.synthetic.main.activity_main.navView
import Kosarica


class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle

    private val firstFragment=MainFragment()
    private val secondFragment=SecondFragment()
    private val activityKosara=KosaraActivity()
    private val restavracijaFragment=RestavracijaFragment()
    private val dostaveFragment=DostaveFragment()
    private val kontaktiFragment=KontaktiFragment()
    private val pravilaFragment=PravilaFragment()
    private val vrsteHraneFragment=VrsteHraneFragment()
    private val zgodovinaFragment=ZgodovinaFragment()

    var clicked = "Empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val i = Kosarica.arrayList.size

        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)

        val s1: String? = sh.getString("username", "")

        if(s1 == ""){
            hideOption(R.id.miDostave)
            hideOption(R.id.miZgodovina)
            hideOption(R.id.miProfil)
            showOption(R.id.miPrijava)
        }
        else{
            showOption(R.id.miDostave)
            showOption(R.id.miZgodovina)
            showOption(R.id.miProfil)
            hideOption(R.id.miPrijava)
        }

        checkTheme()

        val bundle: Bundle? = intent.extras

        if (bundle != null) {
            when(bundle.get("nav").toString()){
                "home" -> setCurrentFragment(firstFragment)
                "search" -> setCurrentFragment(secondFragment)
                "basket" -> {
                    Intent(this, KosaraActivity::class.java).also {
                        startActivity(it)
                    }
                }
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
                R.id.basket-> {
                    Intent(this, KosaraActivity::class.java).also {
                        startActivity(it)
                    }
                }
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

    private fun hideOption(id: Int) {
        val item: MenuItem = navView.menu.findItem(id)
        item.isVisible = false
    }

    private fun showOption(id: Int) {
        val item: MenuItem = navView.menu.findItem(id)
        item.isVisible = true
    }
}