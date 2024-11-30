package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import funcionamiento.Cliente;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ClientePanel extends JPanel {
    private DefaultTableModel model;
    private JTable clientes;
    private JButton btnNuevo, btnImprimir, btnGuardar, btnCerrar, btnEditar;
    private JPanel AgregarClientePanel;
    private JTextField textField, textField_1, textField_2, textField_3;
    private JLabel lblNewLabel, lblNewLabel_1, lblNewLabel_2, lblNewLabel_3;
    private JScrollPane scrollPane;
    static ArrayList<Cliente> cliente = new ArrayList<Cliente>();
    private JButton btnEliminar;

    public ClientePanel(JPanel Mainpanel) {
        this.setBackground(Color.WHITE);
        setLayout(null);

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

        clientes = new JTable();
        clientes.setBounds(74, 248, 1, 1);
        add(clientes);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Telefono");
        model.addColumn("Dirección");
        model.addColumn("Cedúla / RNC");

        clientes = new JTable(model);

        scrollPane = new JScrollPane(clientes);
        scrollPane.setBounds(50, 161, 600, 289);  // Establecemos las dimensiones de la tabla
        this.add(scrollPane);

        AgregarClientePanel = new JPanel();
        AgregarClientePanel.setBounds(50, 164, 600, 300);
        AgregarClientePanel.setBackground(Color.WHITE);
        AgregarClientePanel.setLayout(null);

        textField = new JTextField();
        textField.setBounds(84, 47, 145, 30);
        AgregarClientePanel.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(84, 124, 145, 30);
        AgregarClientePanel.add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(327, 47, 130, 30);
        AgregarClientePanel.add(textField_2);
        textField_2.setColumns(10);

        textField_3 = new JTextField();
        textField_3.setBounds(327, 124, 130, 30);
        AgregarClientePanel.add(textField_3);
        textField_3.setColumns(10);

        lblNewLabel = new JLabel("Nombre");
        lblNewLabel.setBounds(84, 22, 112, 14);
        AgregarClientePanel.add(lblNewLabel);

        lblNewLabel_1 = new JLabel("Telefono ");
        lblNewLabel_1.setBounds(84, 99, 96, 14);
        AgregarClientePanel.add(lblNewLabel_1);

        lblNewLabel_2 = new JLabel("Direccion ");
        lblNewLabel_2.setBounds(327, 22, 112, 14);
        AgregarClientePanel.add(lblNewLabel_2);

        lblNewLabel_3 = new JLabel("RNC/Cédula");
        lblNewLabel_3.setBounds(327, 99, 94, 14);
        AgregarClientePanel.add(lblNewLabel_3);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(284, 270, 301, 30);
        AgregarClientePanel.add(btnCerrar);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(0, 270, 263, 30);
        AgregarClientePanel.add(btnGuardar);

        // Acción al presionar "Nuevo"
        btnNuevo.addActionListener(e -> {
            add(AgregarClientePanel);
            remove(scrollPane);
            revalidate();
            repaint();
        });

        btnCerrar.addActionListener(e -> {
            remove(AgregarClientePanel);
            add(scrollPane);
            revalidate();
            repaint();
        });

        // Acción al presionar "Guardar"
        btnGuardar.addActionListener(e -> {
            try {
                // Obtener los valores de los campos de texto
                String nombre = textField.getText();
                String telefono = textField_1.getText();
                String direccion = textField_2.getText();
                String cedula_RNC = textField_3.getText();

                // Crear un objeto Cliente con un ID único
                Cliente nuevoCliente = new Cliente(generarNuevoID(), nombre, telefono, direccion, cedula_RNC);

                // Agregar el cliente al ArrayList
                cliente.add(nuevoCliente);

                // Agregar el cliente a la tabla
                model.addRow(new Object[]{nuevoCliente.getIdCliente(), nuevoCliente.getNombre(),
                        nuevoCliente.getTelefono(), nuevoCliente.getDireccion(), nuevoCliente.getCedula_RNC()});

                // Limpiar los campos después de agregar el cliente
                textField.setText("");
                textField_1.setText("");
                textField_2.setText("");
                textField_3.setText("");

                // Volver a mostrar la tabla
                scrollPane.setVisible(true);

                JOptionPane.showMessageDialog(this, "Cliente agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa valores válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEditar.addActionListener(e -> {
            int selectedRow = clientes.getSelectedRow();
            if (selectedRow != -1) {
                try {
                    int id = (int) model.getValueAt(selectedRow, 0);  // Obtener el ID
                    String nombre = (String) model.getValueAt(selectedRow, 1);
                    String telefono = (String) model.getValueAt(selectedRow, 2);
                    String direccion = (String) model.getValueAt(selectedRow, 3);
                    String cedula_RNC = (String) model.getValueAt(selectedRow, 4);

                    // Usamos JOptionPane para editar
                    JTextField idField = new JTextField(String.valueOf(id));
                    JTextField nombreField = new JTextField(nombre);
                    JTextField telefonoField = new JTextField(telefono);
                    JTextField direccionField = new JTextField(direccion);
                    JTextField cedulaField = new JTextField(cedula_RNC);

                    // Hacer el JOptionPane más grande
                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(5, 2, 10, 10));
                    panel.add(new JLabel("ID:"));
                    panel.add(idField);
                    panel.add(new JLabel("Nombre:"));
                    panel.add(nombreField);
                    panel.add(new JLabel("Telefono:"));
                    panel.add(telefonoField);
                    panel.add(new JLabel("Direccion:"));
                    panel.add(direccionField);
                    panel.add(new JLabel("Cedula/RNC:"));
                    panel.add(cedulaField);

                    JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
                    JDialog dialog = optionPane.createDialog("Editar Cliente");

                    dialog.setSize(600, 300); // Aumentar el tamaño del JOptionPane
                    dialog.setVisible(true);

                    if (optionPane.getValue().equals(JOptionPane.OK_OPTION)) {
                        // Validación de campos no vacíos
                        if (nombreField.getText().isEmpty() || telefonoField.getText().isEmpty() || direccionField.getText().isEmpty() || cedulaField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Todos los campos deben ser completados.");
                        }

                        // Actualizar valores en la tabla
                        model.setValueAt(nombreField.getText(), selectedRow, 1);
                        model.setValueAt(telefonoField.getText(), selectedRow, 2);
                        model.setValueAt(direccionField.getText(), selectedRow, 3);
                        model.setValueAt(cedulaField.getText(), selectedRow, 4);

                        JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente.");
                    }
                } catch (IllegalArgumentException ex) {
                    // Excepción cuando los campos están vacíos
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    // Captura cualquier otro tipo de excepción
                    JOptionPane.showMessageDialog(this, "Ocurrió un error al actualizar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un cliente para editar.");
            }
        });
    }

    // Método para generar un ID único
    private int generarNuevoID() {
        return cliente.size() + 1;
    }
}
