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
import kotlinx.android.synthetic.main.activity_prijava.navView2
import kotlinx.android.synthetic.main.activity_prijava.*
import com.android.volley.VolleyError

import org.json.JSONException

import org.json.JSONObject

import com.android.volley.toolbox.StringRequest

import com.android.volley.toolbox.Volley

import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText


class PrijavaActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prijava)

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
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

        if(eposta.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"enter email address",Toast.LENGTH_SHORT).show();
        }
        else {
            if (!eposta.getText().toString().trim().matches(emailPattern)) {
                Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show()
            }
            else{
                postDataUsingVolley(eposta.getText().toString(), geslo.getText().toString());

                //TODO - to prestavit v funkcijo da se prijava zgodi samo ob pravilnem vnosu

                var sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                val myEdit: SharedPreferences.Editor = sharedPreferences.edit()

                myEdit.putString("eposta", eposta.text.toString())
                myEdit.apply()
            }
        }
    }

    private fun postDataUsingVolley(email: String, password: String) {
        val url = "https://bolt.printeepro.com/API/login"
        loadingPB.visibility = View.VISIBLE

        val queue = Volley.newRequestQueue(this)

        val request: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                loadingPB.visibility = View.GONE
                eposta.setText("")
                geslo.setText("")

                try {
                    val respObj = JSONObject(response)

                    val email = respObj.getString("email")
                    val password = respObj.getString("password")

                    odgovor.setText("Email : $email\nPassword : $password")

                } catch (e: JSONException) {
                    val respObj = JSONObject(response)
                    Toast.makeText(this, respObj.getString("error"), Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> // method to handle errors.
                Toast.makeText(
                    this,
                    "Fail to get response = $error",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()

                params["email"] = email
                params["password"] = password

                return params
            }
        }
        // below line is to make
        // a json object request.
        queue.add(request)
    }
}