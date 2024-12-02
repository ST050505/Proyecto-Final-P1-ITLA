package funcionamiento;

public interface ManejadorDeFacturacion {
	
	void actualizarPrecioProducto();	
	void calcularTotales();
	void procesarFacturacion();
    void limpiarCampos();
    void cargarProductos();
    void cargarClientes();
    void loadEvents();
}