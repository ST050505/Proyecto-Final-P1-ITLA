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

	// Getters y Setters
	
	public int getIdDocumento() {return idDocumento;}
	public void setIdDocumento(int idDocumento) {this.idDocumento = idDocumento;}

	public Date getFechaEmision() {return fechaEmision;}
	public void setFechaEmision(Date fechaEmision) {this.fechaEmision = fechaEmision;}

	public double getMontoTotal() {return montoTotal;}
	public void setMontoTotal(double montoTotal) {this.montoTotal = montoTotal;}
}