package com.example.gametetrisdaniel

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import com.example.gametetrisdaniel.Pecas.*
import kotlinx.android.synthetic.main.activity_jogar.*
import java.util.*
import kotlin.random.Random
import com.example.gametetrisdaniel.R

class Jogar : AppCompatActivity() {

    val LINHA = 30//36
    val COLUNA = 27//27
    var running = true
    var speed : Long = 200

    val PREFS = "speed_file"

    var pt : Piece = I(3,COLUNA/2)
    var random = Random
    var novaPeca = 1

    var tabuleiro = Array(LINHA){
        arrayOfNulls<ImageView>(COLUNA)
    }

    val vm: BoardViewModel by lazy {
        ViewModelProviders.of(this)[BoardViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide() //tirar barra de título
        setContentView(R.layout.activity_jogar)

        pegarVelocidade()

        gridboard.rowCount = LINHA
        gridboard.columnCount = COLUNA

        val inflater = LayoutInflater.from(this)

        for (i in 0 until LINHA) {
            for (j in 0 until COLUNA) {
                tabuleiro[i][j] = inflater.inflate(R.layout.inflate_image_view, gridboard, false) as ImageView
                gridboard.addView( tabuleiro[i][j])
            }
        }

        tocarMusica()
        novaPeca()
        gameRun()

        //verificaPontos()

        direitaBtn.setOnClickListener {
            if(!bateuDireitaBorda()){
                if(!bateuDireitaPeca()){
                    pt.moveRight()
                }
            }
        }

        esquerdaBtn.setOnClickListener {
            if(!bateuEsquerdaBorda()){
                if(!bateuEsquerdaPeca()){
                    pt.moveLeft()
                }
            }

        }

        baixoBtn.setOnClickListener {
            moverBaixo()
        }

        rotateButton.setOnClickListener {
            pt.moveRotate(COLUNA)
            colisionRotatePiece()
        }

        pauseButton.setOnClickListener {
            if(running){
                running = false
            }else{
                running = true
                gameRun()
            }

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
                            if (vm.board[i][j] == 0){
                                tabuleiro[i][j]!!.setImageResource(R.drawable.black)
                            }
                        }
                    }
                    //move peça atual

                    moverBaixo()

                    //print peça
                    try {
                        desenharPeca()
                        //Log.i("OK","FEZ")
                    }catch (e:ArrayIndexOutOfBoundsException ) {
                        //se a peça passou das bordas eu vou parar o jogo
                        //running = false
                        /*Log.i("ERRO","Deu erro"+ e.message)
                        Log.i("INFORMACAO","PONTOA linha = "+ pt.pontoA.linha)
                        Log.i("INFORMACAO","PONTOA linha = "+ pt.pontoA.coluna)

                        Log.i("INFORMACAO","PONTOB linha = "+ pt.pontoB.linha)
                        Log.i("INFORMACAO","PONTOB linha = "+ pt.pontoB.coluna)

                        Log.i("INFORMACAO","PONTOC linha = "+ pt.pontoC.linha)
                        Log.i("INFORMACAO","PONTOC linha = "+ pt.pontoC.coluna)

                        Log.i("INFORMACAO","PONTOD linha = "+ pt.pontoD.linha)
                        Log.i("INFORMACAO","PONTOD linha = "+ pt.pontoD.coluna)*/

                        //bateuLateral()
                    }

                }
            }
        }.start()
    }


    fun moverBaixo(){
        if(!bateuFinal()){
            if(!bateuPeca()){
                pt.moveDown()
            }
        }else{
            atualizar()
        }

    }

    fun atualizar(){
        vm.board[pt.pontoA.linha][pt.pontoA.coluna] =1
        vm.board[pt.pontoB.linha][pt.pontoB.coluna] =1
        vm.board[pt.pontoC.linha][pt.pontoC.coluna] =1
        vm.board[pt.pontoD.linha][pt.pontoD.coluna] =1

        verificaPontos()
        desenharPeca()
        novaPeca()
    }

    fun desenharPeca(){
        tabuleiro[pt.pontoA.linha][pt.pontoA.coluna]!!.setImageResource(pt.getColorPiece())
        tabuleiro[pt.pontoB.linha][pt.pontoB.coluna]!!.setImageResource(pt.getColorPiece())
        tabuleiro[pt.pontoC.linha][pt.pontoC.coluna]!!.setImageResource(pt.getColorPiece())
        tabuleiro[pt.pontoD.linha][pt.pontoD.coluna]!!.setImageResource(pt.getColorPiece())

    }

    fun verificaPontos(){
        for (i in 0..LINHA-1) {
            var pont = 0
            for(j in 0..COLUNA-1){
                if(vm.board[i][j] == 1){
                    pont++
                }else{
                    break
                }

                if(pont == COLUNA){
                    Log.i("ACERTOU","ACEEERTOUUU MISERAVI")
                    var pontuacaoAtual : Int = Integer.parseInt(pontuacao.text.toString())
                    pontuacaoAtual += 100
                    pontuacao.setText(Integer.toString(pontuacaoAtual))

                    acertouTabuleiro(i)
                }
            }
        }
    }

    fun acertouTabuleiro(linha : Int){
        for(coluna in 0..COLUNA-1){
            vm.board[linha][coluna] = 0
        }

        for(linhaa in linha-1 downTo 0){
            for(coluna in COLUNA-1 downTo 0){
                println("Linha = "+ linhaa+ "coluna = "+ coluna)
                if(vm.board[linhaa][coluna] == 1){
                    vm.board[linhaa][coluna] = 0
                    vm.board[linhaa+1][coluna] = 1
                }
            }
        }
    }

    fun novaPeca(){
        verificaDerrota()

        var peca = novaPeca

        if(peca == 0){
            pt = L(3,COLUNA/2)
        }else if(peca == 1){
            pt = I(3,COLUNA/2)
        }else if(peca == 2){
            pt = Z(3,COLUNA/2)
        }else if(peca == 4){
            pt = Quadrado(3,COLUNA/2)
        }else{
            pt = T(3,COLUNA/2)
        }

        novaPeca = random.nextInt(5)

        if(novaPeca == 0){
            proximaImageView.setImageResource(R.drawable.l_peca)
        }else if(novaPeca == 1){
            proximaImageView.setImageResource(R.drawable.i_peca)
        }else if(novaPeca == 2){
            proximaImageView.setImageResource(R.drawable.z_peca)
        }else if(novaPeca == 4){
            proximaImageView.setImageResource(R.drawable.quadrado_peca)
        }else{
            proximaImageView.setImageResource(R.drawable.t_peca)
        }


    }

    fun bateuPeca():Boolean{
            try {
            if((vm.board[pt.pontoA.linha+1][pt.pontoA.coluna] == 1) || (vm.board[pt.pontoB.linha+1][pt.pontoB.coluna] == 1)//bateu no final da peca
                || (vm.board[pt.pontoC.linha+1][pt.pontoC.coluna] == 1) || (vm.board[pt.pontoD.linha+1][pt.pontoD.coluna] == 1)){
                //bateuFinal()
                atualizar()
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            //Log.i("ERRO","Erro não importante")
        }
        return false
    }


    fun bateuFinal() : Boolean{
        if(pt.pontoA.linha+1 >= LINHA || pt.pontoB.linha+1 >= LINHA || pt.pontoC.linha+1 >= LINHA ||
            pt.pontoD.linha+1 >= LINHA){
            //pt.moveTop()
            return true
        }
        return false
    }


    fun bateuDireitaBorda() : Boolean{
        if(pt.pontoA.coluna+1 >= COLUNA || pt.pontoB.coluna+1 >= COLUNA || pt.pontoC.coluna+1 >= COLUNA ||
                pt.pontoD.coluna+1 >= COLUNA){
            return true
        }
        return false
    }

    fun bateuEsquerdaBorda(): Boolean{
        if(pt.pontoA.coluna-1 < 0 || pt.pontoB.coluna-1 < 0 || pt.pontoC.coluna-1 < 0 ||
            pt.pontoD.coluna-1 < 0){
            return true
        }
        return false
    }

    fun bateuDireitaPeca():Boolean{
        try {
            if((vm.board[pt.pontoA.linha][pt.pontoA.coluna+1] == 1) || (vm.board[pt.pontoB.linha][pt.pontoB.coluna+1] == 1)
                || (vm.board[pt.pontoC.linha][pt.pontoC.coluna+1] == 1) || (vm.board[pt.pontoD.linha][pt.pontoD.coluna+1] == 1)) {//bateu no lado direito
                return true
                }
        }catch (e:ArrayIndexOutOfBoundsException){
            //Log.i("ERRO","Erro não importante")
        }
        return false
    }

    fun bateuEsquerdaPeca():Boolean{
        try {
            if((vm.board[pt.pontoA.linha][pt.pontoA.coluna-1] == 1) || (vm.board[pt.pontoB.linha][pt.pontoB.coluna-1] == 1)
                || (vm.board[pt.pontoC.linha][pt.pontoC.coluna-1] == 1) || (vm.board[pt.pontoD.linha][pt.pontoD.coluna-1] == 1)) {//bateu no lado esquerdo
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            //Log.i("ERRO","Erro não importante")
        }
        return false
    }

    fun colisionRotatePiece(){
        if(vm.board[pt.pontoA.linha][pt.pontoA.coluna] == 1 || vm.board[pt.pontoB.linha][pt.pontoB.coluna] == 1
            || vm.board[pt.pontoC.linha][pt.pontoC.coluna] == 1 || vm.board[pt.pontoD.linha][pt.pontoD.coluna] == 1){
            pt.moveRotate(COLUNA)
        }
    }

    fun pegarVelocidade(){
        val settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        speed = settings.getLong("speed",200)
    }

    fun verificaDerrota(){
        for(coluna in 0 until COLUNA){
            if(vm.board[3][coluna] == 1){
                running = false

                var i = Intent(this,GameOver::class.java)
                var b = Bundle()

                b.putString("pontuacaoAtual", pontuacao.text.toString())
                i.putExtras(b)

                startActivity(i)
                finish()
                break

            }
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
    }

    override fun onRestart() {
        super.onRestart()
        running = true
        gameRun()
    }

    fun tocarMusica(){
        //val mp : MediaPlayer = MediaPlayer.create(this,R.raw.musicafundo)
        //mp.start()

    }


}
