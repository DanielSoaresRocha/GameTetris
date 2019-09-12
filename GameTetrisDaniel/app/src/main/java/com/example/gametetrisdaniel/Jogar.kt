package com.example.gametetrisdaniel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import com.example.gametetrisdaniel.Pecas.Ponto
import kotlinx.android.synthetic.main.activity_jogar.*

class Jogar : AppCompatActivity() {
    val LINHA = 36
    val COLUNA = 26
    var running = true
    var speed : Long = 300

    var pt = Ponto(0,17)

    var board = Array(LINHA) {
        Array(COLUNA){0}
    }

    var tabuleiro = Array(LINHA){
        arrayOfNulls<ImageView>(COLUNA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogar)

        gridboard.rowCount = LINHA
        gridboard.columnCount = COLUNA

        val inflater = LayoutInflater.from(this)

        for (i in 0 until LINHA) {
            for (j in 0 until COLUNA) {
                tabuleiro[i][j] = inflater.inflate(R.layout.inflate_image_view, gridboard, false) as ImageView
                gridboard.addView( tabuleiro[i][j])
            }
        }

        gameRun()
    }
    fun gameRun(){
        Thread{
            while(running){
                Thread.sleep(speed)
                runOnUiThread{
                    //limpa tela
                    for (i in 0 until LINHA) {
                        for (j in 0 until COLUNA) {
                            tabuleiro[i][j]!!.setImageResource(R.drawable.black)
                        }
                    }
                    //move peça atual
                    pt.moveDown()
                    //print peça
                    try {
                        tabuleiro[pt.linha][pt.coluna]!!.setImageResource(R.drawable.white)
                    }catch (e:ArrayIndexOutOfBoundsException ) {
                        //se a peça passou das bordas eu vou parar o jogo
                        running = false
                    }

                }
            }
        }.start()
    }
}
