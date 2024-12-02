package funcionamiento;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String telefono;
    private String direccion;
    private String cedula_RNC;

    public Cliente(int idCliente, String nombre, String telefono, String direccion, String cedula_RNC) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.cedula_RNC = cedula_RNC;
    }

    // Getters y Setters
    public int getIdCliente() {return idCliente;}
    public void setIdCliente(int idCliente) {this.idCliente = idCliente;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getTelefono() {return telefono;}
    public void setTelefono(String telefono) {this.telefono = telefono;}

    public String getDireccion() {return direccion;}
    public void setDireccion(String direccion) {this.direccion = direccion;}

    public String getCedula_RNC() {return cedula_RNC;}
    public void setCedula_RNC(String cedula_RNC) {this.cedula_RNC = cedula_RNC;}
	
    // Devuelve un texto al comboBox para decir que se seleccione un cliente
    
	@Override
	public String toString() {
	    return nombre;
	}
}