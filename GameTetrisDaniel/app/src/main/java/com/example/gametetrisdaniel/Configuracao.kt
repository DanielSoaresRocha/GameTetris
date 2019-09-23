package com.example.gametetrisdaniel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_configuracao.*

class Configuracao : AppCompatActivity() {

    val PREFS = "speed_file"
    var speed : Long = 300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracao)


        salvarBtn.setOnClickListener {
            salvar()
            finish()

        }

        facilBtn.setOnClickListener {
            speed = 400
        }

        medioBtn.setOnClickListener {
            speed = 200
        }

        dificilBtn.setOnClickListener {
            speed = 100
        }
    }

    fun salvar(){

        val settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        var editor = settings.edit()

        editor.putLong("speed", speed)
        editor.commit()
    }
}
