package com.example.gametetrisdaniel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        novoJogo.setOnClickListener {
            var i = Intent(this,Jogar::class.java)
            startActivity(i)
        }

        configurarBtn.setOnClickListener {
            var configurar = Intent(this,Configuracao::class.java)
            startActivity(configurar)
        }

    }
}
