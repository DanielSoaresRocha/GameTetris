package com.example.gametetrisdaniel.Pecas

import com.example.gametetrisdaniel.Piece

class L(linha:Int,coluna:Int) : Piece(linha, coluna) {


    init {
        pontoB = Ponto(linha-1,coluna)
        pontoC = Ponto(linha-2,coluna)
        pontoD = Ponto(linha,coluna+1)
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

    override fun moveRotate() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}