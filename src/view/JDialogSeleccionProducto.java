package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Categoria;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import java.awt.Dimension;
import javax.swing.SpinnerNumberModel;

public class JDialogSeleccionProducto extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4409354552270858241L;
	private final JPanel contentPanel = new JPanel();
	public JComboBox<Object> cbCategoria;
	public JComboBox<String> cbOrdenar;
	public JComboBox<String> cbSentido;
	public JButton okButton;
	public JButton cancelButton;
	public JTable tableProductos;
	public JSpinner spinnerCantidad;

	/**
	 * Create the dialog.
	 */
	public JDialogSeleccionProducto() {
		setModal(true);
		setTitle("Seleccione el producto");
		setBounds(100, 100, 593, 484);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JPanel panel = new JPanel();
		
		JPanel panel_1 = new JPanel();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
				.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(3))
		);
		panel_1.setLayout(new MigLayout("", "[grow][][][][][][][][][][][][][][][60.00]", "[]"));
		
		JLabel lblCantidad = new JLabel("Cantidad");
		panel_1.add(lblCantidad, "cell 14 0");
		
		spinnerCantidad = new JSpinner();
		spinnerCantidad.setModel(new SpinnerNumberModel(new Float(1), null, null, new Float(1)));
		spinnerCantidad.setPreferredSize(new Dimension(60, 28));
		panel_1.add(spinnerCantidad, "cell 15 0");
		
		tableProductos = new JTable();
		scrollPane.setViewportView(tableProductos);
		panel.setLayout(new MigLayout("", "[][grow][][grow][][grow]", "[]"));
		
		JLabel lblCategoria = new JLabel("Categoria");
		panel.add(lblCategoria, "cell 0 0,alignx trailing");
		
		cbCategoria = new JComboBox<Object>();
		cbCategoria.setModel(new DefaultComboBoxModel(new String[] {"All"}));
		panel.add(cbCategoria, "cell 1 0,growx");
		
		JLabel lblOrdenarPor = new JLabel("Ordenar por:");
		panel.add(lblOrdenarPor, "cell 2 0,alignx trailing");
		
		cbOrdenar = new JComboBox<String>();
		cbOrdenar.setModel(new DefaultComboBoxModel<String>(new String[] {"id", "descArticulo", "existencias", "pvp", "categoriaId"}));
		panel.add(cbOrdenar, "cell 3 0,growx");
		
		JLabel lblSentido = new JLabel("Sentido");
		panel.add(lblSentido, "cell 4 0,alignx trailing");
		
		cbSentido = new JComboBox<String>();
		cbSentido.setModel(new DefaultComboBoxModel<String>(new String[] {"ASC", "DESC"}));
		panel.add(cbSentido, "cell 5 0,growx");
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
