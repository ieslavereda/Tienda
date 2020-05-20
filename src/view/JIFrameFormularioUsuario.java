package view;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import com.github.lgooddatepicker.components.DatePicker;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Image;

import javax.swing.ImageIcon;
import java.io.File;

public class JIFrameFormularioUsuario extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4099793395672279988L;
	public JTextField txtFieldDNI;
	public JButton btnCancel;
	public JTextField txtFieldApellidos;
	public JButton btnAdd;
	public DatePicker datePicker;
	public JTextField txtFieldNombre;
	public JLabel lblFoto;
	public Image foto;
	public File fotoFile;

	/**
	 * Create the frame.
	 */
	public JIFrameFormularioUsuario() {
		setClosable(true);
		setMaximizable(true);
		setBounds(100, 100, 450, 531);

		JPanel panelSuperior = new JPanel();

		JPanel panelInferior = new JPanel();
		
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panelSuperior, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
						.addComponent(panelInferior, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 291, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panelSuperior, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panelInferior, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 187, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblFoto = new JLabel("");
		lblFoto.setIcon(new ImageIcon(JIFrameFormularioUsuario.class.getResource("/images/anonimo.png")));
		GridBagConstraints gbc_lblFoto = new GridBagConstraints();
		gbc_lblFoto.anchor = GridBagConstraints.NORTH;
		gbc_lblFoto.insets = new Insets(0, 0, 0, 5);
		gbc_lblFoto.gridx = 2;
		gbc_lblFoto.gridy = 0;
		panel.add(lblFoto, gbc_lblFoto);
		panelInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		btnAdd = new JButton("Add");
		panelInferior.add(btnAdd);

		btnCancel = new JButton("Cancel");
		panelInferior.add(btnCancel);
		GridBagLayout gbl_panelSuperior = new GridBagLayout();
		gbl_panelSuperior.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelSuperior.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelSuperior.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelSuperior.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelSuperior.setLayout(gbl_panelSuperior);

		JLabel lblNewLabel = new JLabel("Nombre");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		panelSuperior.add(lblNewLabel, gbc_lblNewLabel);

		txtFieldNombre = new JTextField();
		GridBagConstraints gbc_txtFieldNombre = new GridBagConstraints();
		gbc_txtFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_txtFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldNombre.gridx = 2;
		gbc_txtFieldNombre.gridy = 0;
		panelSuperior.add(txtFieldNombre, gbc_txtFieldNombre);
		txtFieldNombre.setColumns(10);

		JLabel lblApellidos = new JLabel("Apellidos");
		GridBagConstraints gbc_lblApellidos = new GridBagConstraints();
		gbc_lblApellidos.anchor = GridBagConstraints.EAST;
		gbc_lblApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidos.gridx = 1;
		gbc_lblApellidos.gridy = 1;
		panelSuperior.add(lblApellidos, gbc_lblApellidos);

		txtFieldApellidos = new JTextField();
		GridBagConstraints gbc_txtFieldApellidos = new GridBagConstraints();
		gbc_txtFieldApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_txtFieldApellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldApellidos.gridx = 2;
		gbc_txtFieldApellidos.gridy = 1;
		panelSuperior.add(txtFieldApellidos, gbc_txtFieldApellidos);
		txtFieldApellidos.setColumns(10);

		JLabel lblDni = new JLabel("DNI");
		GridBagConstraints gbc_lblDni = new GridBagConstraints();
		gbc_lblDni.anchor = GridBagConstraints.EAST;
		gbc_lblDni.insets = new Insets(0, 0, 5, 5);
		gbc_lblDni.gridx = 1;
		gbc_lblDni.gridy = 2;
		panelSuperior.add(lblDni, gbc_lblDni);

		txtFieldDNI = new JTextField();
		GridBagConstraints gbc_txtFieldDNI = new GridBagConstraints();
		gbc_txtFieldDNI.insets = new Insets(0, 0, 5, 5);
		gbc_txtFieldDNI.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldDNI.gridx = 2;
		gbc_txtFieldDNI.gridy = 2;
		panelSuperior.add(txtFieldDNI, gbc_txtFieldDNI);
		txtFieldDNI.setColumns(10);

		JLabel lblNacimiento = new JLabel("Nacimiento");
		GridBagConstraints gbc_lblNacimiento = new GridBagConstraints();
		gbc_lblNacimiento.insets = new Insets(0, 0, 0, 5);
		gbc_lblNacimiento.gridx = 1;
		gbc_lblNacimiento.gridy = 3;
		panelSuperior.add(lblNacimiento, gbc_lblNacimiento);

		datePicker = new DatePicker();
		GridBagConstraints gbc_datePicker = new GridBagConstraints();
		gbc_datePicker.insets = new Insets(0, 0, 0, 5);
		gbc_datePicker.fill = GridBagConstraints.BOTH;
		gbc_datePicker.gridx = 2;
		gbc_datePicker.gridy = 3;
		panelSuperior.add(datePicker, gbc_datePicker);
		getContentPane().setLayout(groupLayout);

	}
}
