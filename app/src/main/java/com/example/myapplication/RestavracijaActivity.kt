package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_restavracija.*
import kotlinx.android.synthetic.main.activity_restavracija.bottomNavigationView

class RestavracijaActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restavracija)

        val bundle: Bundle? = intent.extras

        val clicked = bundle?.get("clicked")

        setImgRes(clicked as String)

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

    fun setImgRes(clicked: String){

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}