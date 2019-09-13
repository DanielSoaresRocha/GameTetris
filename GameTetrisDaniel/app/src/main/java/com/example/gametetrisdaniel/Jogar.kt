package com.example.gametetrisdaniel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import com.example.gametetrisdaniel.Pecas.L
import com.example.gametetrisdaniel.Pecas.Ponto
import kotlinx.android.synthetic.main.activity_jogar.*

class Jogar : AppCompatActivity() {
    val LINHA = 36
    val COLUNA = 26
    var running = true
    var speed : Long = 300

    var pt = L(2,17)

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

        direitaBtn.setOnClickListener {
            if(!bateuDireita()){
                pt.moveRight()
            }
        }

        esquerdaBtn.setOnClickListener {
            if(!bateuEsquerda()){
                pt.moveLeft()
            }

        }

        baixoBtn.setOnClickListener {
            pt.moveDown()

        }

        rotateButton.setOnClickListener {
            pt.moveRotate()
        }
    }
    fun gameRun(){
        Thread{
            while(running){
                Thread.sleep(speed)
                runOnUiThread{
                    //limpa tela
                    for (i in 0 until LINHA) {
                        for (j in 0 until COLUNA) {
                            if (board[i][j] == 0){
                                tabuleiro[i][j]!!.setImageResource(R.drawable.black)
                            }
                        }
                    }
                    //move peça atual

                    if(!bateuPeca()){
                        pt.moveDown()
                    }

                    //print peça
                    try {
                        desenharPeca()
                        //Log.i("OK","FEZ")
                    }catch (e:ArrayIndexOutOfBoundsException ) {
                        //se a peça passou das bordas eu vou parar o jogo
                        //running = false
                        Log.i("ERRO","Deu erro"+ e.message)
                        Log.i("INFORMACAO","PONTOA linha = "+ pt.pontoA.linha)
                        Log.i("INFORMACAO","PONTOA linha = "+ pt.pontoA.coluna)

                        Log.i("INFORMACAO","PONTOB linha = "+ pt.pontoB.linha)
                        Log.i("INFORMACAO","PONTOB linha = "+ pt.pontoB.coluna)

                        Log.i("INFORMACAO","PONTOC linha = "+ pt.pontoC.linha)
                        Log.i("INFORMACAO","PONTOC linha = "+ pt.pontoC.coluna)

                        Log.i("INFORMACAO","PONTOD linha = "+ pt.pontoD.linha)
                        Log.i("INFORMACAO","PONTOD linha = "+ pt.pontoD.coluna)

                        bordaOuLateral()
                    }

                }
            }
        }.start()
    }


    fun atualizar(){
        board[pt.pontoA.linha][pt.pontoA.coluna] =1
        board[pt.pontoB.linha][pt.pontoB.coluna] =1
        board[pt.pontoC.linha][pt.pontoC.coluna] =1
        board[pt.pontoD.linha][pt.pontoD.coluna] =1

        desenharPeca()
        novaPeca()
    }

    fun desenharPeca(){
        tabuleiro[pt.pontoA.linha][pt.pontoA.coluna]!!.setImageResource(R.drawable.white)
        tabuleiro[pt.pontoB.linha][pt.pontoB.coluna]!!.setImageResource(R.drawable.white)
        tabuleiro[pt.pontoC.linha][pt.pontoC.coluna]!!.setImageResource(R.drawable.white)
        tabuleiro[pt.pontoD.linha][pt.pontoD.coluna]!!.setImageResource(R.drawable.white)

    }

    fun novaPeca(){
        pt = L(2,17)
    }

    fun bateuPeca():Boolean{
            try {
            if((board[pt.pontoA.linha+1][pt.pontoA.coluna] == 1) || (board[pt.pontoB.linha+1][pt.pontoB.coluna] == 1)//bateu no final da peca
                || (board[pt.pontoC.linha+1][pt.pontoC.coluna] == 1) || (board[pt.pontoD.linha+1][pt.pontoD.coluna] == 1)){
                //bateuFinal()
                atualizar()
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Log.i("ERRO","Erro não importante")
        }
        return false
    }

    //bateu no final ou na lateral?
    fun bordaOuLateral(){
        if(pt.pontoA.linha >= LINHA || pt.pontoB.linha >= LINHA || pt.pontoC.linha >= LINHA ||
            pt.pontoD.linha >= LINHA){
            bateuFinal()
        }else{
            bateuLateral()
        }
    }

    fun bateuFinal(){
        pt.moveTop()
        atualizar()
    }

    //ver se bateu na direita ou esquerda
    fun bateuLateral(){
        if(pt.pontoA.coluna >= COLUNA || pt.pontoB.coluna >= COLUNA || pt.pontoC.coluna >= COLUNA ||
            pt.pontoD.coluna >= COLUNA){
            pt.moveLeft()
        }else{
            pt.moveRight()
        }
    }

    fun bateuDireita():Boolean{
        try {
            if((board[pt.pontoA.linha][pt.pontoA.coluna+1] == 1) || (board[pt.pontoB.linha][pt.pontoB.coluna+1] == 1)
                || (board[pt.pontoC.linha][pt.pontoC.coluna+1] == 1) || (board[pt.pontoD.linha][pt.pontoD.coluna+1] == 1)) {//bateu no lado direito
                return true
                }
        }catch (e:ArrayIndexOutOfBoundsException){
            Log.i("ERRO","Erro não importante")
        }
        return false
    }

    fun bateuEsquerda():Boolean{
        try {
            if((board[pt.pontoA.linha][pt.pontoA.coluna-1] == 1) || (board[pt.pontoB.linha][pt.pontoB.coluna-1] == 1)
                || (board[pt.pontoC.linha][pt.pontoC.coluna-1] == 1) || (board[pt.pontoD.linha][pt.pontoD.coluna-1] == 1)) {//bateu no lado esquerdo
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Log.i("ERRO","Erro não importante")
        }
        return false
    }
}
