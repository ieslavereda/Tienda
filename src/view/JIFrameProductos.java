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
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import java.awt.Dimension;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;

public class JIFrameProductos extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5178241664842747088L;
	public JTable tableProductos;
	public JTextField txtFieldDescripcion;
	public JTextField txtFieldPVP;
	public JButton btnAdd;
	public JButton btnUpdate;
	public JButton btnDelete;
	public JComboBox comboBoxSeleccionCategoria;
	public JComboBox comboBoxCategoria;
	public JSpinner spinnerExistencias;
	public JComboBox comboBoxSorted;
	private JLabel lblSorted;
	private JLabel lblOrder;
	public JComboBox comboBoxOrder;

	/**
	 * Create the frame.
	 */
	public JIFrameProductos() {
		setMaximizable(true);
		setResizable(true);
		setClosable(true);
		setBounds(100, 100, 768, 643);
		
		JPanel panelSuperior = new JPanel();
		panelSuperior.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		JPanel panelDetalles = new JPanel();
		panelDetalles.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel panelSeleccion = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelSuperior, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
						.addComponent(panelSeleccion, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
						.addComponent(panelDetalles, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
						.addComponent(panelBotones, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelSeleccion, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelSuperior, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelDetalles, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelBotones, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panelSeleccion.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblCategoria_1 = new JLabel("Categoria");
		panelSeleccion.add(lblCategoria_1);
		
		comboBoxSeleccionCategoria = new JComboBox();
		panelSeleccion.add(comboBoxSeleccionCategoria);
		comboBoxSeleccionCategoria.setModel(new DefaultComboBoxModel(new String[] {"All"}));
		
		lblSorted = new JLabel("Sorted");
		panelSeleccion.add(lblSorted);
		
		comboBoxSorted = new JComboBox();
		panelSeleccion.add(comboBoxSorted);
		comboBoxSorted.setModel(new DefaultComboBoxModel(new String[] {"categoriaId", "descArticulo", "pvp", "existencias"}));
		
		lblOrder = new JLabel("Order");
		panelSeleccion.add(lblOrder);
		
		comboBoxOrder = new JComboBox();
		panelSeleccion.add(comboBoxOrder);
		comboBoxOrder.setModel(new DefaultComboBoxModel(new String[] {"ASC", "DESC"}));
		panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnAdd = new JButton("Add");
		panelBotones.add(btnAdd);
		
		btnUpdate = new JButton("Update");
		panelBotones.add(btnUpdate);
		
		btnDelete = new JButton("Delete");
		panelBotones.add(btnDelete);
		panelDetalles.setLayout(new MigLayout("", "[380.00][][147.00][][]", "[][][][]"));
		
		JLabel lblCategoria = new JLabel("Categoria");
		panelDetalles.add(lblCategoria, "cell 0 0");
		
		comboBoxCategoria = new JComboBox();
		panelDetalles.add(comboBoxCategoria, "cell 0 1,growx");
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		panelDetalles.add(lblDescripcion, "cell 0 2");
		
		JLabel lblExistencias = new JLabel("Existencias");
		panelDetalles.add(lblExistencias, "cell 2 2");
		
		JLabel lblNewLabel = new JLabel("P.V.P.");
		panelDetalles.add(lblNewLabel, "cell 4 2");
		
		txtFieldDescripcion = new JTextField();
		panelDetalles.add(txtFieldDescripcion, "cell 0 3,growx");
		txtFieldDescripcion.setColumns(10);
		
		spinnerExistencias = new JSpinner();
		spinnerExistencias.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerExistencias.setPreferredSize(new Dimension(300, 20));
		panelDetalles.add(spinnerExistencias, "cell 2 3");
		
		txtFieldPVP = new JTextField();
		panelDetalles.add(txtFieldPVP, "cell 4 3,growx");
		txtFieldPVP.setColumns(10);
		panelSuperior.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelSuperior.add(scrollPane, BorderLayout.CENTER);
		
		tableProductos = new JTable();
		scrollPane.setViewportView(tableProductos);
		getContentPane().setLayout(groupLayout);

	}
}
