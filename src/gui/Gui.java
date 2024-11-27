package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import paneles.*;

public class Gui {

    JFrame frame;
    JPanel sidebar, dropdownPanel;
    boolean isDropdownVisible = false;
    private JButton dashboardButton;
    private InventarioPanel inventarioPanel;
    private FacturaClientePanel facturaClientePanel;
    private ClientePanel clientePanel;
    private ReciboPago reciboPago;
    // private Connection conexion; // Añadir la conexión

    public Gui() {
       /*conexion = ConexionDB.getConexion(); // Obtener la conexión antes de inicializar la interfaz gráfica*/
        initialize();
        
        /*frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ConexionDB.cerrarConexion();  // Cierra la conexión a la base de datos
                JOptionPane.showMessageDialog(frame, "La conexión se ha cerrado correctamente.", 
                        "Cierre de Conexión", JOptionPane.INFORMATION_MESSAGE);  // Mostrar mensaje de cierre
                System.exit(0);  // Finaliza la aplicación
            }
        });*/
    }

    private void initialize() {
    	
        // Configuración de la ventana principal
    	
        frame = new JFrame("Tienda de celulares - Proyecto Final");
        frame.setBounds(100, 100, 1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);

        // Crear Sidebar
        
        sidebar = new JPanel();
        sidebar.setBounds(0, 0, 250, 600);
        sidebar.setBackground(new Color(54, 57, 63)); // Color oscuro
        sidebar.setLayout(null); // Usamos layout null para controlar el posicionamiento manual
        sidebar.setPreferredSize(new Dimension(250, frame.getHeight())); // Sidebar más ancho
        frame.getContentPane().add(sidebar);

        // Panel para el menú desplegable
        
        dropdownPanel = new JPanel();
        dropdownPanel.setBackground(new Color(45, 48, 54));
        dropdownPanel.setBounds(30, 69, 180, 323);
        dropdownPanel.setVisible(false);
        sidebar.add(dropdownPanel);
        dropdownPanel.setLayout(null);
        
        JLabel opcion1 = new JLabel("Inventario");
        opcion1.setBounds(0, 0, 180, 64);
        opcion1.setForeground(Color.WHITE);
        opcion1.setFont(new Font("Arial", Font.PLAIN, 14));
        dropdownPanel.add(opcion1);
        
        JLabel opcion2 = new JLabel("Factura a cliente");
        opcion2.setBounds(0, 54, 180, 45);
        opcion2.setForeground(Color.WHITE);
        opcion2.setFont(new Font("Arial", Font.PLAIN, 14));
        dropdownPanel.add(opcion2);
        
        JLabel opcion3 = new JLabel("Cliente");
        opcion3.setBounds(0, 110, 180, 28);
        opcion3.setForeground(new Color(255, 255, 255));
        opcion3.setFont(new Font("Arial", Font.PLAIN, 14));
        dropdownPanel.add(opcion3);
        
        JLabel opcion4 = new JLabel("Recibo de pago");
        opcion4.setBounds(0, 149, 180, 45);
        opcion4.setFont(new Font("Arial", Font.PLAIN, 14));
        opcion4.setForeground(Color.WHITE);
        dropdownPanel.add(opcion4);

        // Agregar cada una al dropwdownPanel
        
        createDropdownOption(opcion1);
        createDropdownOption(opcion2);
        createDropdownOption(opcion3);
        createDropdownOption(opcion4);
        
        dropdownPanel.add(opcion1);
        dropdownPanel.add(opcion2);
        dropdownPanel.add(opcion3);
        dropdownPanel.add(opcion4);
        
        JPanel panel = new JPanel();
        panel.setBounds(260, 11, 717, 541);
        frame.getContentPane().add(panel);
        
        inventarioPanel = new InventarioPanel();
        facturaClientePanel = new FacturaClientePanel();
        clientePanel = new ClientePanel();
        reciboPago = new ReciboPago();
        

        panel.setLayout(new CardLayout(0, 0));
        
        panel.add(inventarioPanel, "Inventario");
        panel.add(facturaClientePanel, "Factura a cliente");
        panel.add(clientePanel, "Cliente panel");
        panel.add(reciboPago, "Recibo de pago");

        opcion1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Cambiar el panel cuando se haga clic
                CardLayout cl = (CardLayout) (panel.getLayout());
                cl.show(panel, "Inventario");
            }
        });
        
        opcion2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Cambiar el panel cuando se haga clic
                CardLayout cl = (CardLayout) (panel.getLayout());
                cl.show(panel, "Factura a cliente");
            }
        });
        
        opcion3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Cambiar el panel cuando se haga clic
                CardLayout cl = (CardLayout) (panel.getLayout());
                cl.show(panel, "Cliente panel");
            }
        });
        
        opcion4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Cambiar el panel cuando se haga clic
                CardLayout cl = (CardLayout) (panel.getLayout());
                cl.show(panel, "Recibo de pago");
            }
        });

        // Botón principal (Dashboard)
        
        dashboardButton = new JButton("Dashboard");
        dashboardButton.setBounds(30, 39, 180, 30);
        sidebar.add(dashboardButton);
        dashboardButton.setBackground(new Color(45, 48, 54));
        dashboardButton.setForeground(Color.WHITE);
        dashboardButton.setFocusPainted(false); // Eliminar el borde cuando se hace clic
        dashboardButton.setFont(new Font("Arial", Font.BOLD, 16));
        dashboardButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ActionListener para mostrar u ocultar el menú desplegable
        
        dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDropdownVisible = !isDropdownVisible;
                dropdownPanel.setVisible(isDropdownVisible);
                sidebar.revalidate();
                sidebar.repaint();
            }
        });

        // Acciones de las opciones del menú
        frame.setVisible(true);
    }

    // Método para crear opciones estilizadas (sin fondo)
    
    private void createDropdownOption(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setOpaque(false); // Sin fondo
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Agregar efectos al pasar el ratón
        label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label.setForeground(new Color(173, 216, 230)); // Cambiar color cuando el ratón entra
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                label.setForeground(Color.WHITE); // Regresar al color original cuando el ratón sale
            }
        });
    }
}