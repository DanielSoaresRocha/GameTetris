package com.example.gametetrisdaniel.Pecas

import androidx.lifecycle.ViewModel

class BoardViewModel : ViewModel() {
    val LINHA = 20
    val COLUNA = 20

    val PREFS = "speed_file"

    var board = Array(LINHA) {
        Array(COLUNA) { 0 }
    }



}