package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import paneles.*;
import db.*;

public class Gui {

    JFrame frame;
    JPanel sidebar, dropdownPanel;
    boolean isDropdownVisible = false;
    private JButton dashboardButton;
    private InventarioPanel inventarioPanel;
    private FacturaClientePanel facturaClientePanel;
    private ClientePanel clientePanel;
    private Connection conexion;

    public Gui() {
    	conexion = ConexionDB.getConnection();
        initialize();

        clientePanel.setOnClienteActualizado(() -> facturaClientePanel.recargarClientes());
        inventarioPanel.setOnProductoActualizado(() -> facturaClientePanel.recargarProductos());

       frame.addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		ConexionDB.closeConnection(conexion);  // Cierra la conexión a la base de datos
        		System.out.println("Se ha cerrado la conexión.");
        		JOptionPane.showMessageDialog(frame, "La conexión se ha cerrado correctamente.", 
        				"Cierre de Conexión", JOptionPane.INFORMATION_MESSAGE);
        		System.exit(0);
        	}
        });
    }

    private void initialize() {
    	
        frame = new JFrame("Tienda de celulares - Proyecto Final");
        frame.setBounds(100, 100, 1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);

        // Crear Sidebar
        
        sidebar = new JPanel();
        sidebar.setBounds(0, 0, 250, 600);
        sidebar.setBackground(new Color(54, 57, 63));
        sidebar.setLayout(null);
        sidebar.setPreferredSize(new Dimension(250, frame.getHeight()));
        frame.getContentPane().add(sidebar);

        // Panel para el menú desplegable
        
        dropdownPanel = new JPanel();
        dropdownPanel.setBackground(new Color(45, 48, 54));
        dropdownPanel.setBounds(30, 69, 180, 160);
        dropdownPanel.setVisible(false);
        sidebar.add(dropdownPanel);
        dropdownPanel.setLayout(null);
        
        JLabel opcion1 = new JLabel("Inventario");
        opcion1.setBounds(0, 0, 180, 64);
        opcion1.setForeground(Color.WHITE);
        opcion1.setFont(new Font("Verdana", Font.BOLD, 14));
        dropdownPanel.add(opcion1);
        
        JLabel opcion2 = new JLabel("Factura a cliente");
        opcion2.setHorizontalAlignment(SwingConstants.CENTER);
        opcion2.setBounds(0, 54, 180, 45);
        opcion2.setForeground(Color.WHITE);
        opcion2.setFont(new Font("Arial", Font.PLAIN, 14));
        dropdownPanel.add(opcion2);
        
        JLabel opcion3 = new JLabel("Cliente");
        opcion3.setHorizontalAlignment(SwingConstants.CENTER);
        opcion3.setBounds(0, 110, 180, 28);
        opcion3.setForeground(new Color(255, 255, 255));
        opcion3.setFont(new Font("Arial", Font.PLAIN, 14));
        dropdownPanel.add(opcion3);

        // Agregar cada una al dropwdownPanel
        
        createDropdownOption(opcion1);
        createDropdownOption(opcion2);
        createDropdownOption(opcion3);
        
        dropdownPanel.add(opcion1);
        dropdownPanel.add(opcion2);
        dropdownPanel.add(opcion3);
        
        CardLayout cardLayout = new CardLayout();
        JPanel Mainpanel = new JPanel(cardLayout);
       
        Mainpanel.setBounds(260, 11, 717, 541);
        frame.getContentPane().add(Mainpanel);
        
        inventarioPanel = new InventarioPanel(Mainpanel);
        facturaClientePanel = new FacturaClientePanel(Mainpanel);
        clientePanel = new ClientePanel(Mainpanel);
        
        facturaClientePanel.setOnActualizarInventario(() -> inventarioPanel.recargarInventario());
        inventarioPanel.setOnProductoActualizado(() -> {
        	facturaClientePanel.cargarProductos(); });

        Mainpanel.setLayout(new CardLayout(0, 0));
        
        Mainpanel.add(inventarioPanel, "Inventario");
        Mainpanel.add(facturaClientePanel, "Factura a cliente");
        Mainpanel.add(clientePanel, "Cliente panel");
  
        agregarCambioDePanel(opcion1, Mainpanel, "Inventario");
        agregarCambioDePanel(opcion2, Mainpanel, "Factura a cliente");
        agregarCambioDePanel(opcion3, Mainpanel, "Cliente panel");

        dashboardButton = new JButton("Dashboard");
        dashboardButton.setBounds(30, 39, 180, 30);
        sidebar.add(dashboardButton);
        dashboardButton.setBackground(new Color(45, 48, 54));
        dashboardButton.setForeground(Color.WHITE);
        dashboardButton.setFocusPainted(false);
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

    // Método para hacer cambio de panel a través de un MouseListener
    
    private void agregarCambioDePanel(JLabel opcion, JPanel Mainpanel, String nombrePanel) {
        opcion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CardLayout cl = (CardLayout) (Mainpanel.getLayout());
                cl.show(Mainpanel, nombrePanel);
            }
        });
    }
    
    // Método para crear opciones estilizadas (sin fondo)
    
    private void createDropdownOption(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setHorizontalAlignment(SwingConstants.CENTER);
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