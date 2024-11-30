package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import funcionamiento.Producto;
import java.awt.*;
import java.util.ArrayList;

public class InventarioPanel extends JPanel {

    private DefaultTableModel model;
    private JLabel lblProductos;
    private JButton btnNuevo, btnImprimir, btnEditar, btnEliminar, btnCerrar,
    btnGuardar, btnGuardarEditar, btnGuardarCerrar;
    private JPanel panelNuevo;
    private JTextField textField, textField_1, textField_2, textField_3;
    private JTable table;
    private JScrollPane scrollPane;
    static ArrayList<Producto> producto = new ArrayList<>();
    private JPanel editarPanel;

    @SuppressWarnings("serial")
	public InventarioPanel(JPanel Mainpanel) {
        this.setBackground(Color.WHITE);
        setLayout(null);  

        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(50, 74, 89, 34);
        add(btnNuevo);

        btnImprimir = new JButton("Imprimir");
        btnImprimir.setBounds(347, 74, 89, 34);
        add(btnImprimir);

        btnEditar = new JButton("Editar");
        btnEditar.setBounds(149, 74, 89, 34);
        add(btnEditar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(248, 74, 89, 34);
        add(btnEliminar);
        
        // Inicializamos la tabla y el modelo correctamente
        model = new DefaultTableModel(new Object[]{"Nombre", "Precio", "Marca", "Cantidad"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No permitir edición directa en la tabla
            }
        };

        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 161, 600, 289);  // Establecemos las dimensiones de la tabla
        add(scrollPane);

        // Inicializamos el panelNuevo (formulario)
        panelNuevo = new JPanel();
        panelNuevo.setBounds(50, 164, 600, 300);
        panelNuevo.setBackground(Color.WHITE);
        panelNuevo.setLayout(null);

        // Botón de Cerrar dentro de panelNuevo
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(279, 277, 321, 23);
        panelNuevo.add(btnCerrar);

        // Botón de Guardar dentro de panelNuevo
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(0, 277, 302, 23);
        panelNuevo.add(btnGuardar);

        // Campos del formulario
        JLabel lblNewLabel = new JLabel("Nombre");
        lblNewLabel.setBounds(20, 27, 49, 14);
        panelNuevo.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Marca ");
        lblNewLabel_1.setBounds(20, 114, 49, 14);
        panelNuevo.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Precio ");
        lblNewLabel_2.setBounds(211, 27, 49, 14);
        panelNuevo.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Cantidad");
        lblNewLabel_3.setBounds(211, 114, 72, 14);
        panelNuevo.add(lblNewLabel_3);

        textField = new JTextField();
        textField.setBounds(20, 52, 137, 32);
        panelNuevo.add(textField);

        textField_1 = new JTextField();
        textField_1.setBounds(20, 139, 137, 32);
        panelNuevo.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setBounds(211, 52, 122, 32);
        panelNuevo.add(textField_2);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(211, 139, 122, 32);
        panelNuevo.add(textField_3);

        // Acción al presionar "Nuevo"
        btnNuevo.addActionListener(e -> {
        	remove(scrollPane); // Ocultar la tabla
            add(panelNuevo); // Mostrar el panel de formulario
            revalidate();
            repaint();
        });

        // Acción al presionar "Cerrar"
        btnCerrar.addActionListener(e -> {
            remove(panelNuevo); // Eliminar el formulario
            add(scrollPane);      // Volver a mostrar la tabla
            revalidate();
            repaint();
        });

        // Acción al presionar "Guardar"
        btnGuardar.addActionListener(e -> {
            try {
                // Obtener los valores de los campos de texto
                String nombre = textField.getText();
                String marca = textField_1.getText();
                double precio = Double.parseDouble(textField_2.getText()); // Convertir el precio a double
                int cantidad = Integer.parseInt(textField_3.getText()); // Convertir la cantidad a int

                // Crear un objeto Producto con un ID único
                Producto nuevoProducto = new Producto(generarNuevoID(), nombre, marca, precio, cantidad);

                // Agregar el producto al ArrayList
                producto.add(nuevoProducto);

                // Agregar el producto a la tabla
                model.addRow(new Object[]{nuevoProducto.getNombre(), nuevoProducto.getPrecio(), 
                		nuevoProducto.getMarca(), nuevoProducto.getCantidad()});

                // Limpiar los campos después de agregar el producto
                textField.setText("");
                textField_1.setText("");
                textField_2.setText("");
                textField_3.setText("");

                // Volver a mostrar la tabla
                scrollPane.setVisible(true);

                JOptionPane.showMessageDialog(this, "Producto agregado exitosamente.", "Éxito", 
                		JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa valores válidos.", "Error", 
                		JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnEliminar.addActionListener(e -> { // Botón "Eliminar"
            int selectedRow = table.getSelectedRow(); // Obtener la fila seleccionada

            if (selectedRow >= 0) { // Verificar que haya una fila seleccionada
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "¿Estás seguro de que deseas eliminar este producto?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    // Eliminar el producto del ArrayList
                    producto.remove(selectedRow);

                    // Eliminar la fila de la tabla
                    model.removeRow(selectedRow);

                    JOptionPane.showMessageDialog(
                            this,
                            "Producto eliminado correctamente.",
                            "Eliminación exitosa",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Por favor, selecciona un producto para eliminar.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });
        
        
        btnEditar.addActionListener(e -> {
            int selectedRow = table.getSelectedRow(); // Obtener la fila seleccionada

            if (selectedRow >= 0) { // Verificar si hay una fila seleccionada
                try {
                    // Obtener el producto seleccionado
                    Producto productoSeleccionado = producto.get(selectedRow);

                    // Crear campos de texto para editar los valores del producto
                    JTextField nombreField = new JTextField(productoSeleccionado.getNombre());
                    JTextField marcaField = new JTextField(productoSeleccionado.getMarca());
                    JTextField precioField = new JTextField(String.valueOf(productoSeleccionado.getPrecio()));
                    JTextField cantidadField = new JTextField(String.valueOf(productoSeleccionado.getCantidad()));

                    // Configurar el formulario de edición en un panel
                    JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
                    panel.add(new JLabel("Nombre:"));
                    panel.add(nombreField);
                    panel.add(new JLabel("Marca:"));
                    panel.add(marcaField);
                    panel.add(new JLabel("Precio:"));
                    panel.add(precioField);
                    panel.add(new JLabel("Cantidad:"));
                    panel.add(cantidadField);

                    // Configurar el JOptionPane en un JDialog con tamaño personalizado
                    JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
                    JDialog dialog = optionPane.createDialog("Editar Producto");

                    dialog.setSize(600, 300); // Establecer el tamaño del diálogo
                    dialog.setVisible(true);  // Mostrar el diálogo

                    if (optionPane.getValue() != null && optionPane.getValue().equals(JOptionPane.OK_OPTION)) {
                        // Validar y actualizar el producto
                        String nuevoNombre = nombreField.getText();
                        String nuevaMarca = marcaField.getText();
                        double nuevoPrecio = Double.parseDouble(precioField.getText());
                        int nuevaCantidad = Integer.parseInt(cantidadField.getText());

                        // Actualizar el objeto Producto
                        productoSeleccionado.setNombre(nuevoNombre);
                        productoSeleccionado.setMarca(nuevaMarca);
                        productoSeleccionado.setPrecio(nuevoPrecio);
                        productoSeleccionado.setCantidad(nuevaCantidad);

                        // Actualizar la tabla
                        model.setValueAt(nuevoNombre, selectedRow, 0);
                        model.setValueAt(nuevaMarca, selectedRow, 1);
                        model.setValueAt(nuevoPrecio, selectedRow, 2);
                        model.setValueAt(nuevaCantidad, selectedRow, 3);

                        JOptionPane.showMessageDialog(this, "Producto actualizado exitosamente.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Por favor, ingresa valores válidos para el precio y la cantidad.",
                        "Error de Formato",
                        JOptionPane.ERROR_MESSAGE
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Ocurrió un error al editar el producto: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto para editar.");
            }
        });


}
    // Método para generar un ID único
    private int generarNuevoID() {
        return producto.size() + 1;
    }
}