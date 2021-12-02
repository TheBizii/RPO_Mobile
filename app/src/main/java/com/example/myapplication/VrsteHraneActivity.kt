package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_vrste_hrane.*
import kotlinx.android.synthetic.main.activity_restavracija.bottomNavigationView

class VrsteHraneActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vrste_hrane)

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
            }
            true
        }
    }

    fun imageClickRestavracija(view: android.view.View) {
        val clicked = resources.getResourceEntryName(view.id)

        Intent(this, RestavracijaActivity::class.java).also {
            it.putExtra("clicked", clicked)
            startActivity(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}