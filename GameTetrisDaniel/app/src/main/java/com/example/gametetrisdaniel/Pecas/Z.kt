package com.example.gametetrisdaniel.Pecas


import com.example.gametetrisdaniel.Piece

class Z(linha:Int,coluna:Int) : Piece(linha, coluna) {

    var estado : String = "normal"

    init {
        pontoB = Ponto(linha,coluna-1)
        pontoC = Ponto(linha-1,coluna)
        pontoD = Ponto(linha-1,coluna+1)
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

    fun moveTop(){
        pontoA.moveTop()
        pontoB.moveTop()
        pontoC.moveTop()
        pontoD.moveTop()
    }

    override fun moveRotate() {

        if(estado.equals("normal")){
            pontoB.linha = pontoA.linha-1
            pontoB.coluna = pontoA.coluna

            pontoC.linha = pontoA.linha
            pontoC.coluna = pontoA.coluna+1

            pontoD.linha = pontoA.linha+1
            pontoD.coluna = pontoA.coluna+1

            estado = "rotacionado"
        }else{
            pontoB.linha = pontoA.linha
            pontoB.coluna = pontoA.coluna-1

            pontoC.linha = pontoA.linha-1
            pontoC.coluna = pontoA.coluna

            pontoD.linha = pontoA.linha-1
            pontoD.coluna = pontoA.coluna+1

            estado = "normal"
        }
    }

}