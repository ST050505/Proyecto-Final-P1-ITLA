package funcionamiento;

import java.util.List;

public interface ManejadorDeFacturacion {
	
    Factura crearFactura(Cliente cliente, List<Producto> producto);
	
	List<Factura> listarFacturas();
	
	double calcularTotales(Factura factura);
}
