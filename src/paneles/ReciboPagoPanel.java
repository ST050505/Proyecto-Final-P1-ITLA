package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReciboPagoPanel extends JPanel {
	
	private DefaultTableModel model;
	private JTable recibos;
	private JLabel lblProductos;
	private JButton btnNuevo, btnImprimir;
	private JScrollPane scrollPane;
	
    public ReciboPagoPanel(JPanel Mainpanel) {
    	
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
        model.addColumn("Código");
        model.addColumn("Fecha");
        model.addColumn("Cuenta CxC");
        model.addColumn("Destinatario");
        model.addColumn("Moneda");
        model.addColumn("Medio de pago");
        model.addColumn("Recibido");
        
        recibos = new JTable(model);
        recibos.setForeground(Color.WHITE);
        scrollPane = new JScrollPane(recibos);
        scrollPane.setBounds(50, 150, 600, 300);  
        this.add(scrollPane);
        
        btnImprimir = new JButton("Imprimir");
        btnImprimir.setBounds(149, 85, 89, 23);
        add(btnImprimir);
    }
}