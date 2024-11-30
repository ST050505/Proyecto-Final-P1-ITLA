package funcionamiento;

import java.util.ArrayList;
import java.util.List;

public class Cliente implements ManejadorDeClientes {
	
	private int idCliente;
	private String nombre;
	private String telefono;
	private String direccion;
	private String cedula_RNC;
	static List<Cliente> Clientes = new ArrayList<>();
	
	public Cliente(int idCliente, String nombre, String telefono, String direccion, String cedula_RNC) {
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		this.cedula_RNC = cedula_RNC;
	}

	//Metodos implementacion de interfaz ManejadorDeCliente

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public String getCedula_RNC() {
		return cedula_RNC;
	}

	public void setCedula_RNC(String cedula_RNC) {
		this.cedula_RNC = cedula_RNC;
	}


	@Override
	public void registrarCliente(Cliente cliente) {
        for (Cliente c : Clientes) {
            if (c.idCliente == cliente.idCliente) {
                System.out.println("Producto con el mismo ID ya existe.");
                return;
            }
        }
        
        Clientes.add(cliente);
        System.out.println("Producto agregado: " + cliente.getNombre());
		
	}

	@Override
	public Cliente consultarCliente(int idCliente) {
		return null;
	}

	@Override
	public void actualizarCliente(Cliente cliente) {
		
	}


}