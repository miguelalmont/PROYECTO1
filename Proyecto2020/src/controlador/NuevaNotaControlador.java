/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.Articulo;
import modelo.Libro;
import modelo.LibrosJDBC;
import modelo.Nota;
import modelo.NotasJDBC;
import vista.MenuLibro;
import vista.NuevaNota;


/**
 *
 * @author migue
 */
public class NuevaNotaControlador implements ActionListener{
    
        /** instancia a nuestra interfaz de usuario*/
    NuevaNota vista ;
    Nota nota = new Nota();
    NotasJDBC notaConn = new NotasJDBC();
    Libro libro = new Libro();
    LibrosJDBC libroConn = new LibrosJDBC();
    Articulo articulo = new Articulo();
    
    
    /** instancia a nuestro modelo */
    public boolean fromLibro = false;
    public boolean fromArticulo = false;
    

    /** Se declaran en un ENUM las acciones que se realizan desde la
     * interfaz de usuario VISTA y posterior ejecución desde el controlador
     */
    public enum AccionMVC
    {
        __INTRODUCIR_NOTA,
        __CANCELAR
    }

    /** Constrcutor de clase
     * @param vista Instancia de clase interfaz
     */
    public NuevaNotaControlador( NuevaNota vista )
    {
        this.vista = vista;
    }

    /** Inicia el skin y las diferentes variables que se utilizan */
    public void iniciar()
    {
        // Skin tipo WINDOWS
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vista);
            
            vista.setVisible(true);
            HomeControlador.vista.setEnabled(false);
            HomeControlador.mLib.vista.setEnabled(false);
            
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {}

        //declara una acción y añade un escucha al evento producido por el componente
        this.vista.__INTRODUCIR_NOTA.setActionCommand( "__INTRODUCIR_NOTA" );
        this.vista.__INTRODUCIR_NOTA.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vista.__CANCELAR.setActionCommand( "__CANCELAR" );
        this.vista.__CANCELAR.addActionListener(this);
        
        
    }

    //Control de eventos de los controles que tienen definido un "ActionCommand"
    public void actionPerformed(ActionEvent e) {

        switch (AccionMVC.valueOf(e.getActionCommand())) {
            case __INTRODUCIR_NOTA:
                if (this.vista.temaBox.getText().length() == 0 || this.vista.contenidoArea.getText().length() == 0) {

                    JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios.");
                } else {
                    if (fromLibro){
                        nota.setId(0);
                        nota.setTema(this.vista.temaBox.getText());
                        nota.setContenido(this.vista.contenidoArea.getText());
                        nota.setIdLibro(libroConn.getId(MenuLibro.isbnBox.getText())); 
                        nota.setIdArticulo(0);
                        
                        notaConn.insertNotaLibro(nota); 
                    }
                    if (fromArticulo){  
                        nota.setId(0);
                        nota.setTema(this.vista.temaBox.getText());
                        nota.setContenido(this.vista.contenidoArea.getText());
                        nota.setIdLibro(libroConn.getId(MenuLibro.isbnBox.getText())); 
                        nota.setIdArticulo(0);
                        
                        notaConn.insertNotaArticulo(nota);
                        }
                clean();
                this.vista.dispose();
                HomeControlador.vista.toFront();
                HomeControlador.mLib.vista.toFront();
                HomeControlador.mLib.vista.setEnabled(true);
                HomeControlador.vista.setEnabled(true);
                }
                
                break;
            case __CANCELAR:
                
                clean();
                this.vista.dispose();
                HomeControlador.vista.toFront();
                HomeControlador.mLib.vista.toFront();
                HomeControlador.mLib.vista.setEnabled(true);
                HomeControlador.vista.setEnabled(true);
                break;
        }
    }
    
    private void clean() {
        
        this.vista.temaBox.setText("");
        this.vista.contenidoArea.setText("");
    }
}
