package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import funcionamiento.Cliente;
import gui.ConexionDB;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClientePanel extends JPanel {
	
    private DefaultTableModel model;
    private JTable clientes;
    private JButton btnNuevo, btnImprimir, btnGuardar, btnCerrar, btnEditar, btnEliminar;
    private JPanel AgregarClientePanel;
    private JTextField textFieldNombre, textFieldCorreo, textFieldDireccion, textFieldPI;
    private JLabel lblNombre, lblCorreo, lblDireccion, lblPI;
    private JScrollPane scrollPane;
    static ArrayList<Cliente> cliente = new ArrayList<>();
    private Connection conn;

    public ClientePanel(JPanel Mainpanel) {
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
        model.addColumn("Correo");
        model.addColumn("Dirección");
        model.addColumn("Cedúla / RNC");

        clientes = new JTable(model);

        scrollPane = new JScrollPane(clientes);
        scrollPane.setBounds(50, 161, 600, 289);
        this.add(scrollPane);

        // Panel para agregar clientes
        AgregarClientePanel = new JPanel();
        AgregarClientePanel.setBounds(50, 164, 600, 300);
        AgregarClientePanel.setBackground(Color.WHITE);
        AgregarClientePanel.setLayout(null);

        textFieldNombre = new JTextField();
        textFieldNombre.setBounds(84, 47, 145, 30);
        AgregarClientePanel.add(textFieldNombre);

        textFieldCorreo = new JTextField();
        textFieldCorreo.setBounds(84, 124, 145, 30);
        AgregarClientePanel.add(textFieldCorreo);

        textFieldDireccion = new JTextField();
        textFieldDireccion.setBounds(327, 47, 130, 30);
        AgregarClientePanel.add(textFieldDireccion);

        textFieldPI = new JTextField();
        textFieldPI.setBounds(327, 124, 130, 30);
        AgregarClientePanel.add(textFieldPI);

        lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(84, 22, 112, 14);
        AgregarClientePanel.add(lblNombre);

        lblCorreo = new JLabel("Correo");
        lblCorreo.setBounds(84, 99, 96, 14);
        AgregarClientePanel.add(lblCorreo);

        lblDireccion = new JLabel("Direccion");
        lblDireccion.setBounds(327, 22, 112, 14);
        AgregarClientePanel.add(lblDireccion);

        lblPI = new JLabel("RNC/Cédula");
        lblPI.setBounds(327, 99, 94, 14);
        AgregarClientePanel.add(lblPI);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(284, 270, 301, 30);
        AgregarClientePanel.add(btnCerrar);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(0, 270, 263, 30);
        AgregarClientePanel.add(btnGuardar);
    }

    private void configureActionListeners() {
        btnNuevo.addActionListener(e -> showAddClientPanel());
        btnCerrar.addActionListener(e -> showClientTable());
        btnGuardar.addActionListener(e -> saveClient());
        btnEditar.addActionListener(e -> editClient());
        btnEliminar.addActionListener(e -> deleteClient());
    }

    private void showAddClientPanel() {
        add(AgregarClientePanel);
        remove(scrollPane);
        revalidate();
        repaint();
    }

    private void showClientTable() {
        remove(AgregarClientePanel);
        add(scrollPane);
        revalidate();
        repaint();
    }

    private void saveClient() {
        if (validarCampos()) {
            String nombre = textFieldNombre.getText();
            String correo = textFieldCorreo.getText();
            String direccion = textFieldDireccion.getText();
            String PI = textFieldPI.getText();

            String query = "INSERT INTO cliente (nombre_cliente, email, direccion, PI) VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nombre);
                stmt.setString(2, correo);
                stmt.setString(3, direccion);
                stmt.setString(4, PI);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        model.addRow(new Object[]{id, nombre, correo, direccion, PI});
                        JOptionPane.showMessageDialog(this, "Cliente guardado correctamente con ID: " + id);
                        limpiarCampos();
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } 
        }
    }

    private void editClient() {
        int selectedRow = clientes.getSelectedRow();
        if (selectedRow != -1) {
            try {
                // Obtener los valores actuales del cliente seleccionado
                int id = (int) model.getValueAt(selectedRow, 0);
                String nombre = (String) model.getValueAt(selectedRow, 1);
                String correo = (String) model.getValueAt(selectedRow, 2);
                String direccion = (String) model.getValueAt(selectedRow, 3);
                String PI = (String) model.getValueAt(selectedRow, 4);

                // Crear campos de texto con los valores actuales
                JTextField nombreField = new JTextField(nombre);
                JTextField correoField = new JTextField(correo);
                JTextField direccionField = new JTextField(direccion);
                JTextField PIField = new JTextField(PI);

                // Crear panel para mostrar los campos en el JOptionPane
                JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
                panel.add(new JLabel("Nombre:"));
                panel.add(nombreField);
                panel.add(new JLabel("Correo:"));
                panel.add(correoField);
                panel.add(new JLabel("Direccion:"));
                panel.add(direccionField);
                panel.add(new JLabel("PI:"));
                panel.add(PIField);

                // Mostrar el JOptionPane para editar el cliente
                int option = JOptionPane.showConfirmDialog(this, panel, "Editar Cliente", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    // Validar los campos antes de realizar el cambio
                	if (nombreField.getText().isEmpty() || correoField.getText().isEmpty() ||
                            direccionField.getText().isEmpty() || PIField.getText().isEmpty()) {
                        throw new IllegalArgumentException("Todos los campos deben ser completados.");
                    }

                    // Actualizar los valores en la tabla
                    model.setValueAt(nombreField.getText(), selectedRow, 1);
                    model.setValueAt(correoField.getText(), selectedRow, 2);
                    model.setValueAt(direccionField.getText(), selectedRow, 3);
                    model.setValueAt(PIField.getText(), selectedRow, 4);

                    // Actualizar el cleinte en la base de datos
                    String updateQuery = "UPDATE cliente SET nombre_cliente = ?, email = ?, direccion = ?, PI = ? WHERE id_cliente = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                        stmt.setString(1, nombreField.getText());
                        stmt.setString(2, correoField.getText());
                        stmt.setString(3, direccionField.getText());
                        stmt.setString(4, PIField.getText());
                        stmt.setInt(5, id);

                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Error al actualizar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Error al actualizar el cliente en la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un cliente para editar.");
        }
    }

    private void deleteClient() {
        int selectedRow = clientes.getSelectedRow();
        if (selectedRow != -1) {
            int idCliente = (int) model.getValueAt(selectedRow, 0);

            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este cliente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                String query = "DELETE FROM cliente WHERE id_cliente = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, idCliente);
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un cliente para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private boolean validarCampos() {
        if (textFieldNombre.getText().isEmpty() || textFieldCorreo.getText().isEmpty() || textFieldDireccion.getText().isEmpty() || textFieldPI.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void limpiarCampos() {
    	textFieldNombre.setText("");
    	textFieldCorreo.setText("");
    	textFieldDireccion.setText("");
    	textFieldPI.setText("");
    }
    
    private void loadEvents() {
        try {
            model.setRowCount(0);  // Limpiar la tabla antes de cargar nuevos datos
            String query = "SELECT * FROM cliente";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("id_cliente"),
                    rs.getString("nombre_cliente"),
                    rs.getString("email"),
                    rs.getString("direccion"),
                    rs.getString("PI")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los eventos: " + e.getMessage());
        }
    }

    private int generarNuevoID() {
        return cliente.size() + 1;
    }
}