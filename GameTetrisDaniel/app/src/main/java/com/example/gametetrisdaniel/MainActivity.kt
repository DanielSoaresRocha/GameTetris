package com.example.gametetrisdaniel

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val PREFS = "speed_file"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listener()

        var settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        var continuar = settings.getBoolean("continuar",false)

        if (continuar){
            continuarBtn.visibility = View.VISIBLE
        }else{
            continuarBtn.visibility = View.GONE
        }

    }

    fun listener(){

        continuarBtn.setOnClickListener {
            var telaJogar = Intent(this, Jogar::class.java)
            var b = Bundle()

            b.putBoolean("continuar", true)
            telaJogar.putExtras(b)

            startActivity(telaJogar)
        }

        novoJogo.setOnClickListener {
            var settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            var edit = settings.edit()
            edit.putBoolean("continuar",false)
            edit.commit()

            var i = Intent(this,Jogar::class.java)
            startActivity(i)

        }

        configurarBtn.setOnClickListener {
            var configurar = Intent(this,Configuracao::class.java)
            startActivity(configurar)
        }
    }

    override fun onResume() {
        if (intent.getBooleanExtra("SAIR", false)) {
            finish()
        }
        super.onResume()
    }
}
