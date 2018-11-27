package br.edu.utfpr.comunicacaowebmobile.model.servidor

import java.time.LocalDate

data class Pedido(var codigo: Int, var cliente: Cliente, var data: LocalDate, var itens: List<PedidoItem>)