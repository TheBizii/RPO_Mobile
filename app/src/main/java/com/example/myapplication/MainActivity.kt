package com.example.myapplication

import MojeNastavitve
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import android.provider.Settings
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
import kotlinx.android.synthetic.main.activity_main.drawerLayout
import kotlinx.android.synthetic.main.activity_main.navView
import kotlinx.android.synthetic.main.fragment_main.*
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONArray
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle

    private val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    private val firstFragment=MainFragment()
    private val secondFragment=SecondFragment()
    private val restavracijaFragment=RestavracijaFragment()
    private val dostaveFragment=DostaveFragment()
    private val kontaktiFragment=KontaktiFragment()
    private val pravilaFragment=PravilaFragment()
    private val vrsteHraneFragment=VrsteHraneFragment()
    private val zgodovinaFragment=ZgodovinaFragment()

    private var clicked = "Empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLastLocation()

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

    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

    fun imageClickRestavracija(view: android.view.View) {
        clicked = resources.getResourceEntryName(view.id)

        Intent(this, RestavracijaActivity::class.java).also {
            it.putExtra("clicked", view.contentDescription)
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

    private fun hideOption(id: Int) {
        val item: MenuItem = navView.menu.findItem(id)
        item.isVisible = false
    }

    private fun showOption(id: Int) {
        val item: MenuItem = navView.menu.findItem(id)
        item.isVisible = true
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        postDataUsingVolley(location.latitude.toString(), location.longitude.toString())
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun postDataUsingVolley(lat: String, lng: String) {
        val url = "https://bolt.printeepro.com/API/nearbyShops"

        val queue = Volley.newRequestQueue(this)

        val req = JSONObject("{\"lat\":\"${lat}\",\"lng\":\"${lng}\"}")

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, req,
            { response ->
                try {
                    val jsonArray: JSONArray = response.getJSONArray("shops")

                    Picasso.with(this).load(jsonArray.getJSONObject(0).getString("image").toString()).into(findViewById<ImageView>(R.id.restavracija1))
                    Picasso.with(this).load(jsonArray.getJSONObject(1).getString("image").toString()).into(findViewById<ImageView>(R.id.restavracija2))
                    Picasso.with(this).load(jsonArray.getJSONObject(2).getString("image").toString()).into(findViewById<ImageView>(R.id.restavracija3))
                    Picasso.with(this).load(jsonArray.getJSONObject(3).getString("image").toString()).into(findViewById<ImageView>(R.id.restavracija4))
                    Picasso.with(this).load(jsonArray.getJSONObject(4).getString("image").toString()).into(findViewById<ImageView>(R.id.restavracija5))
                    Picasso.with(this).load(jsonArray.getJSONObject(5).getString("image").toString()).into(findViewById<ImageView>(R.id.restavracija6))

                    findViewById<ImageView>(R.id.restavracija1).contentDescription = jsonArray.getJSONObject(0).getString("id").toString()
                    findViewById<ImageView>(R.id.restavracija2).contentDescription = jsonArray.getJSONObject(1).getString("id").toString()
                    findViewById<ImageView>(R.id.restavracija3).contentDescription = jsonArray.getJSONObject(2).getString("id").toString()
                    findViewById<ImageView>(R.id.restavracija4).contentDescription = jsonArray.getJSONObject(3).getString("id").toString()
                    findViewById<ImageView>(R.id.restavracija5).contentDescription = jsonArray.getJSONObject(4).getString("id").toString()
                    findViewById<ImageView>(R.id.restavracija6).contentDescription = jsonArray.getJSONObject(5).getString("id").toString()

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