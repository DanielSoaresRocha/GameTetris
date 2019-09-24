package com.example.gametetrisdaniel.Pecas

import androidx.lifecycle.ViewModel

class BoardViewModel : ViewModel() {
    val LINHA = 30
    val COLUNA = 27

    var board = Array(LINHA) {
        Array(COLUNA) { 0 }
    }
}