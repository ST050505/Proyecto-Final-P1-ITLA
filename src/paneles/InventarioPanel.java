package paneles;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class InventarioPanel extends JPanel {
	private DefaultTableModel model;
	private JTable Productos;
	
    public InventarioPanel() {
    	
        this.setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Productos");
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNewLabel.setBounds(50, 119, 600, 34);
        add(lblNewLabel);
        
        JButton btnNewButton = new JButton("Nuevo");
        btnNewButton.setBounds(50, 85, 89, 23);
        this.add(btnNewButton);
       
        model = new DefaultTableModel();
        model.addColumn("Referencia");
        model.addColumn("Nombre");
        model.addColumn("Medida");
        model.addColumn("Existencia");
        
        Productos = new JTable(model);
        Productos.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(Productos);
        scrollPane.setBounds(50, 150, 600, 300);  
        this.add(scrollPane);
        
        JButton btnNewButton_1 = new JButton("Imprimir");
        btnNewButton_1.setBounds(149, 85, 89, 23);
        add(btnNewButton_1);
        
        System.out.print("algo mas");
    }
}
