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
import android.R.attr.start
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

//import android.R



class Jogar : AppCompatActivity() {

    val LINHA = 20//36
    val COLUNA = 20//27
    var running = true
    var speed : Long = 200

    val PREFS = "speed_file"

    var pt : Piece = I(3,COLUNA/2)
    var random = Random
    var novaPeca = 1

    var tabuleiro = Array(LINHA){
        arrayOfNulls<ImageView>(COLUNA)
    }

    var mp :MediaPlayer? = null

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
                gridboard.addView(tabuleiro[i][j])
            }
        }

        verificaContinuacao()
        tocarMusica()
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
                var telaInicial = Intent(this, MainActivity::class.java)
                startActivity(telaInicial)
                onPause()
                onStop()
                finish()
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
                            if(vm.board[i][j] == 0){
                                tabuleiro[i][j]!!.setImageResource(R.drawable.black)
                            }else{
                                tabuleiro[i][j]!!.setImageResource(vm.board[i][j])
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
        vm.board[pt.pontoA.linha][pt.pontoA.coluna] = pt.getColorPiece()
        vm.board[pt.pontoB.linha][pt.pontoB.coluna] = pt.getColorPiece()
        vm.board[pt.pontoC.linha][pt.pontoC.coluna] = pt.getColorPiece()
        vm.board[pt.pontoD.linha][pt.pontoD.coluna] = pt.getColorPiece()

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
                if(vm.board[i][j] != 0){
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
                if(vm.board[linhaa][coluna] != 0){
                    vm.board[linhaa+1][coluna] = vm.board[linhaa][coluna]
                    vm.board[linhaa][coluna] = 0
                    //vm.board[linhaa][coluna] = 0
                    //vm.board[linhaa+1][coluna] = 1
                }
            }
        }
    }

    fun novaPeca(){
        if(running){ //Isto não permite que ele faça isto mais que uma vez
            verificaDerrota()
        }

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

        mudaProximaPeca()


    }

    fun mudaProximaPeca(){
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
            if((vm.board[pt.pontoA.linha+1][pt.pontoA.coluna] != 0) || (vm.board[pt.pontoB.linha+1][pt.pontoB.coluna] != 0)//bateu no final da peca
                || (vm.board[pt.pontoC.linha+1][pt.pontoC.coluna] != 0) || (vm.board[pt.pontoD.linha+1][pt.pontoD.coluna] != 0)){
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
            if((vm.board[pt.pontoA.linha][pt.pontoA.coluna+1] != 0) || (vm.board[pt.pontoB.linha][pt.pontoB.coluna+1] != 0)
                || (vm.board[pt.pontoC.linha][pt.pontoC.coluna+1] != 0) || (vm.board[pt.pontoD.linha][pt.pontoD.coluna+1] != 0)) {//bateu no lado direito
                return true
                }
        }catch (e:ArrayIndexOutOfBoundsException){
            //Log.i("ERRO","Erro não importante")
        }
        return false
    }

    fun bateuEsquerdaPeca():Boolean{
        try {
            if((vm.board[pt.pontoA.linha][pt.pontoA.coluna-1] != 0) || (vm.board[pt.pontoB.linha][pt.pontoB.coluna-1] != 0)
                || (vm.board[pt.pontoC.linha][pt.pontoC.coluna-1] != 0) || (vm.board[pt.pontoD.linha][pt.pontoD.coluna-1] != 0)) {//bateu no lado esquerdo
                return true
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            //Log.i("ERRO","Erro não importante")
        }
        return false
    }

    fun colisionRotatePiece(){
        if(vm.board[pt.pontoA.linha][pt.pontoA.coluna] != 0 || vm.board[pt.pontoB.linha][pt.pontoB.coluna] != 0
            || vm.board[pt.pontoC.linha][pt.pontoC.coluna] != 0 || vm.board[pt.pontoD.linha][pt.pontoD.coluna] != 0){
            pt.moveRotate(COLUNA)
        }
    }

    fun pegarVelocidade(){
        val settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        speed = settings.getLong("speed",200)
    }

    fun verificaDerrota(){
        for(coluna in 0 until COLUNA) {
                if (vm.board[3][coluna] != 0) {
                    running = false

                    var i = Intent(this, GameOver::class.java)
                    var b = Bundle()

                    b.putString("pontuacaoAtual", pontuacao.text.toString())
                    i.putExtras(b)

                    startActivity(i)
                    //onPause()
                    //onStop()
                    finish()
                    break
            }
        }
    }

    override fun onPause() {
        super.onPause()
        running = false

        salvarEstado()
    }

    override fun onRestart() {
        super.onRestart()
        running = true
        gameRun()

        mp?.stop()
        mp = null
        tocarMusica()

        restaurarEstado()
    }

    override fun onStop() {
        super.onStop()

        val setting = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        var edit = setting.edit()

        edit.putBoolean("continuar",true)
        edit.commit()

    }

    fun salvarEstado(){
        val setting = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        var edit = setting.edit()

        for (i in 0..LINHA-1) {
            for(j in 0..COLUNA-1){
                edit.putInt("L"+i+"C"+j,vm.board[i][j])
                Log.i("ESTADO","L"+i+"C"+j +" numPosicao:" +vm.board[i][j])
            }
        }

        edit.putInt("ponto_A_linha", pt.pontoA.linha)
        edit.putInt("ponto_A_coluna", pt.pontoA.coluna)

        edit.putInt("ponto_B_linha", pt.pontoB.linha)
        edit.putInt("ponto_B_coluna", pt.pontoB.coluna)

        edit.putInt("ponto_C_linha", pt.pontoC.linha)
        edit.putInt("ponto_C_coluna", pt.pontoC.coluna)

        edit.putInt("ponto_D_linha", pt.pontoD.linha)
        edit.putInt("ponto_D_coluna", pt.pontoD.coluna)

        edit.putInt("pieceColor",pt.getColorPiece())
        Log.i("RESTAURACAO", "cor = " + pt.getColorPiece())

        edit.putString("pontuacao", pontuacao.text.toString())
        edit.putInt("novaPeca", novaPeca)

        edit.commit()

    }

    fun restaurarEstado(){
        val setting = getSharedPreferences(PREFS, Context.MODE_PRIVATE)

        for (i in 0..LINHA-1) {
            for(j in 0..COLUNA-1){
                vm.board[i][j] = setting.getInt("L"+i+"C"+j,0)
                //Log.i("ESTADO","L"+i+"C"+j +" numPosicao:" +vm.board[i][j])
            }
        }

        pt.pontoA.linha = setting.getInt("ponto_A_linha",0)
        pt.pontoA.coluna = setting.getInt("ponto_A_coluna",0)
        pt.pontoB.linha = setting.getInt("ponto_B_linha",0)
        pt.pontoB.coluna = setting.getInt("ponto_B_coluna",0)
        pt.pontoC.linha = setting.getInt("ponto_C_linha",0)
        pt.pontoC.coluna = setting.getInt("ponto_C_coluna",0)
        pt.pontoD.linha = setting.getInt("ponto_D_linha",0)
        pt.pontoD.coluna = setting.getInt("ponto_D_coluna",0)

        pontuacao.text = setting.getString("pontuacao","")
        novaPeca = setting.getInt("novaPeca", 0)
        mudaProximaPeca()

        pt.setColorPiece(setting.getInt("pieceColor",R.color.branco))
        desenharPeca()

    }

    fun verificaContinuacao(){
        //pegando da dos da itent
        var params = intent.extras

        var continuar = params?.getBoolean("continuar",false) // pontuação vinda da activity
        if(continuar is Boolean){
            restaurarEstado()
        }

    }

    fun tocarMusica(){
        mp = MediaPlayer.create(this,R.raw.musica_fundo)
        mp?.setOnCompletionListener { mp ->
            var mp = mp
            mp!!.stop()
            mp!!.release()
            mp = null
        }
        mp?.start()
    }

}
