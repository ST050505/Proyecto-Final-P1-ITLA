package funcionamiento;

import java.sql.Date;

public abstract class Factura extends Documento{

    private Cliente cliente;

    public Factura(int idDocumento, Date fechaEmision, double montoTotal, Cliente cliente) {
        super(idDocumento, fechaEmision, montoTotal);
        this.setCliente(cliente);
    }
    
    // Getters y Setters
    
    public Cliente getCliente() {return cliente;}
	public void setCliente(Cliente cliente) {this.cliente = cliente;}

    //Metodos de herencia de clase abstracta Documento 

    @Override
    public String generarDocumento() {
        return null;
    }

    @Override
    public double calcularMontoTotal() {
        return 0;
    }
}