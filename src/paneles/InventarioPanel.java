package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InventarioPanel extends JPanel {
	
	private DefaultTableModel model;
	private JTable Productos;
	private JLabel lblProductos;
	private JButton btnNuevo, btnImprimir;
	private JScrollPane scrollPane;
	
    public InventarioPanel() {
    	
        this.setBackground(Color.WHITE);
        setLayout(null);
        
        lblProductos = new JLabel("Productos");
        lblProductos.setFont(new Font("Arial", Font.PLAIN, 14));
        lblProductos.setBounds(50, 119, 600, 34);
        add(lblProductos);
        
        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(50, 85, 89, 23);
        this.add(btnNuevo);
       
        model = new DefaultTableModel();
        model.addColumn("Referencia");
        model.addColumn("Nombre");
        model.addColumn("Medida");
        model.addColumn("Existencia");
        
        Productos = new JTable(model);
        Productos.setForeground(Color.WHITE);
        scrollPane = new JScrollPane(Productos);
        scrollPane.setBounds(50, 150, 600, 300);  
        this.add(scrollPane);
        
        btnImprimir = new JButton("Imprimir");
        btnImprimir.setBounds(149, 85, 89, 23);
        add(btnImprimir);
    }
}