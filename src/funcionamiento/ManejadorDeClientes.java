package funcionamiento;

public interface ManejadorDeClientes {
	
	void registrarCliente(Cliente cliente);
	
	Cliente consultarCliente(int idCliente);
	
	void actualizarCliente(Cliente cliente);

}