package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import funcionamiento.Cliente;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientePanel extends JPanel {
    private DefaultTableModel model;
    private JTable clientes;
    private JButton btnNuevo, btnImprimir, btnGuardar, btnCerrar, btnEditar, btnEliminar;
    private JPanel AgregarClientePanel;
    private JTextField textField, textField_1, textField_2, textField_3;
    private JLabel lblNewLabel, lblNewLabel_1, lblNewLabel_2, lblNewLabel_3;
    private JScrollPane scrollPane;
    static ArrayList<Cliente> cliente = new ArrayList<>();

    public ClientePanel(JPanel Mainpanel) {
        this.setBackground(Color.WHITE);
        setLayout(null);

        // Inicialización de componentes
        initComponents();
        
        // Configuración de ActionListeners
        configureActionListeners();
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
        model.addColumn("Telefono");
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

        textField = new JTextField();
        textField.setBounds(84, 47, 145, 30);
        AgregarClientePanel.add(textField);

        textField_1 = new JTextField();
        textField_1.setBounds(84, 124, 145, 30);
        AgregarClientePanel.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setBounds(327, 47, 130, 30);
        AgregarClientePanel.add(textField_2);

        textField_3 = new JTextField();
        textField_3.setBounds(327, 124, 130, 30);
        AgregarClientePanel.add(textField_3);

        lblNewLabel = new JLabel("Nombre");
        lblNewLabel.setBounds(84, 22, 112, 14);
        AgregarClientePanel.add(lblNewLabel);

        lblNewLabel_1 = new JLabel("Telefono");
        lblNewLabel_1.setBounds(84, 99, 96, 14);
        AgregarClientePanel.add(lblNewLabel_1);

        lblNewLabel_2 = new JLabel("Direccion");
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
        String nombre = textField.getText();
        String telefono = textField_1.getText();
        String direccion = textField_2.getText();
        String cedulaRNC = textField_3.getText();

        // Validación para asegurarse de que todos los campos estén completos
        if (nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || cedulaRNC.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben ser completados.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear un nuevo cliente con los datos
        Cliente nuevoCliente = new Cliente(generarNuevoID(), nombre, telefono, direccion, cedulaRNC);

        // Guardar el cliente en la lista de clientes
        cliente.add(nuevoCliente);

        // Actualizar la tabla
        model.addRow(new Object[]{nuevoCliente.getIdCliente(), nombre, telefono, direccion, cedulaRNC});
        
        // Limpiar los campos
        textField.setText("");
        textField_1.setText("");
        textField_2.setText("");
        textField_3.setText("");
        
        JOptionPane.showMessageDialog(this, "Cliente guardado exitosamente.");
    }

    private void editClient() {
        int selectedRow = clientes.getSelectedRow();
        if (selectedRow != -1) {
            try {
                // Obtener datos actuales del cliente seleccionado
                int id = (int) model.getValueAt(selectedRow, 0);
                String nombre = (String) model.getValueAt(selectedRow, 1);
                String telefono = (String) model.getValueAt(selectedRow, 2);
                String direccion = (String) model.getValueAt(selectedRow, 3);
                String cedula_RNC = (String) model.getValueAt(selectedRow, 4);

                // Mostrar cuadro de edición
                JTextField idField = new JTextField(String.valueOf(id));
                JTextField nombreField = new JTextField(nombre);
                JTextField telefonoField = new JTextField(telefono);
                JTextField direccionField = new JTextField(direccion);
                JTextField cedulaField = new JTextField(cedula_RNC);

                JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
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

                int option = JOptionPane.showConfirmDialog(this, panel, "Editar Cliente", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    // Validación de campos
                    if (nombreField.getText().isEmpty() || telefonoField.getText().isEmpty()
                            || direccionField.getText().isEmpty() || cedulaField.getText().isEmpty()) {
                        throw new IllegalArgumentException("Todos los campos deben ser completados.");
                    }

                    // Actualizar la fila en la tabla
                    model.setValueAt(nombreField.getText(), selectedRow, 1);
                    model.setValueAt(telefonoField.getText(), selectedRow, 2);
                    model.setValueAt(direccionField.getText(), selectedRow, 3);
                    model.setValueAt(cedulaField.getText(), selectedRow, 4);

                    // Actualizar el cliente en la lista de clientes
                    Cliente clienteEditado = cliente.get(selectedRow);
                    clienteEditado.setNombre(nombreField.getText());
                    clienteEditado.setTelefono(telefonoField.getText());
                    clienteEditado.setDireccion(direccionField.getText());
                    clienteEditado.setCedula_RNC(cedulaField.getText());

                    JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un cliente para editar.");
        }
    }

    private void deleteClient() {
        int selectedRow = clientes.getSelectedRow(); // Obtener la fila seleccionada

        if (selectedRow >= 0) { // Verificar que haya una fila seleccionada
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que deseas eliminar este cliente?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                // Eliminar el cliente de la lista
                cliente.remove(selectedRow);

                // Eliminar la fila de la tabla
                model.removeRow(selectedRow);

                JOptionPane.showMessageDialog(
                        this,
                        "Cliente eliminado correctamente.",
                        "Eliminación exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Por favor, selecciona un cliente para eliminar.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private int generarNuevoID() {
        return cliente.size() + 1;
    }
}

