package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import funcionamiento.Producto;

import java.awt.*;
import java.util.ArrayList;

public class InventarioPanel extends JPanel {

    private DefaultTableModel model;
    private JLabel lblProductos;
    private JButton btnNuevo, btnImprimir;
    private JPanel panelNuevo;
    private JButton btnNewButton;
    private JTextField textField, textField_1, textField_2, textField_3;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnNewButton_1;
    private JButton btnNewButton_2;
    static ArrayList<funcionamiento.Producto> producto = new ArrayList<funcionamiento.Producto>();

    public InventarioPanel(JPanel Mainpanel) {

        this.setBackground(Color.WHITE);
        setLayout(null);  // Usamos null layout para manejar la posición de los componentes

        lblProductos = new JLabel("Productos");
        lblProductos.setFont(new Font("Arial", Font.PLAIN, 14));
        lblProductos.setBounds(50, 119, 600, 34);
        add(lblProductos);

        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(50, 85, 89, 23);
        this.add(btnNuevo);

        btnImprimir = new JButton("Imprimir");
        btnImprimir.setBounds(347, 85, 89, 23);
        add(btnImprimir);
        
        btnNewButton_1 = new JButton("Editar ");
        btnNewButton_1.setBounds(149, 85, 89, 23);
        add(btnNewButton_1);
        
        btnNewButton_2 = new JButton("Eliminar");
        btnNewButton_2.setBounds(248, 85, 89, 23);
        add(btnNewButton_2);

        // Inicializamos la tabla
        model = new DefaultTableModel();
        model.addColumn("Nombre");
        model.addColumn("Precio");
        model.addColumn("Marca");
        model.addColumn("Cantidad");

        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 161, 600, 289);  // Establecemos las dimensiones de la tabla
        add(scrollPane);

        // Inicializamos el panelNuevo (formulario)
        panelNuevo = new JPanel();
        panelNuevo.setBounds(50, 164, 600, 300);
        panelNuevo.setBackground(Color.WHITE);
        panelNuevo.setLayout(null);

        JLabel label = new JLabel("Formulario de Nuevo Producto", SwingConstants.CENTER);
        label.setBounds(0, 0, 600, 14);
        panelNuevo.add(label);

        // Botón de Cerrar dentro de panelNuevo
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(0, 277, 321, 23);
        panelNuevo.add(btnCerrar);

        // Botón de Guardar dentro de panelNuevo
        btnNewButton = new JButton("Guardar");
        btnNewButton.setBounds(318, 277, 282, 23);
        panelNuevo.add(btnNewButton);

        // Campos del formulario
        JLabel lblNewLabel = new JLabel("Nombre");
        lblNewLabel.setBounds(20, 85, 49, 14);
        panelNuevo.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Marca ");
        lblNewLabel_1.setBounds(20, 158, 49, 14);
        panelNuevo.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Precio ");
        lblNewLabel_2.setBounds(199, 85, 49, 14);
        panelNuevo.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Cantidad");
        lblNewLabel_3.setBounds(199, 158, 72, 14);
        panelNuevo.add(lblNewLabel_3);

        textField = new JTextField();
        textField.setBounds(20, 99, 137, 32);
        panelNuevo.add(textField);

        textField_1 = new JTextField();
        textField_1.setBounds(20, 172, 137, 32);
        panelNuevo.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setBounds(199, 99, 122, 32);
        panelNuevo.add(textField_2);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(199, 178, 122, 32);
        panelNuevo.add(textField_3);

        // Acción al presionar "Nuevo"
        btnNuevo.addActionListener(e -> {
            scrollPane.setVisible(false); // Eliminar la tabla
            add(panelNuevo); // Mostrar el panel de formulario
            revalidate();
            repaint();
        });

        // Acción al presionar "Cerrar"
        btnCerrar.addActionListener(e -> {
            remove(panelNuevo); // Eliminar el formulario
            scrollPane.setVisible(true); // Volver a mostrar la tabla
            revalidate();
            repaint();
        });
        
      
        btnNewButton.addActionListener(e -> {
            // Obtener los valores de los campos de texto
            String nombre = textField.getText();
            String marca = textField_1.getText();
            double precio = Double.parseDouble(textField_2.getText()); // Convertir el precio a double
            int cantidad = Integer.parseInt(textField_3.getText()); // Convertir la cantidad a int

            // Crear un objeto Producto
            Producto nuevoProducto = new Producto(1, nombre, marca, precio, cantidad); // Asignar un ID único o generarlo dinámicamente

            // Agregar el producto al inventario utilizando el método de la interfaz
            nuevoProducto.agregarproducto(nuevoProducto); 

            // Limpiar los campos después de agregar el producto
            textField.setText("");
            textField_1.setText("");
            textField_2.setText("");
            textField_3.setText("");

            // Opcional: Mostrar el nuevo producto en la tabla
            model.addRow(new Object[]{nuevoProducto.getNombre(), nuevoProducto.getPrecio(), nuevoProducto.getMarca(), nuevoProducto.getCantidad()});
            
        });

    }
}
