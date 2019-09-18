package com.example.gametetrisdaniel.Pecas


import com.example.gametetrisdaniel.Piece

class Quadrado(linha:Int,coluna:Int) : Piece(linha, coluna) {
    var estado = "normal"

    init {
        pontoB = Ponto(linha-1, coluna)
        pontoC = Ponto(linha -1, coluna+1)
        pontoD = Ponto(linha, coluna + 1)
    }

    override fun moveDown() {
        pontoA.moveDown()
        pontoB.moveDown()
        pontoC.moveDown()
        pontoD.moveDown()

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

    override fun moveTop() {
        pontoA.moveTop()
        pontoB.moveTop()
        pontoC.moveTop()
        pontoD.moveTop()
    }

    override fun moveRotate() {

    }
}