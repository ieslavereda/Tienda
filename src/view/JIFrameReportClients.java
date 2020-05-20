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

public class JIFrameReportClients extends JInternalFrame {
	public JButton btnTodos;
	public JButton btnCustom;
	public JComboBox cbOrderBy;
	public JComboBox cbOrder;
	private JSeparator separator;
	private JLabel lblYear;
	private JLabel lblOrder;
	private JLabel lblOrder_1;
	private JLabel lblId;
	private JLabel lblTo;
	public DatePicker datePickerFrom;
	public DatePicker datePickerTo;
	public JComboBox cbId;

	/**
	 * Create the frame.
	 */
	public JIFrameReportClients() {
		setPreferredSize(new Dimension(800, 600));

		int comboBoxWidth = 100;
		int datePickerTextFieldWidth = 175;
		int datePickerButtonWidth = 20;
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

		btnTodos = new JButton("All");
		toolBarClientes.add(btnTodos);

		lblId = new JLabel("id");
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblId.setPreferredSize(new Dimension(30, 15));
		toolBarClientes.add(lblId);

		cbId = new JComboBox();

		cbId.setModel(new DefaultComboBoxModel(new String[] {"All"}));
		toolBarClientes.add(cbId);

		lblYear = new JLabel("From ");
		lblYear.setHorizontalAlignment(SwingConstants.RIGHT);
		lblYear.setPreferredSize(new Dimension(50, 15));
		lblYear.setMaximumSize(new Dimension(100, 15));
		lblYear.setMinimumSize(new Dimension(100, 15));
		toolBarClientes.add(lblYear);

		datePickerFrom = new DatePicker();
		datePickerFrom.getComponentDateTextField().setEditable(false);
		toolBarClientes.add(datePickerFrom);

		lblTo = new JLabel("To ");
		lblTo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTo.setHorizontalTextPosition(SwingConstants.LEADING);
		lblTo.setPreferredSize(new Dimension(40, 15));
		toolBarClientes.add(lblTo);

		datePickerTo = new DatePicker();
		datePickerTo.getComponentDateTextField().setEditable(false);
		toolBarClientes.add(datePickerTo);

		lblOrder = new JLabel("Order by ");
		lblOrder.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOrder.setPreferredSize(new Dimension(70, 15));
		toolBarClientes.add(lblOrder);

		cbOrderBy = new JComboBox<Object>();
		toolBarClientes.add(cbOrderBy);

		lblOrder_1 = new JLabel("Order ");
		lblOrder_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOrder_1.setPreferredSize(new Dimension(50, 15));
		toolBarClientes.add(lblOrder_1);

		cbOrder = new JComboBox<Object>();
		cbOrder.setModel(new DefaultComboBoxModel(new String[] { "ascendente", "descendente" }));
		toolBarClientes.add(cbOrder);

		btnCustom = new JButton("Custom");
		toolBarClientes.add(btnCustom);

		separator = new JSeparator();
		toolBarClientes.add(separator);

		// Establecemos dimensiones de para la barra de herramientas
		int toolBarHeight = (int) toolBarClientes.getPreferredSize().getHeight();
		Dimension dimComboBox = new Dimension(comboBoxWidth, toolBarHeight);
		Dimension dimDatePickerTextField = new Dimension(datePickerTextFieldWidth, toolBarHeight);
		Dimension dimDatePickerButton = new Dimension(datePickerButtonWidth, toolBarHeight);

		cbId.setPreferredSize(dimComboBox);
		datePickerFrom.getComponentDateTextField().setPreferredSize(dimDatePickerTextField);
		datePickerFrom.getComponentToggleCalendarButton().setPreferredSize(dimDatePickerButton);
		datePickerTo.getComponentDateTextField().setPreferredSize(dimDatePickerTextField);
		datePickerTo.getComponentToggleCalendarButton().setPreferredSize(dimDatePickerButton);
		
		// Establecemos el tamaño minimo del Frame
		this.setMinimumSize(new Dimension(frameWidth, frameHeight));
		this.setPreferredSize(this.getMinimumSize());
	}

}
