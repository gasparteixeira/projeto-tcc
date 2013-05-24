package br.com.senac.event.event;

import java.util.Date;


public class PedidoEvent {

    private double valor;
    private Date dataTempo;

    public PedidoEvent(double valor, Date dataTempo) {
        this.valor = valor;
        this.dataTempo = dataTempo;
    }

    public double getValor() {
        return valor;
    }

    public Date getDataTempo() {
        return dataTempo;
    }

    @Override
    public String toString() {
        return "Pedido [R$ " + valor + "]";
    }
}
