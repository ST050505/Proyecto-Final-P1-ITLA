package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import funcionamiento.Producto;
import gui.ConexionDB;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventarioPanel extends JPanel {
	
    private DefaultTableModel model;
    private JTable productos;
    private JButton btnNuevo, btnImprimir, btnGuardar, btnCerrar, btnEditar, btnEliminar;
    private JPanel AgregarProductoPanel;
    private JTextField textFieldNombre, textFieldMarca, textFieldPrecio, textFieldCantidad;
    private JLabel lblNombre, lblMarca, lblPrecio, lblCantidad;
    private JScrollPane scrollPane;
    static ArrayList<Producto> producto = new ArrayList<>();
    private Connection conn;

    public InventarioPanel(JPanel Mainpanel) {
    	conn = ConexionDB.getConnection();
    	this.setBackground(Color.WHITE);
        setLayout(null);
        initComponents();
        configureActionListeners();
        loadEvents();
    }

    private void initComponents() {
        // Botones
        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(50, 59, 104, 37);
        add(btnNuevo);

        btnImprimir = new JButton("Imprimir");
        btnImprimir.setBounds(396, 59, 104, 37);
        add(btnImprimir);

        btnEditar = new JButton("Editar");
        btnEditar.setBounds(168, 59, 104, 37);
        add(btnEditar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(282, 59, 104, 37);
        add(btnEliminar);

        // Tabla y modelo
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Marca");
        model.addColumn("Precio");
        model.addColumn("Cantidad");

        productos = new JTable(model);

        scrollPane = new JScrollPane(productos);
        scrollPane.setBounds(50, 161, 600, 289);
        this.add(scrollPane);

        // Panel para agregar productos
        AgregarProductoPanel = new JPanel();
        AgregarProductoPanel.setBounds(50, 164, 600, 300);
        AgregarProductoPanel.setBackground(Color.WHITE);
        AgregarProductoPanel.setLayout(null);

        textFieldNombre = new JTextField();
        textFieldNombre.setBounds(84, 47, 145, 30);
        AgregarProductoPanel.add(textFieldNombre);

        textFieldMarca = new JTextField();
        textFieldMarca.setBounds(84, 124, 145, 30);
        AgregarProductoPanel.add(textFieldMarca);

        textFieldPrecio = new JTextField();
        textFieldPrecio.setBounds(327, 47, 130, 30);
        AgregarProductoPanel.add(textFieldPrecio);

        textFieldCantidad = new JTextField();
        textFieldCantidad.setBounds(327, 124, 130, 30);
        AgregarProductoPanel.add(textFieldCantidad);

        lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(84, 22, 112, 14);
        AgregarProductoPanel.add(lblNombre);

        lblMarca = new JLabel("Marca");
        lblMarca.setBounds(84, 99, 96, 14);
        AgregarProductoPanel.add(lblMarca);

        lblPrecio = new JLabel("Precio");
        lblPrecio.setBounds(327, 22, 112, 14);
        AgregarProductoPanel.add(lblPrecio);

        lblCantidad = new JLabel("Cantidad");
        lblCantidad.setBounds(327, 99, 94, 14);
        AgregarProductoPanel.add(lblCantidad);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(284, 270, 301, 30);
        AgregarProductoPanel.add(btnCerrar);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(0, 270, 263, 30);
        AgregarProductoPanel.add(btnGuardar);
    }

    private void configureActionListeners() {
        btnNuevo.addActionListener(e -> showAddProductPanel());
        btnCerrar.addActionListener(e -> showProductTable());
        btnGuardar.addActionListener(e -> saveProduct());
        btnEditar.addActionListener(e -> editProduct());
        btnEliminar.addActionListener(e -> deleteProduct());
    }

    private void showAddProductPanel() {
        add(AgregarProductoPanel);
        remove(scrollPane);
        revalidate();
        repaint();
    }

    private void showProductTable() {
        remove(AgregarProductoPanel);
        add(scrollPane);
        revalidate();
        repaint();
    }

    private void saveProduct() {
        if (validarCampos()) {
            String nombre = textFieldNombre.getText();
            String marca = textFieldMarca.getText();
            double precio = Double.parseDouble(textFieldPrecio.getText());
            int cantidad = Integer.parseInt(textFieldCantidad.getText());

            String query = "INSERT INTO producto (nombre_producto, marca_producto, precio_producto, cantidad_en_inventario) VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nombre);
                stmt.setString(2, marca);
                stmt.setDouble(3, precio);
                stmt.setInt(4, cantidad);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        model.addRow(new Object[]{id, nombre, marca, precio, cantidad});
                        JOptionPane.showMessageDialog(this, "Producto guardado correctamente con ID: " + id);
                        limpiarCampos();
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } 
        }
    }

    private void editProduct() {
        int selectedRow = productos.getSelectedRow();
        if (selectedRow != -1) {
            try {
                // Obtener los valores actuales del producto seleccionado
                int id = (int) model.getValueAt(selectedRow, 0);
                String nombre = (String) model.getValueAt(selectedRow, 1);
                String marca = (String) model.getValueAt(selectedRow, 2);

                // Convertir el precio a double de forma segura
                String precioStr = model.getValueAt(selectedRow, 3).toString();
                double precio = Double.parseDouble(precioStr);

                // Convertir la cantidad a int de forma segura
                String cantidadStr = model.getValueAt(selectedRow, 4).toString();
                int cantidad = Integer.parseInt(cantidadStr);

                // Crear campos de texto con los valores actuales
                JTextField nombreField = new JTextField(nombre);
                JTextField marcaField = new JTextField(marca);
                JTextField precioField = new JTextField(String.valueOf(precio));
                JTextField cantidadField = new JTextField(String.valueOf(cantidad));

                // Crear panel para mostrar los campos en el JOptionPane
                JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
                panel.add(new JLabel("Nombre:"));
                panel.add(nombreField);
                panel.add(new JLabel("Marca:"));
                panel.add(marcaField);
                panel.add(new JLabel("Precio:"));
                panel.add(precioField);
                panel.add(new JLabel("Cantidad:"));
                panel.add(cantidadField);

                // Mostrar el JOptionPane para editar el producto
                int option = JOptionPane.showConfirmDialog(this, panel, "Editar Producto", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    // Validar los campos antes de realizar el cambio
                    if (nombreField.getText().isEmpty() || marcaField.getText().isEmpty() ||
                            precioField.getText().isEmpty() || cantidadField.getText().isEmpty()) {
                        throw new IllegalArgumentException("Todos los campos deben ser completados.");
                    }

                    // Actualizar los valores en la tabla
                    model.setValueAt(nombreField.getText(), selectedRow, 1);
                    model.setValueAt(marcaField.getText(), selectedRow, 2);

                    // Convertir los valores del JOptionPane
                    double precioNuevo = Double.parseDouble(precioField.getText());
                    int cantidadNueva = Integer.parseInt(cantidadField.getText());

                    model.setValueAt(precioNuevo, selectedRow, 3);
                    model.setValueAt(cantidadNueva, selectedRow, 4);

                    // Actualizar el producto en la base de datos
                    String updateQuery = "UPDATE producto SET nombre_producto = ?, marca_producto = ?, precio_producto = ?, cantidad_en_inventario = ? WHERE id_producto = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                        stmt.setString(1, nombreField.getText());
                        stmt.setString(2, marcaField.getText());
                        stmt.setDouble(3, precioNuevo);
                        stmt.setInt(4, cantidadNueva);
                        stmt.setInt(5, id);

                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Error al actualizar el producto en la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto para editar.");
        }
    }

    private void deleteProduct() {
        int selectedRow = productos.getSelectedRow();
        if (selectedRow != -1) {
            int idProducto = (int) model.getValueAt(selectedRow, 0);

            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este producto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                String query = "DELETE FROM producto WHERE id_producto = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, idProducto);
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarCampos() {
        if (textFieldNombre.getText().isEmpty() || textFieldPrecio.getText().isEmpty() || textFieldCantidad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(textFieldPrecio.getText());
            Integer.parseInt(textFieldCantidad.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio y Stock deben ser numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void limpiarCampos() {
    	textFieldNombre.setText("");
    	textFieldMarca.setText("");
    	textFieldPrecio.setText("");
    	textFieldCantidad.setText("");
    }

    private void loadEvents() {
        try {
            model.setRowCount(0);  // Limpiar la tabla antes de cargar nuevos datos
            String query = "SELECT * FROM producto";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("id_producto"),
                    rs.getString("nombre_producto"),
                    rs.getString("marca_producto"),
                    rs.getString("precio_producto"),
                    rs.getString("cantidad_en_inventario")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los eventos: " + e.getMessage());
        }
    }
    
    private int generarNuevoID() {
        return producto.size() + 1;
    }
}