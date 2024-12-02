package funcionamiento;

public interface ManejadorDeClientes {
	
	void saveClient(Cliente cliente);
	void editClient(Cliente cliente);
	void deleteClient(Cliente cliente);
	void limpiarCampos();
	void loadEvents();
}