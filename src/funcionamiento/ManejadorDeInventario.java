package funcionamiento;

public interface ManejadorDeInventario {
	
	void saveProduct(Producto producto);
	void editProduct(Producto producto);
	void deleteProduct(Producto producto);
	void limpiarCampos();
	void loadEvents();
	boolean validarCampos();
}