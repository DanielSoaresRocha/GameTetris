package com.example.gametetrisdaniel

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_game_over.*


class GameOver : AppCompatActivity() {

    val PREFS = "speed_file"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        listener()
        //pegando da dos da itent
        var params = intent.extras
        var pontuacaoRecebida = params?.getString("pontuacaoAtual") // pontuação vinda da activity
        pontuacaoAtual.text = "${pontuacaoRecebida}"
        ////

        //pegando record armazenado
        val setting = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        var edit = setting.edit()

        var recorde = setting.getInt("recorde", 0)

        recordTextView.text = recorde.toString() //recorde salvo
        ////

        var pontuacaoAtual : Int = Integer.parseInt(pontuacaoRecebida.toString())

        if(pontuacaoAtual > recorde){
            novoRecordTextView.visibility = View.VISIBLE

            edit.putInt("recorde", pontuacaoAtual )
            edit.commit()
        }


    }

    fun listener(){
        novoJogoBtn.setOnClickListener {
            var i = Intent(this,Jogar::class.java)
            startActivity(i)
        }

        sairBtn.setOnClickListener {
            val it = Intent(applicationContext, MainActivity::class.java)
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            it.putExtra("SAIR", true)
            startActivity(it)
        }

    }

}
