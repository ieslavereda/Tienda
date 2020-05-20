package principal;


import java.awt.EventQueue;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import control.ControladorPrincipal;
import model.Modelo;
import view.JFramePrincipal;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				// se crean los objetos MODELO Y VISTA
				Modelo modelo = new Modelo();
				JFramePrincipal vista = new JFramePrincipal();
				
				// Para colocar un skin propio de java
				try {
					
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							SwingUtilities.updateComponentTreeUI(vista);
							break;
						}
					}

					new ControladorPrincipal(vista, modelo).go();
					
				} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
					ex.printStackTrace();
				} 
				
			}
		});
	}
}
