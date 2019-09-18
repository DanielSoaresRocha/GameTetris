package com.example.gametetrisdaniel.Pecas

import com.example.gametetrisdaniel.Piece

class I(linha:Int,coluna:Int) : Piece(linha, coluna){

    var estado : String = "normal"
    init {
        pontoB = Ponto(linha-1,coluna)
        pontoC = Ponto(linha-2,coluna)
        pontoD = Ponto(linha-3,coluna)
    }

    override fun moveRight() {
        pontoA.moveRight()
        pontoB.moveRight()
        pontoC.moveRight()
        pontoD.moveRight()
    }

    override fun moveLeft() {
        pontoA.moveLeft()
        pontoB.moveLeft()
        pontoC.moveLeft()
        pontoD.moveLeft()
    }

    override fun moveDown() {
        pontoA.moveDown()
        pontoB.moveDown()
        pontoC.moveDown()
        pontoD.moveDown()
    }

    override fun moveTop(){
        pontoA.moveTop()
        pontoB.moveTop()
        pontoC.moveTop()
        pontoD.moveTop()
    }

    override fun moveRotate() {

        if(estado.equals("normal")){
            pontoB.linha = pontoA.linha
            pontoB.coluna = pontoA.coluna+1

            pontoC.linha = pontoA.linha
            pontoC.coluna = pontoA.coluna+2

            pontoD.linha = pontoA.linha
            pontoD.coluna = pontoA.coluna+3

            estado = "rotacionado"
        }else{
            pontoB.linha = pontoA.linha-1
            pontoB.coluna = pontoA.coluna

            pontoC.linha = pontoA.linha-2
            pontoC.coluna = pontoA.coluna

            pontoD.linha = pontoA.linha-3
            pontoD.coluna = pontoA.coluna

            estado = "normal"
        }
    }
}