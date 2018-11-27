package br.edu.utfpr.comunicacaowebmobile.model.servidor

data class Produto(var codigo: Int, var nome: String, var categoria: Categoria,
                   var preco: Double, var ativo: Boolean)