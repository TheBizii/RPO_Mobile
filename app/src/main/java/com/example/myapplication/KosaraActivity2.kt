package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_kosara2.*
import kotlinx.android.synthetic.main.activity_kosara2.bottomNavigationView
import android.widget.RadioButton

import android.widget.RadioGroup
import androidx.core.widget.addTextChangedListener
import android.text.Editable

import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_kosara.*
import kotlinx.android.synthetic.main.activity_kosara2.buttonNext
import kotlinx.android.synthetic.main.activity_kosara2.drawerLayout
import kotlinx.android.synthetic.main.activity_kosara2.navView

class KosaraActivity2 : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kosara2)

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


        radioGroupPlacilo.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton = group.findViewById<View>(checkedId) as RadioButton
            if (checkedRadioButton.isChecked && checkedRadioButton.text == "Visa/MasterCard") {
                vnosPodatkovKartica.visibility = View.VISIBLE
                vnosPodatkovTRR.visibility = View.INVISIBLE

                buttonNext.isEnabled = false
            }
            else if(checkedRadioButton.isChecked && checkedRadioButton.text == "Ob povzetju"){
                vnosPodatkovKartica.visibility = View.INVISIBLE
                vnosPodatkovTRR.visibility = View.INVISIBLE

                buttonNext.isEnabled = true
            }
            else if(checkedRadioButton.isChecked && checkedRadioButton.text == "Predračun (TRR)"){
                vnosPodatkovKartica.visibility = View.INVISIBLE
                vnosPodatkovTRR.visibility = View.VISIBLE

                buttonNext.isEnabled = false
            }
        }

        cvc.addTextChangedListener{
            buttonNext.isEnabled = preveriKartico()
        }
        datum.addTextChangedListener{
            buttonNext.isEnabled = preveriKartico()
        }
        imeLastnika.addTextChangedListener{
            buttonNext.isEnabled = preveriKartico()
        }
        stevilkaKartice.addTextChangedListener{
            buttonNext.isEnabled = preveriKartico()
        }

        imeRacuna.addTextChangedListener{
            buttonNext.isEnabled = preveriTRR()
        }
        iban.addTextChangedListener{
            buttonNext.isEnabled = preveriTRR()
            if(iban.text.isEmpty()){
                iban.setText("SI56")
            }
        }

        var pos = 0
        val ed = findViewById<View>(R.id.datum) as EditText
        ed.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                if (ed.text.length == 2 && pos != 3) {
                    ed.setText(ed.text.toString() + "/")
                    ed.setSelection(3)
                }
            }

            override fun beforeTextChanged(
                arg0: CharSequence, arg1: Int, arg2: Int,
                arg3: Int
            ) {
                pos = ed.text.length
            }

            override fun afterTextChanged(arg0: Editable) {}
        })


        iban.addTextChangedListener{
            if(iban.text.length % 5 == 4){
                iban.setText(iban.text.toString() + " ")
                iban.setSelection(iban.text.length)
            }
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

    fun btnNextClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun preveriKartico() : Boolean {
        if(imeLastnika.text.isEmpty()) return false
        if(stevilkaKartice.text.length != 16) return false
        if(datum.text.length != 5) {
            val month: String = datum.text.substring(
                0,
                datum.text.toString().length.coerceAtMost(1)
            )
            if(month.toInt() >= 12){
                return false
            }
        }

        return true
    }

    private fun preveriTRR() : Boolean {
        if(imeRacuna.length() == 0) return false
        if(iban.length() != 23) return false

        return true
    }
}