package funcionamiento;

public class Producto implements ManejadorDeInventario {
	
	private int idProducto;
	private String nombre;
	private String marca;
	private double precio;
	private int cantidadDeInventario;
	
	public Producto(int idProducto, String nombre, String marca, double precio, 
	int cantidadDeInventario) {
		
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.marca = marca;
		this.precio = precio;
		this.cantidadDeInventario = cantidadDeInventario;
	}
	
	//Metodos implementacion de interfaz ManejadorDeInventario

	@Override
	public void agregarproducto(Producto producto) {
	}

	@Override
	public void actualizarProducto(Producto producto) {
		
	}

	@Override
	public void eliminarProducto(int idProducto) {
		
	}

	@Override
	public Producto consultarProducto(int idProducto) {
		return null;
	}

}