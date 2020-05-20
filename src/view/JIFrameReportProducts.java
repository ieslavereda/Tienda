package view;

import javax.swing.JInternalFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import com.github.lgooddatepicker.components.DatePicker;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class JIFrameReportProducts extends JInternalFrame {
	public JButton btnCustom;
	public JComboBox cbOrderBy;
	public JComboBox cbOrder;
	private JSeparator separator;
	private JLabel lblYear;
	private JLabel lblOrder;
	private JLabel lblOrder_1;
	private JLabel lblId;
	private JLabel lblTo;
	public JComboBox cbId;
	public JTextField txtFieldPVPFrom;
	public JTextField txtFieldPVPTo;

	/**
	 * Create the frame.
	 */
	public JIFrameReportProducts() {
		setPreferredSize(new Dimension(800, 600));

		int frameWidth = 1000;
		int frameHeight = 600;

		setClosable(true);
		setTitle("Report");
		setResizable(true);
		setMaximizable(true);
		setBounds(10, 10, 1100, 810);

		JToolBar toolBarClientes = new JToolBar();
		toolBarClientes.setFloatable(false);
		getContentPane().add(toolBarClientes, BorderLayout.NORTH);

		lblId = new JLabel("Categories");
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblId.setPreferredSize(new Dimension(100, 15));
		toolBarClientes.add(lblId);

		cbId = new JComboBox();

		cbId.setModel(new DefaultComboBoxModel(new String[] {"All"}));
		toolBarClientes.add(cbId);

		lblYear = new JLabel("PVP From ");
		lblYear.setHorizontalAlignment(SwingConstants.RIGHT);
		lblYear.setPreferredSize(new Dimension(80, 15));
		lblYear.setMaximumSize(new Dimension(100, 15));
		lblYear.setMinimumSize(new Dimension(100, 15));
		toolBarClientes.add(lblYear);
		
		txtFieldPVPFrom = new JTextField();
		txtFieldPVPFrom.setMaximumSize(new Dimension(100, 2147483647));
		toolBarClientes.add(txtFieldPVPFrom);
		txtFieldPVPFrom.setColumns(10);

		lblTo = new JLabel("PVP To ");
		lblTo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTo.setHorizontalTextPosition(SwingConstants.LEADING);
		lblTo.setPreferredSize(new Dimension(80, 15));
		toolBarClientes.add(lblTo);
		
		txtFieldPVPTo = new JTextField();
		txtFieldPVPTo.setMaximumSize(new Dimension(100, 2147483647));
		toolBarClientes.add(txtFieldPVPTo);
		txtFieldPVPTo.setColumns(10);

		lblOrder = new JLabel("Order by ");
		lblOrder.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOrder.setPreferredSize(new Dimension(70, 15));
		toolBarClientes.add(lblOrder);

		cbOrderBy = new JComboBox<Object>();
		cbOrderBy.setPreferredSize(new Dimension(150, 24));
		cbOrderBy.setMaximumSize(new Dimension(150, 32767));
		toolBarClientes.add(cbOrderBy);

		lblOrder_1 = new JLabel("Order ");
		lblOrder_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOrder_1.setPreferredSize(new Dimension(50, 15));
		toolBarClientes.add(lblOrder_1);

		cbOrder = new JComboBox<Object>();
		cbOrder.setMaximumSize(new Dimension(100, 32767));
		cbOrder.setModel(new DefaultComboBoxModel(new String[] { "ascendente", "descendente" }));
		toolBarClientes.add(cbOrder);

		btnCustom = new JButton("Custom");
		toolBarClientes.add(btnCustom);

		separator = new JSeparator();
		toolBarClientes.add(separator);




		// Establecemos el tamaño minimo del Frame
		this.setMinimumSize(new Dimension(frameWidth, frameHeight));
		this.setPreferredSize(this.getMinimumSize());
	}

}
