package paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FacturaClientePanel extends JPanel {

    private DefaultTableModel model;
    private JTable facturas;
    private JButton btnNuevo, btnCerrar, btnGuardar;
    private JPanel FacturarPanel;
    private JScrollPane scrollPane;

    // Campos de texto
    private JTextField textFieldCliente, textFieldCedula, textFieldMonto, textFieldTipoIngreso, textFieldMoneda, textFieldFecha;

    public FacturaClientePanel(JPanel Mainpanel) {
        initComponents();
        configureActionListeners();
    }

    private void initComponents() {
        this.setBackground(Color.WHITE);
        setLayout(null);

        // Botones principales
        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(50, 59, 104, 37);
        add(btnNuevo);

        // Tabla para mostrar facturas
        model = new DefaultTableModel();
        model.addColumn("Cliente");
        model.addColumn("Cédula / RNC");
        model.addColumn("Tipo de Ingreso");
        model.addColumn("Monto");
        model.addColumn("Moneda");
        model.addColumn("Fecha Emisión");

        facturas = new JTable(model);
        scrollPane = new JScrollPane(facturas);
        scrollPane.setBounds(50, 161, 600, 289);
        add(scrollPane);

        // Panel de facturación
        FacturarPanel = new JPanel();
        FacturarPanel.setBounds(50, 107, 632, 401);
        FacturarPanel.setLayout(null);
        FacturarPanel.setVisible(false); // Oculto inicialmente
        add(FacturarPanel);

        // Campos de entrada en el FacturarPanel
        textFieldCliente = new JTextField();
        textFieldCliente.setBounds(43, 75, 117, 31);
        FacturarPanel.add(textFieldCliente);

        JLabel lblCliente = new JLabel("Cliente");
        lblCliente.setBounds(45, 59, 49, 14);
        FacturarPanel.add(lblCliente);

        textFieldCedula = new JTextField();
        textFieldCedula.setBounds(43, 155, 117, 31);
        FacturarPanel.add(textFieldCedula);

        JLabel lblCedula = new JLabel("Cédula / RNC");
        lblCedula.setBounds(45, 124, 115, 20);
        FacturarPanel.add(lblCedula);

        textFieldMonto = new JTextField();
        textFieldMonto.setBounds(360, 75, 142, 31);
        FacturarPanel.add(textFieldMonto);

        JLabel lblMonto = new JLabel("Monto");
        lblMonto.setBounds(360, 59, 96, 14);
        FacturarPanel.add(lblMonto);

        textFieldTipoIngreso = new JTextField();
        textFieldTipoIngreso.setBounds(209, 75, 115, 31);
        FacturarPanel.add(textFieldTipoIngreso);

        JLabel lblTipoIngreso = new JLabel("Tipo de ingreso");
        lblTipoIngreso.setBounds(209, 59, 96, 14);
        FacturarPanel.add(lblTipoIngreso);

        textFieldMoneda = new JTextField();
        textFieldMoneda.setBounds(209, 155, 115, 31);
        FacturarPanel.add(textFieldMoneda);

        JLabel lblMoneda = new JLabel("Moneda");
        lblMoneda.setBounds(209, 127, 96, 14);
        FacturarPanel.add(lblMoneda);

        textFieldFecha = new JTextField();
        textFieldFecha.setBounds(360, 155, 142, 31);
        FacturarPanel.add(textFieldFecha);

        JLabel lblFechaEmision = new JLabel("Fecha emisión");
        lblFechaEmision.setBounds(360, 127, 96, 14);
        FacturarPanel.add(lblFechaEmision);

        // Botones en el FacturarPanel
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(0, 378, 324, 23);
        FacturarPanel.add(btnGuardar);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(331, 378, 301, 23);
        FacturarPanel.add(btnCerrar);
    }

    private void configureActionListeners() {
        btnNuevo.addActionListener(e -> togglePanels(true));
        btnCerrar.addActionListener(e -> togglePanels(false));

        btnGuardar.addActionListener(e -> {
            // Obtener los valores de los campos
            String cliente = textFieldCliente.getText();
            String cedula = textFieldCedula.getText();
            String tipoIngreso = textFieldTipoIngreso.getText();
            String monto = textFieldMonto.getText();
            String moneda = textFieldMoneda.getText();
            String fecha = textFieldFecha.getText();

            // Agregar fila al modelo de la tabla
            model.addRow(new Object[]{cliente, cedula, tipoIngreso, monto, moneda, fecha});

            // Limpiar los campos
            textFieldCliente.setText("");
            textFieldCedula.setText("");
            textFieldTipoIngreso.setText("");
            textFieldMonto.setText("");
            textFieldMoneda.setText("");
            textFieldFecha.setText("");

            // Alternar a la tabla
            togglePanels(false);
        });
    }

    private void togglePanels(boolean showFacturarPanel) {
        FacturarPanel.setVisible(showFacturarPanel);
        scrollPane.setVisible(!showFacturarPanel);
        revalidate();
        repaint();
    }
    
    
}
