package com.example.myapplication

import android.app.Notification
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_main.*
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment=FirstFragment()
        val secondFragment=SecondFragment()
        val thirdFragment=ThirdFragment()

        setCurrentFragment(firstFragment)



        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miRestavracije -> Toast.makeText(applicationContext, "Restavracije", Toast.LENGTH_SHORT).show()
                R.id.miTrgovine -> Toast.makeText(applicationContext, "Trgovine", Toast.LENGTH_SHORT).show()
                R.id.miZgodovina -> Toast.makeText(applicationContext, "Zgodovina", Toast.LENGTH_SHORT).show()
                R.id.miDostave -> Toast.makeText(applicationContext, "Dostave", Toast.LENGTH_SHORT).show()
                R.id.miPrijava -> Toast.makeText(applicationContext, "Prijava", Toast.LENGTH_SHORT).show()
                R.id.miPravila -> Toast.makeText(applicationContext, "Pravila/Pravice", Toast.LENGTH_SHORT).show()
                R.id.miKontakti -> Toast.makeText(applicationContext, "Kontakti", Toast.LENGTH_SHORT).show()
                R.id.miNastavitve -> Toast.makeText(applicationContext, "Nastavitve", Toast.LENGTH_SHORT).show()
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

    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}