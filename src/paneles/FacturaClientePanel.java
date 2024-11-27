package paneles;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class FacturaClientePanel extends JPanel {
	private DefaultTableModel model;
	private JTable facturas;
	
    public FacturaClientePanel() {
        this.setBackground(Color.WHITE);
        setLayout(null);
        
        JButton btnNewButton = new JButton("Nuevo ");
        btnNewButton.setBounds(50, 59, 104, 37);
        add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Consultar");
        btnNewButton_1.setBounds(169, 59, 104, 37);
        add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("Imprimir");
        btnNewButton_2.setBounds(287, 59, 104, 37);
        add(btnNewButton_2);
        
        JButton btnNewButton_3 = new JButton("Facturar Cotización");
        btnNewButton_3.setBounds(413, 59, 158, 37);
        add(btnNewButton_3);
        
        facturas = new JTable();
        facturas.setBounds(74, 248, 1, 1);
        add(facturas);
        
        model = new DefaultTableModel();
        model.addColumn("Código");
        model.addColumn("Fecha");
        model.addColumn("Cédula");
        model.addColumn("Destinatario");
        model.addColumn("Moneda");
        model.addColumn("Vendedor");
        model.addColumn("Total");
        
        facturas = new JTable(model);
        facturas.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(facturas);
        scrollPane.setBounds(50, 150, 600, 300);  
        this.add(scrollPane);
       
    }
}