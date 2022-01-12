package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_kosara.*
import kotlinx.android.synthetic.main.activity_kosara.bottomNavigationView
import Kosarica
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.AdapterView.OnItemClickListener


class KosaraActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kosara)

        val list = Kosarica.arrayList

        Kosarica.skupaj = 0.0
        for (item in Kosarica.arrayList){
            Kosarica.skupaj += (Kosarica.prices[item]?.times(Kosarica.duplicates[item]!!))!!
        }
        skupajCena.text = "Skupaj: " + String.format("%.2f", Kosarica.skupaj) + "€"

        listKosarica.adapter = MyAdapter(this, list, Kosarica.duplicates, Kosarica.prices)

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

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listKosarica.onItemClickListener = OnItemClickListener { _, _, position, _ ->

            if(Kosarica.duplicates.containsKey(list[position])){
                val vrednost = Kosarica.duplicates[list[position]]
                if(vrednost!! > 1){
                    Kosarica.duplicates[list[position]] = vrednost - 1
                }
                else{
                    Kosarica.arrayList.remove(list[position])
                    finish()
                    startActivity(intent)
                }
            }
            else{
                Kosarica.arrayList.remove(list[position])
            }
            finish()
            startActivity(intent)
        }

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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
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

class MyAdapter(
    private val context: Context,
    private val arrayList: java.util.ArrayList<String>,
    private val duplicates: HashMap<String, Int>,
    private val prices: HashMap<String, Double>
) : BaseAdapter() {
    private lateinit var izdelek: TextView
    private lateinit var kolicina: TextView
    private lateinit var cena: TextView
    override fun getCount(): Int {
        return arrayList.size
    }
    override fun getItem(position: Int): Any {
        return position
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        convertView = LayoutInflater.from(context).inflate(R.layout.izdelek_v_kosari, parent, false)
        izdelek = convertView.findViewById(R.id.izdelek)
        kolicina = convertView.findViewById(R.id.kolicina)
        cena = convertView.findViewById(R.id.cena)
        izdelek.text = arrayList[position]
        if( duplicates[arrayList[position]].toString() == "null"){
            kolicina.text = "1";
        }
        else kolicina.text = duplicates[arrayList[position]].toString()
        var cenaOut = prices[arrayList[position]]?.times(duplicates[arrayList[position]]!!)
        cena.text = String.format("%.2f", cenaOut) + "€"
        return convertView
    }
}