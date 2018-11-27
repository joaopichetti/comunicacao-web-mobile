package br.edu.utfpr.comunicacaowebmobile.model.servidor

data class PedidoItem(var codigo: Int, var pedido: Pedido, var produto: Produto,
                      var quantidade: Double, var valorUnitario: Double)