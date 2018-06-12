package br.edu.utfpr.comunicacaowebmobile.model.servidor

data class Produto(var codigo: Int, var nome: String, var marca: String, var fabricante: String,
                   var preco: Double, var ativo: Boolean, var modelo: String)