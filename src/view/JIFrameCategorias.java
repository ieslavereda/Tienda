/**
 * 
 */
package view;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JButton;

/**
 * Creado el 9 abr. 2019
 * @author <a href="mailto:joaalsai@ieslavereda.es">Joaquin Vicente Alonso Saiz</a>
 *
 */
public class JIFrameCategorias extends JInternalFrame {
	public JTable tableCategorias;
	public JTextField txtFieldDescripcion;
	public JButton btnActualizar;
	public JButton btnEliminar;
	public JButton btnAdd;


	/**
	 * Create the frame.
	 */
	public JIFrameCategorias() {
		setResizable(true);
		setClosable(true);
		setTitle("Categorias");
		setBounds(100, 100, 592, 432);
		
		JPanel panelDetalles = new JPanel();
		
		JPanel panelBotones = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane();
		
		tableCategorias = new JTable();
		scrollPane.setViewportView(tableCategorias);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelDetalles, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
						.addComponent(panelBotones, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 558, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelDetalles, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelBotones, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnAdd = new JButton("Add");
		panelBotones.add(btnAdd);
		
		btnActualizar = new JButton("Update");
		panelBotones.add(btnActualizar);
		
		btnEliminar = new JButton("Delete");
		panelBotones.add(btnEliminar);
		panelDetalles.setLayout(new MigLayout("", "[grow]", "[][][]"));
		
		JLabel lblDescripcionDeLa = new JLabel("Descripcion de la categoria");
		panelDetalles.add(lblDescripcionDeLa, "cell 0 1");
		
		txtFieldDescripcion = new JTextField();
		panelDetalles.add(txtFieldDescripcion, "cell 0 2,growx");
		txtFieldDescripcion.setColumns(10);
		getContentPane().setLayout(groupLayout);

	}
}
