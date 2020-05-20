package view;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class JIFrameConfiguracion extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5361221243684148333L;
	public JTextField txtFieldHost;
	public JTextField txtFieldPort;
	public JTextField txtFieldDatabase;
	public JTextField txtFieldUser;
	public JButton btnGuardar;
	public JPasswordField passwordField;

	/**
	 * Create the frame.
	 */
	public JIFrameConfiguracion() {
		setClosable(true);
		setResizable(true);
		setTitle("Configuracion");
		setBounds(100, 100, 352, 283);

		JPanel panelSuperior = new JPanel();

		JPanel panelInferior = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(panelSuperior, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 416,
										Short.MAX_VALUE)
								.addComponent(panelInferior, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 416,
										Short.MAX_VALUE))
						.addContainerGap()));
		groupLayout
				.setVerticalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addContainerGap()
										.addComponent(panelSuperior, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(panelInferior,
												GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		panelSuperior.setLayout(new MigLayout("", "[][][grow][][][]", "[][][][][][]"));

		JLabel lblHost = new JLabel("Host");
		panelSuperior.add(lblHost, "cell 1 0,alignx trailing");

		txtFieldHost = new JTextField();
		panelSuperior.add(txtFieldHost, "cell 2 0 3 1,growx");
		txtFieldHost.setColumns(10);

		JLabel lblPort = new JLabel("Port");
		panelSuperior.add(lblPort, "cell 1 1,alignx trailing");

		txtFieldPort = new JTextField();
		panelSuperior.add(txtFieldPort, "cell 2 1,growx");
		txtFieldPort.setColumns(10);

		JLabel lblDatabase = new JLabel("Database");
		panelSuperior.add(lblDatabase, "cell 1 2,alignx trailing");

		txtFieldDatabase = new JTextField();
		panelSuperior.add(txtFieldDatabase, "cell 2 2 2 1,growx");
		txtFieldDatabase.setColumns(10);

		JLabel lblUser = new JLabel("User");
		panelSuperior.add(lblUser, "cell 1 4,alignx trailing");

		txtFieldUser = new JTextField();
		panelSuperior.add(txtFieldUser, "cell 2 4,growx");
		txtFieldUser.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		panelSuperior.add(lblPassword, "cell 1 5,alignx trailing");

		passwordField = new JPasswordField();
		panelSuperior.add(passwordField, "cell 2 5,growx");
		panelInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		btnGuardar = new JButton("Guardar");
		panelInferior.add(btnGuardar);
		getContentPane().setLayout(groupLayout);

	}
}
