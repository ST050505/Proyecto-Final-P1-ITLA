package funcionamiento;

import java.util.ArrayList;
import java.util.List;

import paneles.*;

public class Producto implements ManejadorDeInventario {
	
	private int idProducto;
	private String nombre;
	private String marca;
	private double precio;
	private int cantidad;
	private int cantidadDeInventario;
    static List<Producto> inventario = new ArrayList<>();
	
	public Producto(int idProducto, String nombre, String marca, double precio, int cantidad) {
		
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.marca = marca;
		this.precio = precio;
		this.cantidad = cantidad;
		this.cantidadDeInventario = cantidadDeInventario;
	}
	
	//Getters y setters
	

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getCantidadDeInventario() {
		return cantidadDeInventario;
	}

	public void setCantidadDeInventario(int cantidadDeInventario) {
		this.cantidadDeInventario = cantidadDeInventario;
	}


	

	
	//Metodos implementacion de interfaz ManejadorDeInventario


	@Override
    public void agregarproducto(Producto producto) {
       
        for (Producto p : inventario) {
            if (p.idProducto == producto.idProducto) {
                System.out.println("Producto con el mismo ID ya existe.");
                return;
            }
        }
        
        inventario.add(producto);
        System.out.println("Producto agregado: " + producto.getNombre());
    }


	@Override
	public void editarProducto(Producto producto) {
	    for (Producto p : inventario) {
	        if (p.idProducto == producto.idProducto) {
	            p.setNombre(producto.getNombre());
	            p.setMarca(producto.getMarca());
	            p.setPrecio(producto.getPrecio());
	            p.setCantidadDeInventario(producto.getCantidadDeInventario());
	            System.out.println("Producto editado: " + producto.getNombre());
	            return;
	        }
	    }
	    System.out.println("Producto no encontrado para editar.");
	}

	@Override
	public void eliminarProducto(int idProducto) {
	    Producto productoAEliminar = null;
	    for (Producto p : inventario) {
	        if (p.idProducto == idProducto) {
	            productoAEliminar = p;
	            break;
	        }
	    }
	    if (productoAEliminar != null) {
	        inventario.remove(productoAEliminar);
	        System.out.println("Producto eliminado: " + productoAEliminar.getNombre());
	    } else {
	        System.out.println("Producto no encontrado para eliminar.");
	    }
	}


	@Override
	public Producto consultarProducto(int idProducto) {
		return null;
	}

}