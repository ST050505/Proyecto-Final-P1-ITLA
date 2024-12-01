package gui;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
	
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
    
}