package br.edu.utfpr.comunicacaowebmobile.model.servidor

data class Cidade(var codigo: Int, var nome: String, var ufSigla: String) {
    override fun toString(): String {
        return "Código: $codigo\nNome: $nome\nSigla: $ufSigla"
    }
}