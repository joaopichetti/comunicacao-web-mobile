package br.edu.utfpr.comunicacaowebmobile.model.servidor

import java.text.SimpleDateFormat
import java.util.*

data class Cidade(var codigo: Int, var nome: String, var ufSigla: String, var cepInicial: String,
                  var cepFinal: String, var dataFundacao: Date) {
    override fun toString(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return "$codigo - $nome / $ufSigla\nCEP inicial: $cepInicial\nCEP final: $cepFinal\nData da fundação: ${dateFormat.format(dataFundacao)}"
    }
}