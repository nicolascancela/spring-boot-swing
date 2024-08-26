package com.spring.swing.view;

import com.spring.swing.domain.Usuario;
import com.spring.swing.service.UsuariosService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

@Component
public class VistaPrincipal extends JFrame {
    private final UsuariosService usuariosService;

    private JPanel panelPrincipal;
    private JPanel panelUsuarios;

    private List<Usuario> listaUsuarios = new ArrayList<>();

    public VistaPrincipal(UsuariosService usuariosService){
        this.usuariosService = usuariosService;

        cargarEstiloWindows();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(760, 320);
        setTitle("Mi App");
        setResizable(false);
        setLocationRelativeTo(null);
        crearControles();
    }

    private void cargarEstiloWindows(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        private void crearControles() {
            panelPrincipal = new JPanel();
            panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

            setContentPane(panelPrincipal);
            panelPrincipal.setLayout(null);

            JLabel lblNewLabel = new JLabel("Prueba integraci\u00F3n Swing con Spring Boot");
            lblNewLabel.setBounds(10, 40, 215, 20);
            panelPrincipal.add(lblNewLabel);

            JButton btnNewButton = new JButton("Cerrar");
            btnNewButton.setBounds(584, 227, 146, 23);
            panelPrincipal.add(btnNewButton);

            panelUsuarios = new JPanel();
            panelUsuarios.setLayout(new BoxLayout(panelUsuarios, BoxLayout.Y_AXIS));

            JScrollPane scrollPane = new JScrollPane(panelUsuarios);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setBounds(10, 71, 720, 147);
            panelPrincipal.add(scrollPane);
            dibujarBarrarMenu();

            btnNewButton.addActionListener(event -> {
                if (event.getSource() == btnNewButton) System.exit(0);
            });

            panelPrincipal.add(scrollPane);
            JLabel lblNewLabel_1 = new JLabel("Gesti\u00F3n de usuarios");
            lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblNewLabel_1.setBounds(10, 11, 125, 14);
            panelPrincipal.add(lblNewLabel_1);
    }

    private void cargarDatos() {
        listaUsuarios = usuariosService.obtenerUsuarios();
    }

    private void dibujarUsuarios() {
        cargarDatos();
        panelUsuarios.removeAll();

        for (Usuario persona : listaUsuarios) {
            String nombreCompleto = persona.getNombre() + " " + persona.getApellido();
            JLabel lblPersona = new JLabel(nombreCompleto);
            panelUsuarios.add(lblPersona);
        }

        panelUsuarios.revalidate();
        panelUsuarios.repaint();
    }

    private void dibujarBarrarMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemNuevo = new JMenuItem("Nuevo usuario");
        JMenuItem itemActualizar = new JMenuItem("Actualizar datos usuarios");
        JMenuItem itemCargarPredeterminados = new JMenuItem("Sumar usuarios de muestra");
        JMenuItem itemBorrarTodosLosUsuarios = new JMenuItem("Borrar usuarios");
        JMenuItem itemSalir = new JMenuItem("Salir");

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de");

        menuArchivo.add(itemNuevo);
        menuArchivo.add(itemActualizar);
        menuArchivo.add(itemCargarPredeterminados);
        menuArchivo.addSeparator();
        menuArchivo.add(itemBorrarTodosLosUsuarios);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);

        menuAyuda.add(itemAcercaDe);
        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);

        itemCargarPredeterminados.addActionListener(event -> {
                usuariosService.cargarListaPredefinida();
                dibujarUsuarios();
            }
        );

        itemAcercaDe.addActionListener(event -> mostrarAcercaDe());

        itemBorrarTodosLosUsuarios.addActionListener(event -> {
                usuariosService.borrarTodosLosUsuarios();
                dibujarUsuarios();
            }
        );

        itemSalir.addActionListener(event -> System.exit(0));
        itemActualizar.addActionListener(event -> dibujarUsuarios());
        itemNuevo.addActionListener(event -> crearNuevoUsuario());
        setJMenuBar(menuBar);
    }

    private void mostrarAcercaDe() {
        JOptionPane.showMessageDialog(this,
                "Demo SpringBoot con Swing",
                "Acerca de",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarMensajeError(Exception excepcion) {
        JOptionPane.showMessageDialog(this,
                excepcion.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void crearNuevoUsuario(){
        String nombreYApellido = JOptionPane.showInputDialog(this.panelPrincipal, "Ingrese nombre y apellido:", "Crear nuevo usuario", JOptionPane.QUESTION_MESSAGE);
        try{
            if(nombreYApellido != null){
                usuariosService.crearNuevoUsuario(nombreYApellido);
                cargarDatos();
                dibujarUsuarios();
            }

        } catch (Exception excepcion){
            mostrarMensajeError(excepcion);
        }
    }
}
