package login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import gui.ConexionDB;
import gui.Gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {

    private JPanel contentPane;
    private JTextField username;
    private JPasswordField password;
    private JLabel lblUsuario, lblPassword;
    private JButton btnAcceder;
    private JLabel lblSK, lblTech;

    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 530);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(54, 57, 63));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblUsuario = new JLabel("USUARIO");
        lblUsuario.setFont(new Font("Verdana", Font.BOLD, 14));
        lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsuario.setBounds(25, 245, 80, 20);
        contentPane.add(lblUsuario);

        username = new JTextField();
        username.setBounds(25, 265, 320, 30);
        contentPane.add(username);
        username.setColumns(10);

        lblPassword = new JLabel("CONTRASEÑA");
        lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblPassword.setFont(new Font("Verdana", Font.BOLD, 14));
        lblPassword.setBounds(25, 315, 120, 20);
        contentPane.add(lblPassword);

        password = new JPasswordField();
        password.setBounds(25, 335, 320, 30);
        contentPane.add(password);

        btnAcceder = new JButton("ACCEDER");
        btnAcceder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        btnAcceder.setFont(new Font("Verdana", Font.BOLD, 14));
        btnAcceder.setBounds(125, 415, 130, 30);
        contentPane.add(btnAcceder);
        
        lblTech = new JLabel("TECH");
        lblTech.setFont(new Font("Verdana", Font.PLAIN, 24));
        lblTech.setHorizontalAlignment(SwingConstants.CENTER);
        lblTech.setBounds(145, 160, 80, 30);
        contentPane.add(lblTech);
        
        lblSK = new JLabel("SK");
        lblSK.setFont(new Font("Arial", Font.PLAIN, 99));
        lblSK.setHorizontalAlignment(SwingConstants.CENTER);
        lblSK.setBounds(110, 60, 150, 100);
        contentPane.add(lblSK);
        
        setLocationRelativeTo(null);
        setTitle("Inicio de sesión");
    }

    private void login() {
        String user = username.getText();
        String pass = new String(password.getPassword());
        
        // Validar campos vacíos antes de continuar
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.");
            return;
        }

        String sql = "SELECT * FROM user WHERE user = ? AND password = ?";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, user);
            pst.setString(2, pass);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Login exitoso");	
					Gui gui = new Gui();
					dispose();
                } else {
                	ConexionDB.closeConnection(con);
                    JOptionPane.showMessageDialog(null, 
                    		"Login denegado, credenciales invalidas",
                    		"Error en las credenciales",
                    		JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(null, 
                    		"El programa se cerrará. Vuelva a abrirlo y coloque las credenciales correctas", 
                    	    "Error en las credenciales", 
                    	    JOptionPane.WARNING_MESSAGE);
                    dispose();
                }
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
        	JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }
}