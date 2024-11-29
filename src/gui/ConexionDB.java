package gui;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
	
public class ConexionDB {
	
	private static final String URL = "jdbc:mysql://localhost:3306/proyecto_final"; // Cambia el nombre de la base de datos
	private static final String USER = "root"; // Tu usuario
	private static final String PASSWORD = "admin050505"; // Tu contraseña
	
	private static Connection conexion = null;
	
	// Método para obtener la conexión
	    
	public static Connection getConexion() {
		if (conexion == null) {
			try {
				// Cargar el driver
	                Class.forName("com.mysql.cj.jdbc.Driver");
	                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
	                System.out.println("Conexión establecida");
	            } catch (ClassNotFoundException | SQLException e) {
	                System.out.println("Error al conectar: " + e.getMessage());
	            }
	        }
	        return conexion;
	    }
	
	// Método para cerrar la conexión
	    
	public static void cerrarConexion() {
		try {
			if (conexion != null && !conexion.isClosed()) {
				conexion.close();
				System.out.println("Conexión cerrada");
			}
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexión: " + e.getMessage());
		}
	}
}