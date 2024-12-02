package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import funcionamiento.Cliente;
import funcionamiento.ManejadorDeFacturacion;
import funcionamiento.Producto;
import db.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FacturaClientePanel extends JPanel implements ManejadorDeFacturacion {

    private DefaultTableModel model;
    private JTable facturas;
    private JButton btnFacturar, btnImprimir;
    private JPanel FacturarPanel;
    private JTextField textFieldMonto, textFieldMoneda, textFieldFecha;
    private JComboBox CBCliente, CBProducto;
    private JTextField textField;
    private JLabel lblImpuestos, lblCliente, lblProducto, lblCantidad, lblPrecio, lblSubtotal, lblTotal;
    private JTextPane textPane;
    private JScrollPane scrollPane;
    private Connection conn;
    private Runnable onActualizarInventario;
    private JLabel lblFacturacin;


    public FacturaClientePanel(JPanel Mainpanel) {
    	conn = ConexionDB.getConnection();
        initComponents();
        cargarProductos();
        cargarClientes();
        configureActionListeners();
        loadEvents();
    }

    private void initComponents() {
        this.setBackground(new Color(255, 255, 255));
        setLayout(null);

        // Panel de facturación
        FacturarPanel = new JPanel();
        FacturarPanel.setBounds(40, 40, 640, 462);
        FacturarPanel.setLayout(null);
        FacturarPanel.setVisible(true);
        add(FacturarPanel);
        
        // Tabla para mostrar facturas
        model = new DefaultTableModel();
        model.addColumn("Cliente");
        model.addColumn("Producto");
        model.addColumn("Precio");
        model.addColumn("Cantidad");
        model.addColumn("Subtotal");
        model.addColumn("Impuestos");
        model.addColumn("Total a pagar");
        
        facturas = new JTable(model);
        
        scrollPane = new JScrollPane(facturas);
        scrollPane.setBounds(33, 262, 576, 168);
        FacturarPanel.add(scrollPane);     
        
        lblCliente = new JLabel("Cliente");
        lblCliente.setHorizontalAlignment(SwingConstants.CENTER);
        lblCliente.setBounds(197, 150, 43, 20);
        FacturarPanel.add(lblCliente);

        lblProducto = new JLabel("Producto");
        lblProducto.setHorizontalAlignment(SwingConstants.CENTER);
        lblProducto.setBounds(35, 70, 49, 20);
        FacturarPanel.add(lblProducto);

        textFieldMonto = new JTextField();
        textFieldMonto.setBounds(342, 90, 56, 31);
        FacturarPanel.add(textFieldMonto);

        lblCantidad = new JLabel("Cantidad");
        lblCantidad.setHorizontalAlignment(SwingConstants.CENTER);
        lblCantidad.setBounds(342, 70, 53, 20);
        FacturarPanel.add(lblCantidad);

        textFieldMoneda = new JTextField();
        textFieldMoneda.setBounds(233, 90, 100, 32);
        FacturarPanel.add(textFieldMoneda);

        lblPrecio = new JLabel("Precio");
        lblPrecio.setHorizontalAlignment(SwingConstants.CENTER);
        lblPrecio.setBounds(233, 70, 43, 20);
        FacturarPanel.add(lblPrecio);

        textFieldFecha = new JTextField();
        textFieldFecha.setBounds(408, 90, 90, 31);
        FacturarPanel.add(textFieldFecha);

        lblSubtotal = new JLabel("Subtotal");
        lblSubtotal.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtotal.setBounds(409, 70, 49, 20);
        FacturarPanel.add(lblSubtotal);

        // Botones en el FacturarPanel
        btnFacturar = new JButton("Facturar");
        btnFacturar.setBounds(509, 211, 100, 31);
        FacturarPanel.add(btnFacturar);
                
        CBCliente = new JComboBox();
        CBCliente.setBounds(197, 170, 302, 32);
        FacturarPanel.add(CBCliente);
                
        CBProducto = new JComboBox();
        CBProducto.setBounds(33, 90, 190, 31);
        FacturarPanel.add(CBProducto);
                
        textField = new JTextField();
        textField.setText("");
        textField.setBounds(509, 90, 100, 31);
        FacturarPanel.add(textField);
                
        lblImpuestos = new JLabel("Impuestos");
        lblImpuestos.setHorizontalAlignment(SwingConstants.CENTER);
        lblImpuestos.setBounds(509, 70, 62, 20);
        FacturarPanel.add(lblImpuestos);
                
        textPane = new JTextPane();
        textPane.setBounds(509, 170, 100, 31);
        FacturarPanel.add(textPane);
                
        lblTotal = new JLabel("Total a pagar");
        lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotal.setBounds(510, 150, 71, 20);
        FacturarPanel.add(lblTotal);
        
        btnImprimir = new JButton("Control de Stock");
        btnImprimir.setBounds(374, 211, 125, 31);
        FacturarPanel.add(btnImprimir);
        
        lblFacturacin = new JLabel("FACTURACIÓN");
        lblFacturacin.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblFacturacin.setHorizontalAlignment(SwingConstants.CENTER);
        lblFacturacin.setBounds(255, 25, 130, 25);
        FacturarPanel.add(lblFacturacin);
                   
    }

    private void configureActionListeners() {
        // Llamar a métodos desde los ActionListeners
        CBProducto.addActionListener(e -> actualizarPrecioProducto());
        textFieldMonto.addActionListener(e -> calcularTotales());
        btnFacturar.addActionListener(e -> procesarFacturacion());
        btnImprimir.addActionListener(e -> Imprimir());
    }
    
    private void Imprimir() {
        try {
            String query = "SELECT * FROM factura";
            Connection conn = ConexionDB.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ConexionDB.imprimirFacturaEnArchivo(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al imprimir: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void actualizarPrecioProducto() {
        try {
            Object itemSeleccionado = CBProducto.getSelectedItem();

            // Verificar si el elemento seleccionado es una instancia de Producto
            if (itemSeleccionado instanceof Producto) {
                Producto productoSeleccionado = (Producto) itemSeleccionado;
                textFieldMoneda.setText(String.valueOf(productoSeleccionado.getPrecio()));
            } else {
                textFieldMoneda.setText(""); // Limpia el precio si no es un Producto válido
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el precio del producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void calcularTotales() {
        try {
            Producto productoSeleccionado = (Producto) CBProducto.getSelectedItem();
            if (productoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Selecciona un producto válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int cantidad = Integer.parseInt(textFieldMonto.getText());
            double subtotal = cantidad * productoSeleccionado.getPrecio();
            double impuestos = subtotal * 0.18;
            double total = subtotal + impuestos;

            textFieldFecha.setText(String.format("%.2f", subtotal));
            textField.setText(String.format("%.2f", impuestos));
            textPane.setText(String.format("%.2f", total));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Introduce una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void procesarFacturacion() {
        try {
            Cliente clienteSeleccionado = (Cliente) CBCliente.getSelectedItem();
            Producto productoSeleccionado = (Producto) CBProducto.getSelectedItem();
            
            if (clienteSeleccionado == null || productoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Selecciona un cliente y un producto válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtiene los valores ingresados
            int cantidad;
            double subtotal, impuestos, total;

            try {
                cantidad = Integer.parseInt(textFieldMonto.getText());
                subtotal = Double.parseDouble(textFieldFecha.getText());
                impuestos = Double.parseDouble(textField.getText());
                total = Double.parseDouble(textPane.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Introduce valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validación adicional
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a cero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (subtotal <= 0 || total <= 0) {
                JOptionPane.showMessageDialog(this, "El subtotal y el total deben ser valores positivos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Consulta SQL para insertar la factura
            String queryFactura = "INSERT INTO factura (id_cliente, id_producto, cantidad, subtotal, impuestos, total, precio) VALUES (?, ?, ?, ?, ?, ?, ?)";
            String queryUpdateProducto = "UPDATE producto SET cantidad_en_inventario = cantidad_en_inventario - ? WHERE id_producto = ?";

            Connection conn = ConexionDB.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Error de conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (PreparedStatement stmtFactura = conn.prepareStatement(queryFactura, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement stmtUpdateProducto = conn.prepareStatement(queryUpdateProducto)) {
                
                // Inserta la factura
                stmtFactura.setInt(1, clienteSeleccionado.getIdCliente());
                stmtFactura.setInt(2, productoSeleccionado.getIdProducto());
                stmtFactura.setInt(3, cantidad);
                stmtFactura.setDouble(4, subtotal);
                stmtFactura.setDouble(5, impuestos);
                stmtFactura.setDouble(6, total);
                stmtFactura.setDouble(7, productoSeleccionado.getPrecio());

                int rowsFactura = stmtFactura.executeUpdate();

                if (rowsFactura > 0) {
                    ResultSet rs = stmtFactura.getGeneratedKeys();
                    if (rs.next()) {
                        int idFactura = rs.getInt(1); // ID generado de la factura
                        JOptionPane.showMessageDialog(this, "Factura generada correctamente con ID: " + idFactura);
                    }

                    // Actualiza la cantidad en inventario del producto
                    stmtUpdateProducto.setInt(1, cantidad);
                    stmtUpdateProducto.setInt(2, productoSeleccionado.getIdProducto());
                    int rowsUpdate = stmtUpdateProducto.executeUpdate();

                    if (rowsUpdate > 0) {
                        JOptionPane.showMessageDialog(this, "Cantidad restante del producto facturado actualizada correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo actualizar el inventario.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                }

                // Agregar datos a la tabla en la interfaz
                model.addRow(new Object[]{
                        clienteSeleccionado.getNombre(),
                        productoSeleccionado.getNombre(),
                        productoSeleccionado.getPrecio(),
                        cantidad,
                        subtotal,
                        impuestos,
                        total
                });
                
                if (onActualizarInventario != null) {
                    onActualizarInventario.run();
                }

                limpiarCampos();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al procesar la facturación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void limpiarCampos() {
    	textFieldMoneda.setText("");
        textFieldMonto.setText("");
        textFieldFecha.setText("");
        textField.setText("");
        textPane.setText("");
        CBCliente.setSelectedIndex(-1);
        CBProducto.setSelectedIndex(-1);
    }
    
    private List<Producto> obtenerProductosDesdeDB() {
        List<Producto> listaProductos = new ArrayList<>();
        Connection conn = ConexionDB.getConnection(); // Revisa que esta conexión no sea null.

        if (conn == null) {
            System.err.println("Error: La conexión a la base de datos es null.");
            return listaProductos;
        }

        String query = "SELECT * FROM producto";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id_producto");
                String nombre = rs.getString("nombre_producto");
                String marca = rs.getString("marca_producto");
                double precio = rs.getDouble("precio_producto");
                int cantidad = rs.getInt("cantidad_en_inventario");

                Producto producto = new Producto(id, nombre, marca, precio, cantidad, cantidad);
                listaProductos.add(producto);
                System.out.println("Producto cargado: " + producto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return listaProductos;
    }
    
    public static List<Cliente> obtenerClientesDesdeDB() {
        List<Cliente> listaClientes = new ArrayList<>();
        Connection conn = ConexionDB.getConnection();

        if (conn == null) {
            System.err.println("Error: La conexión a la base de datos es null.");
            return listaClientes;
        }

        System.out.println("Conexión a la base de datos establecida.");

        String query = "SELECT * FROM cliente";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre_cliente");
                String correo = rs.getString("email"); // Verifica si esta columna es correcta
                String direccion = rs.getString("direccion");
                String cedulaRNC = rs.getString("PI");

                Cliente cliente = new Cliente(id, nombre, correo, direccion, cedulaRNC);
                listaClientes.add(cliente);
                System.out.println("Cliente cargado: " + cliente.getNombre());
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
        }

        System.out.println("Total de clientes cargados: " + listaClientes.size());
        return listaClientes;
    }

    @Override
    public void cargarProductos() {
        CBProducto.removeAllItems(); // Limpia el ComboBox
        CBProducto.addItem("Seleccione un nuevo producto");

        List<Producto> productos = obtenerProductosDesdeDB(); // Obtiene los productos de la base de datos.
        if (productos == null || productos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos disponibles en el inventario.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Producto producto : productos) {
            CBProducto.addItem(producto); // Agrega cada producto al ComboBox.
        }
    }   
    
    @Override
    public void cargarClientes() {
        CBCliente.removeAllItems(); // Limpia el ComboBox
        CBCliente.addItem("Seleccione un nuevo cliente");

        List<Cliente> clientes = obtenerClientesDesdeDB(); // Obtiene los clientes de la base de datos.

        if (clientes == null || clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay clientes disponibles.", "Información", JOptionPane.INFORMATION_MESSAGE);
            System.err.println("La lista de clientes está vacía.");
            return;
        }

        for (Cliente cliente : clientes) {
            CBCliente.addItem(cliente); // Agrega cada cliente al ComboBox
            System.out.println("Cliente añadido al ComboBox: " + cliente.getNombre());
        }

        System.out.println("Total de clientes en el ComboBox: " + CBCliente.getItemCount());
    }
    
    @Override
    public void loadEvents() {
        try {
            model.setRowCount(0);
            String query = "SELECT * FROM factura";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("id_cliente"),
                    rs.getInt("id_producto"),
                    rs.getInt("precio"),
                    rs.getInt("cantidad"),
                    rs.getDouble("subtotal"),
                    rs.getDouble("impuestos"),
                    rs.getDouble("total"),
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los eventos: " + e.getMessage());
        }
    }
    
    public void setOnActualizarInventario(Runnable onActualizarInventario) {
        this.onActualizarInventario = onActualizarInventario;
    }
    
    public void recargarClientes() {
        cargarClientes(); // Reutiliza el método existente para cargar clientes
        JOptionPane.showMessageDialog(this, "La caja de clientes ha sido actualizada.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void recargarProductos() {
        cargarProductos(); // Reutiliza el método existente para cargar clientes
        JOptionPane.showMessageDialog(this, "La caja de productos ha sido actualizada.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}