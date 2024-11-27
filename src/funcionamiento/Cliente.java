package funcionamiento;

public class Cliente implements ManejadorDeClientes {
	private int idCliente;
	private String nombre;
	private String telefono;
	private String direccion;
	
	public Cliente(int idCliente, String nombre, String telefono, String direccion) {
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
	}
	
	//Metodos implementacion de interfaz ManejadorDeCliente

	@Override
	public void registrarCliente(Cliente cliente) {
		
	}

	@Override
	public Cliente consultarCliente(int idCliente) {
		return null;
	}

	@Override
	public void actualizarCliente(Cliente cliente) {
		
	}

}