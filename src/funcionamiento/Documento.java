package funcionamiento;

import java.sql.Date;

public abstract class Documento {
private int idDocumento;
private Date fechaEmision;
private double montoTotal;

public Documento(int idDocumento, Date fechaEmision, double montoTotal) {
	this.idDocumento = idDocumento;
	this.fechaEmision = fechaEmision;
	this.montoTotal = montoTotal;
	}

	public abstract String generarDocumento();
	
	public abstract double calcularMontoTotal();
}