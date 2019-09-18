package com.example.gametetrisdaniel.Pecas


import com.example.gametetrisdaniel.Piece

class T(linha:Int,coluna:Int) : Piece(linha, coluna) {
    var estado = "normal"

    init {
        pontoB = Ponto(linha, coluna-1)
        pontoC = Ponto(linha -1, coluna)
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
        /*
        pontoB.linha += 1
        pontoB.coluna += 1

        pontoC.linha += 2
        pontoC.coluna += 2

        pontoD.linha += 1
        pontoD.coluna -= 1*/

        if (estado.equals("normal")) {
            pontoB.linha = pontoA.linha-1
            pontoB.coluna = pontoA.coluna

            pontoC.linha = pontoA.linha
            pontoC.coluna = pontoA.coluna+1

            pontoD.linha = pontoA.linha+1
            pontoD.coluna = pontoA.coluna

            estado = "rotacionado"
        } else {
            pontoB.linha = pontoA.linha
            pontoB.coluna = pontoA.coluna-1

            pontoC.linha = pontoA.linha - 1
            pontoC.coluna = pontoA.coluna

            pontoD.linha = pontoA.linha
            pontoD.coluna = pontoA.coluna + 1

            estado = "normal"
        }

    }
}