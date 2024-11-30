package funcionamiento;

import java.util.ArrayList;
import java.util.List;

public class Cliente implements ManejadorDeClientes {

    private int idCliente;
    private String nombre;
    private String telefono;
    private String direccion;
    private String cedula_RNC;
    public static List<Cliente> Clientes = new ArrayList<>();

    public Cliente(int idCliente, String nombre, String telefono, String direccion, String cedula_RNC) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.cedula_RNC = cedula_RNC;
    }

    // Métodos de acceso y modificación
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

    // Implementación de la interfaz ManejadorDeClientes

    @Override
    public void registrarCliente(Cliente cliente) {
        // Verificar si ya existe un cliente con el mismo ID o Cédula
        for (Cliente c : Clientes) {
            if (c.idCliente == cliente.idCliente || c.cedula_RNC.equals(cliente.cedula_RNC)) {
                System.out.println("Cliente con el mismo ID o Cédula ya existe.");
                return;
            }
        }
        
        Clientes.add(cliente);
        System.out.println("Cliente agregado: " + cliente.getNombre());
    }

    @Override
    public Cliente consultarCliente(int idCliente) {
        // Buscar un cliente por su ID
        for (Cliente c : Clientes) {
            if (c.idCliente == idCliente) {
                return c;
            }
        }
        return null; // Retorna null si no se encuentra el cliente
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        // Actualiza los datos de un cliente en la lista
        for (Cliente c : Clientes) {
            if (c.idCliente == cliente.idCliente) {
                c.setNombre(cliente.getNombre());
                c.setTelefono(cliente.getTelefono());
                c.setDireccion(cliente.getDireccion());
                c.setCedula_RNC(cliente.getCedula_RNC());
                System.out.println("Cliente actualizado: " + cliente.getNombre());
                return;
            }
        }
        System.out.println("Cliente no encontrado para actualizar.");
    }

    @Override
    public String toString() {
        // Sobrescribimos el toString() para mostrar un nombre completo o una representación amigable
        return nombre + " (" + cedula_RNC + ")";
    }
}

