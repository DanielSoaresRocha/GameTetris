package com.example.gametetrisdaniel

import com.example.gametetrisdaniel.Pecas.Ponto

abstract class Piece(var linha:Int,var coluna:Int){

    var pontoA = Ponto(linha,coluna)
    lateinit var pontoB: Ponto
    lateinit var pontoC: Ponto
    lateinit var pontoD: Ponto

    abstract fun moveDown()
    abstract fun moveLeft()
    abstract fun moveRight()
    abstract fun moveRotate(numColum: Int)
    abstract fun moveTop()
    abstract fun getColorPiece():Int
    abstract fun setColorPiece(color :Int)



}