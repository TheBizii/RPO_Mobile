package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_restavracija.*
import kotlinx.android.synthetic.main.activity_restavracija.bottomNavigationView
import Kosarica
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class RestavracijaActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle

    var clicked = "Empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restavracija)

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

        navView.setNavigationItemSelectedListener {
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

        Meniji.setOnClickListener{

        }
    }

    private fun setImgRes(clicked: String){

        postDataUsingVolley(clicked)

        /*when(clicked){
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
        }*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun imageClickVrsteHrane(view: View) {
        clicked = resources.getResourceEntryName(view.id)

        Intent(this, VrsteHraneActivity::class.java).also {
            it.putExtra("clicked", clicked)
            startActivity(it)
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

    fun btnClickMeni(view: View) {
        when(view.tag){
            "meni1" -> {
                Kosarica.arrayList.forEach { str ->
                    if (str == "Meni 1") {
                        val i = Kosarica.duplicates["Meni 1"]
                        if (i != null) {
                            Kosarica.duplicates["Meni 1"] = i + 1
                        }
                        Toast.makeText(this, "Meni 1 dodan v košarico", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                Kosarica.arrayList.add("Meni 1")
                Kosarica.prices["Meni 1"] = 1.50
                Kosarica.duplicates["Meni 1"] = 1
                Toast.makeText(this, "Meni 1 dodan v košarico", Toast.LENGTH_SHORT).show()
            }
            "meni2" -> {
                Kosarica.arrayList.forEach { str ->
                    if (str == "Meni 2") {
                        val i = Kosarica.duplicates["Meni 2"]
                        if (i != null) {
                            Kosarica.duplicates["Meni 2"] = i + 1
                        }
                        Toast.makeText(this, "Meni 2 dodan v košarico", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                Kosarica.arrayList.add("Meni 2")
                Kosarica.prices["Meni 2"] = 2.20
                Kosarica.duplicates["Meni 2"] = 1
                Toast.makeText(this, "Meni 2 dodan v košarico", Toast.LENGTH_SHORT).show()
            }
            "meni3" -> {
                Kosarica.arrayList.forEach { str ->
                    if (str == "Meni 3") {
                        val i = Kosarica.duplicates["Meni 3"]
                        if (i != null) {
                            Kosarica.duplicates["Meni 3"] = i + 1
                        }
                        Toast.makeText(this, "Meni 3 dodan v košarico", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                Kosarica.arrayList.add("Meni 3")
                Kosarica.prices["Meni 3"] = 1.20
                Kosarica.duplicates["Meni 3"] = 1
                Toast.makeText(this, "Meni 3 dodan v košarico", Toast.LENGTH_SHORT).show()
            }
            "meni4" -> {
                Kosarica.arrayList.forEach { str ->
                    if (str == "Meni 4") {
                        val i = Kosarica.duplicates["Meni 4"]
                        if (i != null) {
                            Kosarica.duplicates["Meni 4"] = i + 1
                        }
                        Toast.makeText(this, "Meni 4 dodan v košarico", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                Kosarica.arrayList.add("Meni 4")
                Kosarica.prices["Meni 4"] = 3.66
                Kosarica.duplicates["Meni 4"] = 1
                Toast.makeText(this, "Meni 4 dodan v košarico", Toast.LENGTH_SHORT).show()
            }
            "meni5" -> {
                Kosarica.arrayList.forEach { str ->
                    if (str == "Meni 5") {
                        val i = Kosarica.duplicates["Meni 5"]
                        if (i != null) {
                            Kosarica.duplicates["Meni 5"] = i + 1
                        }
                        Toast.makeText(this, "Meni 5 dodan v košarico", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                Kosarica.arrayList.add("Meni 5")
                Kosarica.prices["Meni 5"] = 4.20
                Kosarica.duplicates["Meni 5"] = 1
                Toast.makeText(this, "Meni 5 dodan v košarico", Toast.LENGTH_SHORT).show()
            }
            "meni6" -> {
                Kosarica.arrayList.forEach { str ->
                    if (str == "Meni 6") {
                        val i = Kosarica.duplicates["Meni 6"]
                        if (i != null) {
                            Kosarica.duplicates["Meni 6"] = i + 1
                        }
                        Toast.makeText(this, "Meni 6 dodan v košarico", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                Kosarica.arrayList.add("Meni 6")
                Kosarica.prices["Meni 6"] = 0.55
                Kosarica.duplicates["Meni 6"] = 1
                Toast.makeText(this, "Meni 6 dodan v košarico", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun postDataUsingVolley(id: String) {
        val url = "https://bolt.printeepro.com/API/shop"

        val queue = Volley.newRequestQueue(this)

        val req = JSONObject("{\"id\":\"${id}\"}")

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, req,
            { response ->
                try {
                    val jsonObject: JSONObject = response.getJSONObject("message")
                    imeRestavracijeText.text = jsonObject.getString("title")

                    val jsonObjectPartner: JSONObject = jsonObject.getJSONObject("partner")
                    Picasso.with(this).load(jsonObjectPartner.getString("image").toString()).into(findViewById<ImageView>(R.id.imgRestavracija))

                    val jsonObjectAddress: JSONObject = jsonObject.getJSONObject("address")
                    txtNaslov.text = jsonObjectAddress.getString("address")



                } catch (e: JSONException) {
                    Toast.makeText(this, response.getString("error"), Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(
                    this,
                    "Fail to get response = $error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
}