package com.example.gametetrisdaniel.Pecas


class Ponto(var linha:Int, var coluna:Int){

    fun moveDown(){
        linha++
    }

    fun moveLeft(){
        coluna--
    }

    fun moveRight(){
        coluna++
    }



}