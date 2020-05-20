/**
 * 
 */
package view;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;

/**
 * Creado el 25 mar. 2019
 * @author <a href="mailto:joaalsai@ieslavereda.es">Joaquin Vicente Alonso Saiz</a>
 *
 */
public class JIFrameListado extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -950812163525334662L;
	public JTable tableUsuarios;
	public JButton btnEliminar;
	public JButton btnModificar;

	/**
	 * Create the frame.
	 */
	public JIFrameListado() {
		setResizable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(100, 100, 573, 300);
		
		JPanel panelSuperior = new JPanel();
		
		JPanel panelInferior = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelInferior, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
						.addComponent(panelSuperior, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelSuperior, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelInferior, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panelInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnModificar = new JButton("Editar");
		panelInferior.add(btnModificar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setHorizontalAlignment(SwingConstants.RIGHT);
		panelInferior.add(btnEliminar);
		panelSuperior.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelSuperior.add(scrollPane, BorderLayout.CENTER);
		
		tableUsuarios = new JTable();
		tableUsuarios.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(tableUsuarios);
		getContentPane().setLayout(groupLayout);

	}
}
