package br.edu.utfpr.comunicacaowebmobile.model.servidor

import java.math.BigDecimal

data class PedidoItem(var codigo: Int, var pedido: Pedido, var produto: Produto,
                      var quantidade: BigDecimal, var valorUnitario: BigDecimal)