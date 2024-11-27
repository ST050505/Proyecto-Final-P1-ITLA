package paneles;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class ClientePanel extends JPanel {
	private DefaultTableModel model;
	private JTable clientes;
	
    public ClientePanel() {
        this.setBackground(Color.WHITE);
        setLayout(null);
        
        JButton btnNewButton = new JButton("Nuevo ");
        btnNewButton.setBounds(50, 59, 104, 37);
        add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Imprimir");
        btnNewButton_1.setBounds(169, 59, 104, 37);
        add(btnNewButton_1);

        clientes = new JTable();
        clientes.setBounds(74, 248, 1, 1);
        add(clientes);
        
        model = new DefaultTableModel();
        model.addColumn("RNC/Cédula");
        model.addColumn("Nombre");
        model.addColumn("Telefono 1");
        model.addColumn("E-mail");
        model.addColumn("Dirección");
      
        clientes = new JTable(model);
        clientes.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(clientes);
        scrollPane.setBounds(50, 150, 600, 300);  
        this.add(scrollPane);
       
    }
}