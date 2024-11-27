package funcionamiento;

import java.util.List;

public interface ManejadorDePagos {
	
   ReciboDePago registrarPago(Factura factura, double monto);
   
   List<ReciboDePago> consultarPagos();
   
   boolean validarMontoPago(Factura factura, double monto);
   
}