package db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import paneles.ClientePanel;
	
public class ConexionDB {

    // Método para obtener una conexión
    public static Connection getConnection() {
        try {
            // Establecer conexión a la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto_final", "root", "admin050505");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para cerrar la conexión
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } 
    
    public static void imprimirClientesEnArchivo(ResultSet rs) {
        // Establecer la ruta donde quieres guardar el archivo
        File file = new File("C:\\Users\\sebas\\Downloads\\proyecto_final.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Escribir los encabezados en el archivo
            writer.write("DATOS DE LOS CLIENTES\n\n");

            // Iterar sobre el ResultSet y escribir los eventos
            while (rs.next()) {
                int idCliente = rs.getInt("id_cliente");
                String nombreCliente = rs.getString("nombre_cliente");
                String email = rs.getString("email");
                String direccion = rs.getString("direccion");
                String PI = rs.getString("PI");

                writer.write("ID del cliente: " + idCliente + 
                		" | Nombre: " + nombreCliente + 
                		" | Correo electrónico: " + email + 
                		" | Dirección: " + direccion + 
                		" | Identificación: " + PI +"\n");
            }

            // Confirmar que el archivo fue creado
            if (file.exists()) {
                System.out.println("El archivo eventos.txt se ha guardado en: " + file.getAbsolutePath());
                ClientePanel.abrirArchivo(file.getAbsolutePath());  // Llamar a abrir el archivo
            } else {
                System.out.println("Error: El archivo no se ha creado.");
            }

        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void imprimirProductoEnArchivo(ResultSet rs) {
        // Establecer la ruta donde quieres guardar el archivo
        File file = new File("C:\\Users\\sebas\\Downloads\\proyecto_final.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Escribir los encabezados en el archivo
            writer.write("DATOS DE LOS PRODUCTOS\n\n");

            // Iterar sobre el ResultSet y escribir los eventos
            while (rs.next()) {
                int idProducto = rs.getInt("id_producto");
                String nombreProducto = rs.getString("nombre_producto");
                String marca = rs.getString("marca_producto");
                int precio = rs.getInt("precio_producto");
                int cantidad = rs.getInt("cantidad_en_inventario");

                writer.write("ID del producto: " + idProducto + 
                		" | Nombre: " + nombreProducto + 
                		" | Marca: " + marca + 
                		" | Precio: " + precio + 
                		" | Cantidad: " + cantidad +"\n");
            }

            // Confirmar que el archivo fue creado
            if (file.exists()) {
                System.out.println("El archivo eventos.txt se ha guardado en: " + file.getAbsolutePath());
                ClientePanel.abrirArchivo(file.getAbsolutePath());  // Llamar a abrir el archivo
            } else {
                System.out.println("Error: El archivo no se ha creado.");
            }

        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void imprimirFacturaEnArchivo(ResultSet rs) {
        // Establecer la ruta donde quieres guardar el archivo
        File file = new File("C:\\Users\\sebas\\Downloads\\proyecto_final.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Escribir los encabezados en el archivo
            writer.write("DATOS DE LAS FACTURAS\n\n");

            // Iterar sobre el ResultSet y escribir los eventos
            while (rs.next()) {
                int idFactura = rs.getInt("id_factura");
                int idCliente = rs.getInt("id_cliente");
                int idProducto = rs.getInt("id_producto");
                int precio = rs.getInt("precio");
                int cantidad = rs.getInt("cantidad");
                int subtotal = rs.getInt("subtotal");
                int impuestos = rs.getInt("impuestos");
                int total = rs.getInt("total");

                writer.write("ID de la factura: " + idFactura + 
                		" | ID del cliente: " + idCliente + 
                		" | ID de producto: " + idProducto + 
                		" | Precio: " + precio + 
                		" | Cantidad: " + cantidad +
                		" | SubTotal: " + subtotal +
                		" | Impuestos: " + impuestos +
                 		" | Total: " + total +"\n");
            }

            // Confirmar que el archivo fue creado
            if (file.exists()) {
                System.out.println("El archivo eventos.txt se ha guardado en: " + file.getAbsolutePath());
                ClientePanel.abrirArchivo(file.getAbsolutePath());  // Llamar a abrir el archivo
            } else {
                System.out.println("Error: El archivo no se ha creado.");
            }

        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}