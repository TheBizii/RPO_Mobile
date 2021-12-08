package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.Switch
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import kotlinx.android.synthetic.main.activity_restavracija.*

class NastavitveActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    var clicked = "Empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nastavitve)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home-> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("nav", "home")
                    startActivity(intent)
                }
                R.id.basket-> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("nav", "basket")
                    startActivity(intent)
                }
                R.id.search-> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("nav", "search")
                    startActivity(intent)
                }
            }
            true
        }
        toggle = ActionBarDrawerToggle(this, drawerLayout2, R.string.open, R.string.close)
        drawerLayout2.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView2.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miRestavracije-> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("nav", "restavracija")
                    startActivity(intent)
                }
                R.id.miVrsteHrane-> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("nav", "vrsteHrane")
                    startActivity(intent)
                }
                R.id.miZgodovina-> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("nav", "zgodovina")
                    startActivity(intent)
                }
                R.id.miDostave-> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("nav", "dostave")
                    startActivity(intent)
                }
                R.id.miPrijava-> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("nav", "prijava")
                    startActivity(intent)
                }
                R.id.miPravila-> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("nav", "pravila")
                    startActivity(intent)
                }
                R.id.miKontakti-> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("nav", "kontakti")
                    startActivity(intent)
                }
                R.id.miNastavitve-> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("nav", "nastavitve")
                    startActivity(intent)
                }
                R.id.miProfil-> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("nav", "profil")
                    startActivity(intent)
                }
            }
            true
        }


        val sw = findViewById<SwitchCompat>(R.id.switchtheme)
        //val cb = findViewById<CheckBox>(R.id.checkBox)

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            sw.isChecked = true
            //cb.isChecked = false
            sw.isClickable = true
            sw.isFocusable = true
        }
        else if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
            sw.isChecked = false
            //cb.isChecked = false
            sw.isClickable = true
            sw.isFocusable = true
        }
        else {
            sw.isClickable = true
            sw.isFocusable = true
            //cb.isChecked = true
        }

        sw.setOnCheckedChangeListener { buttonView, isChecked ->
            if(sw.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        /*cb.setOnCheckedChangeListener { buttonView, isChecked ->

            if(cb.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                sw.isClickable = false
                sw.isFocusable = false
            }
            else{
                if(sw.isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                sw.isFocusable = true
                sw.isClickable = true
            }
        }*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}