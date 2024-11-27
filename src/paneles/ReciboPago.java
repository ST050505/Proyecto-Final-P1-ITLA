package paneles;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class ReciboPago extends JPanel {
	private DefaultTableModel model;
	private JTable recibos;
	
    public ReciboPago() {
    	
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
        model.addColumn("Código");
        model.addColumn("Fecha");
        model.addColumn("Cuenta CxC");
        model.addColumn("Destinatario");
        model.addColumn("Moneda");
        model.addColumn("Medio de pago");
        model.addColumn("Recibido");
        
        recibos = new JTable(model);
        recibos.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(recibos);
        scrollPane.setBounds(50, 150, 600, 300);  
        this.add(scrollPane);
        
        JButton btnNewButton_1 = new JButton("Imprimir");
        btnNewButton_1.setBounds(149, 85, 89, 23);
        add(btnNewButton_1);
    }
}
