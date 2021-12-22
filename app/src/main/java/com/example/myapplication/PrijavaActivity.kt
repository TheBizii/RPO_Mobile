package com.example.myapplication

import MojeNastavitve
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_prijava.bottomNavigationView
import kotlinx.android.synthetic.main.activity_prijava.drawerLayout
import kotlinx.android.synthetic.main.activity_prijava.navView
import kotlinx.android.synthetic.main.activity_prijava.*

import org.json.JSONException

import org.json.JSONObject

import com.android.volley.toolbox.Volley

import com.android.volley.toolbox.JsonObjectRequest

import com.android.volley.*
import java.security.MessageDigest
import java.util.*


class PrijavaActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prijava)

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

    private fun checkTheme() {
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

    fun bntClickPrijava(view: android.view.View) {
        //val emailPattern = "[a-zA-Z0-9._-]+@([a-z]+\\.)+[a-z]+".toRegex()

        if(uporabniskoIme.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Vpisi uporabnisko ime",Toast.LENGTH_SHORT).show();
        }
        else {
            postDataUsingVolley(uporabniskoIme.getText().toString(), geslo.getText().toString());
        }
    }

    private fun postDataUsingVolley(uname: String, password: String) {
        val url = "https://bolt.printeepro.com/API/login"
        loadingPB.visibility = View.VISIBLE

        val queue = Volley.newRequestQueue(this)

        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        val hash = digest.fold("", { str, it -> str + "%02x".format(it) })

        val req = JSONObject("{\"username\":\"${uname}\",\"password\":\"${hash.uppercase(Locale.getDefault())}\"}")

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, req,
            { response ->
                loadingPB.visibility = View.GONE
                uporabniskoIme.setText("")
                geslo.setText("")

                try {
                    if(response.getString("message") == "OK")

                    odgovor.text = "Prijavljen : $uname"

                    var sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                    val myEdit: SharedPreferences.Editor = sharedPreferences.edit()

                    myEdit.putString("username", uname)
                    myEdit.apply()
                    Toast.makeText(this, uname, Toast.LENGTH_SHORT).show()

                } catch (e: JSONException) {
                    val respObj = response
                    Toast.makeText(this, respObj.getString("error"), Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
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

    private fun hideOption(id: Int) {
        val item: MenuItem = navView.menu.findItem(id)
        item.isVisible = false
    }

    private fun showOption(id: Int) {
        val item: MenuItem = navView.menu.findItem(id)
        item.isVisible = true
    }
}