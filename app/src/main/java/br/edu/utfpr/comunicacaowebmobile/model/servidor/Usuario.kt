package br.edu.utfpr.comunicacaowebmobile.model.servidor

data class Usuario(var codigo: Int, var username: String, var password: String, var nome: String,
                   var email: String, var rolename: String)