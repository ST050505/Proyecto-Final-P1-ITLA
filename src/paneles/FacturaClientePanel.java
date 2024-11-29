package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FacturaClientePanel extends JPanel {
	
	private DefaultTableModel model;
	private JTable facturas;
	private JButton btnNuevo, btnConsultar, btnImprimir, btnFacCot;
	private JScrollPane scrollPane;
	
    public FacturaClientePanel(JPanel Mainpanel) {
        this.setBackground(Color.WHITE);
        setLayout(null);
        
        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(50, 59, 104, 37);
        add(btnNuevo);
        
        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(169, 59, 104, 37);
        add(btnConsultar);
        
        btnImprimir = new JButton("Imprimir");
        btnImprimir.setBounds(287, 59, 104, 37);
        add(btnImprimir);
        
        btnFacCot = new JButton("Facturar Cotización");
        btnFacCot.setBounds(413, 59, 158, 37);
        add(btnFacCot);
        
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
        scrollPane = new JScrollPane(facturas);
        scrollPane.setBounds(50, 150, 600, 300);  
        this.add(scrollPane);
    }
}