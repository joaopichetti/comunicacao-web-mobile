package br.edu.utfpr.comunicacaowebmobile.model.servidor

import java.util.*

data class Pedido(var codigo: Int, var cliente: Cliente, var data: Calendar, var itens: List<PedidoItem>)