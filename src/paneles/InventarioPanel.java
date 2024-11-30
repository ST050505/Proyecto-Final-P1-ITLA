package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import funcionamiento.Producto;
import java.awt.*;
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

    public InventarioPanel(JPanel Mainpanel) {
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
        try {
            String nombre = textFieldNombre.getText();
            String marca = textFieldMarca.getText();
            double precio = Double.parseDouble(textFieldPrecio.getText());
            int cantidad = Integer.parseInt(textFieldCantidad.getText());

            Producto nuevoProducto = new Producto(generarNuevoID(), nombre, marca, precio, cantidad);
            producto.add(nuevoProducto);

            model.addRow(new Object[]{nuevoProducto.getIdProducto(), nuevoProducto.getNombre(), 
                                       nuevoProducto.getMarca(), nuevoProducto.getPrecio(), 
                                       nuevoProducto.getCantidad()});

            textFieldNombre.setText("");
            textFieldMarca.setText("");
            textFieldPrecio.setText("");
            textFieldCantidad.setText("");

            JOptionPane.showMessageDialog(this, "Producto agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa valores válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editProduct() {
        int selectedRow = productos.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int id = (int) model.getValueAt(selectedRow, 0);
                String nombre = (String) model.getValueAt(selectedRow, 1);
                String marca = (String) model.getValueAt(selectedRow, 2);
                double precio = (double) model.getValueAt(selectedRow, 3);
                int cantidad = (int) model.getValueAt(selectedRow, 4);

                JTextField nombreField = new JTextField(nombre);
                JTextField marcaField = new JTextField(marca);
                JTextField precioField = new JTextField(String.valueOf(precio));
                JTextField cantidadField = new JTextField(String.valueOf(cantidad));

                JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
                panel.add(new JLabel("Nombre:"));
                panel.add(nombreField);
                panel.add(new JLabel("Marca:"));
                panel.add(marcaField);
                panel.add(new JLabel("Precio:"));
                panel.add(precioField);
                panel.add(new JLabel("Cantidad:"));
                panel.add(cantidadField);

                int option = JOptionPane.showConfirmDialog(this, panel, "Editar Producto", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    if (nombreField.getText().isEmpty() || marcaField.getText().isEmpty() ||
                            precioField.getText().isEmpty() || cantidadField.getText().isEmpty()) {
                        throw new IllegalArgumentException("Todos los campos deben ser completados.");
                    }

                    model.setValueAt(nombreField.getText(), selectedRow, 1);
                    model.setValueAt(marcaField.getText(), selectedRow, 2);
                    model.setValueAt(Double.parseDouble(precioField.getText()), selectedRow, 3);
                    model.setValueAt(Integer.parseInt(cantidadField.getText()), selectedRow, 4);

                    JOptionPane.showMessageDialog(this, "Producto actualizado exitosamente.");
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
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que deseas eliminar este producto?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                producto.remove(selectedRow);
                model.removeRow(selectedRow);

                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private int generarNuevoID() {
        return producto.size() + 1;
    }
}
