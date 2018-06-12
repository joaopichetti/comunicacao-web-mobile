package br.edu.utfpr.comunicacaowebmobile.model.servidor

data class Cliente(var codigo: Int, var nome: String, var cpf: String, var rg: String, var cidade: Cidade)