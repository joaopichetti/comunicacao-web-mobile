package br.edu.utfpr.comunicacaowebmobile.model.servidor

import java.time.LocalDate

data class Cliente(var codigo: Int, var nome: String, var cpf: String, var rg: String,
                   var telefone: String, var dataNasc: LocalDate, var limite: Double,
                   var prazoPag: Int, var cidade: Cidade)