package funcionamiento;

public class Producto {
	
	private int idProducto;
	private String nombre;
	private String marca;
	private double precio;
	private int cantidad;
	private int cantidadDeInventario;
	
	public Producto(int idProducto, String nombre, String marca, double precio, int cantidad, int cantidadDeInventario) {
		
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.marca = marca;
		this.precio = precio;
		this.cantidad = cantidad;
		this.cantidadDeInventario = cantidadDeInventario;
	}
	
	// Getters y setters
	
	public int getIdProducto() {return idProducto;}
	public void setIdProducto(int idProducto) {this.idProducto = idProducto;}

	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}

	public String getMarca() {return marca;}
	public void setMarca(String marca) {this.marca = marca;}

	public double getPrecio() {return precio;}
	public void setPrecio(double precio) {this.precio = precio;}
	
	
	public int getCantidad() {return cantidad;}
	public void setCantidad(int cantidad) {this.cantidad = cantidad;}

	public int getCantidadDeInventario() {return cantidadDeInventario;}
	public void setCantidadDeInventario(int cantidadDeInventario) {this.cantidadDeInventario = cantidadDeInventario;}
	
	// Devuelve un texto al comboBox para decir que se seleccione un producto
	
	@Override
	public String toString() {
	    return nombre;
	}
}