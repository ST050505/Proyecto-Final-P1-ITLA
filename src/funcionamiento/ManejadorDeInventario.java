package funcionamiento;

public interface ManejadorDeInventario {
	
	void agregarproducto(Producto producto);
	
	void actualizarProducto(Producto producto);
	
	void eliminarProducto(int idProducto);
	
	Producto consultarProducto(int idProducto);

}