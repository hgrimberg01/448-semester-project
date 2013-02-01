import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.Icon;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.ImageIcon;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.SystemColor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpringLayout;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.ComponentOrientation;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class mainCommandWindow {

	private JFrame frmTeam;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField txtLeadHacker;
	private JTextField textField_14;
	private JTextField txtAppleDrive;
	private JTextField txtMayth;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
					mainCommandWindow window = new mainCommandWindow();
					window.frmTeam.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainCommandWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTeam = new JFrame();
		frmTeam.setTitle("Team++");
		frmTeam.setResizable(false);
		frmTeam.setBounds(100, 100, 1185, 887);
		frmTeam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTeam.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setBackground(UIManager.getColor("CheckBox.background"));
		tabbedPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabbedPane.setBounds(10, 10, (frmTeam.getWidth()-20), (frmTeam.getHeight()-100));
		frmTeam.getContentPane().add(tabbedPane);
		
		JSplitPane splitPanelManageContacts = new JSplitPane();
		tabbedPane.add(splitPanelManageContacts);
		JLabel lblContacts = new JLabel("Contacts");
		lblContacts.setIcon(new ImageIcon(mainCommandWindow.class.getResource("/Images/singleHead.png")));
		lblContacts.setPreferredSize(new Dimension(200,100));
		tabbedPane.setTabComponentAt(0, lblContacts);
		
		//tabbedPane.setTitleAt(0, "Contacts");
		//tabbedPane.setIconAt(0, new ImageIcon(mainCommandWindow.class.getResource("/Images/singleHead.png")));
		tabbedPane.setEnabledAt(0, true);
		
		
		JPanel panelContactsList = new JPanel();
		panelContactsList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panelContactsList.setBackground(UIManager.getColor("Button.background"));
		panelContactsList.setMinimumSize(new Dimension(300, 10));
		splitPanelManageContacts.setLeftComponent(panelContactsList);
		panelContactsList.setLayout(null);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		toolBar.setBackground(SystemColor.scrollbar);
		toolBar.setBounds(0, 0, 300, 30);
		
		panelContactsList.add(toolBar);
		
		JLabel lblSearch = new JLabel("");
		lblSearch.setIcon(new ImageIcon(mainCommandWindow.class.getResource("/Images/search.png")));
		toolBar.add(lblSearch);
		
		textField_1 = new JTextField();
		toolBar.add(textField_1);
		textField_1.setColumns(10);
		
		JToolBar toolBarBottom = new JToolBar();
		toolBarBottom.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		toolBarBottom.setBackground(SystemColor.scrollbar);
		toolBarBottom.setBounds(0, 745, 300, 30);
		
		panelContactsList.add(toolBarBottom);
		
		JButton btnCreateContact = new JButton("Create Contact");
		toolBarBottom.add(btnCreateContact);
		
		JButton btnEditContact = new JButton("Edit Contact");
		toolBarBottom.add(btnEditContact);
		
		JButton btnDeleteContact = new JButton("Delete Contact");
		toolBarBottom.add(btnDeleteContact);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scrollPane.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 30, 300, 800);
		scrollPane.setPreferredSize(new Dimension(280, panelContactsList.getHeight()));
		panelContactsList.add(scrollPane);
		
		JList list_1 = new JList();
		list_1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		list_1.setModel(new AbstractListModel() {
			String[] values = new String[] {"Felix Jong", "Georgann Soden", "Tamica Albano", "Kenya Fegan", "Emil Hoskins", "Elenora Jeanes", "Denae Goldschmidt", "Janene Unger", "Angelique Augustus", "Sanjuana Zank", "Youlanda Spurlock", "Sallie Huertas", "Lawana Soltis", "Reyes Fortman", "Talia Serafin", "Fredda Simoes", "Hanna Deremer", "Gertie Valliere", "Noe Clair", "Jacinto Paulino", "Victor Kyger", "Gilda Burdette", "Alice Cone", "Latrisha Reich", "Barrie Vanscoy", "Tod Taing", "Denese Tevis", "Sharan Debelak", "Charleen Diede", "Shona Bucko"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.setBounds(0, 0, 300, 500);
		list_1.setPreferredSize(new Dimension(280, 750));
		scrollPane.setViewportView(list_1);
		
		JPanel panelManage = new JPanel();
		panelManage.setBackground(Color.WHITE);
		splitPanelManageContacts.setRightComponent(panelManage);
		GridBagLayout gbl_panelManage = new GridBagLayout();
		gbl_panelManage.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panelManage.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panelManage.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelManage.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelManage.setLayout(gbl_panelManage);
		
		JLabel lblGeorgannSoden = new JLabel("Georgann Soden");
		lblGeorgannSoden.setFont(new Font("Dialog", Font.BOLD, 22));
		GridBagConstraints gbc_lblGeorgannSoden = new GridBagConstraints();
		gbc_lblGeorgannSoden.insets = new Insets(0, 0, 5, 5);
		gbc_lblGeorgannSoden.gridx = 1;
		gbc_lblGeorgannSoden.gridy = 0;
		panelManage.add(lblGeorgannSoden, gbc_lblGeorgannSoden);
		
		JLabel lblJobTitle = new JLabel("Job Title:");
		GridBagConstraints gbc_lblJobTitle = new GridBagConstraints();
		gbc_lblJobTitle.anchor = GridBagConstraints.WEST;
		gbc_lblJobTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblJobTitle.gridx = 1;
		gbc_lblJobTitle.gridy = 2;
		panelManage.add(lblJobTitle, gbc_lblJobTitle);
		
		txtLeadHacker = new JTextField();
		txtLeadHacker.setText("Lead Hacker");
		GridBagConstraints gbc_txtLeadHacker = new GridBagConstraints();
		gbc_txtLeadHacker.insets = new Insets(0, 0, 5, 0);
		gbc_txtLeadHacker.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLeadHacker.gridx = 2;
		gbc_txtLeadHacker.gridy = 2;
		panelManage.add(txtLeadHacker, gbc_txtLeadHacker);
		txtLeadHacker.setColumns(10);
		
		JLabel lblHomePhone = new JLabel("Home Phone:");
		GridBagConstraints gbc_lblHomePhone = new GridBagConstraints();
		gbc_lblHomePhone.anchor = GridBagConstraints.WEST;
		gbc_lblHomePhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblHomePhone.gridx = 1;
		gbc_lblHomePhone.gridy = 3;
		panelManage.add(lblHomePhone, gbc_lblHomePhone);
		
		textField_14 = new JTextField();
		textField_14.setText("(913) 626-7992");
		GridBagConstraints gbc_textField_14 = new GridBagConstraints();
		gbc_textField_14.insets = new Insets(0, 0, 5, 0);
		gbc_textField_14.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_14.gridx = 2;
		gbc_textField_14.gridy = 3;
		panelManage.add(textField_14, gbc_textField_14);
		textField_14.setColumns(10);
		
		JLabel lblHomeAddress = new JLabel("Home Address:");
		GridBagConstraints gbc_lblHomeAddress = new GridBagConstraints();
		gbc_lblHomeAddress.anchor = GridBagConstraints.WEST;
		gbc_lblHomeAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblHomeAddress.gridx = 1;
		gbc_lblHomeAddress.gridy = 4;
		panelManage.add(lblHomeAddress, gbc_lblHomeAddress);
		
		txtAppleDrive = new JTextField();
		txtAppleDrive.setText("123 Apple Drive");
		GridBagConstraints gbc_txtAppleDrive = new GridBagConstraints();
		gbc_txtAppleDrive.insets = new Insets(0, 0, 5, 0);
		gbc_txtAppleDrive.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAppleDrive.gridx = 2;
		gbc_txtAppleDrive.gridy = 4;
		panelManage.add(txtAppleDrive, gbc_txtAppleDrive);
		txtAppleDrive.setColumns(10);
		
		JLabel lblBirthday = new JLabel("Birthday:");
		GridBagConstraints gbc_lblBirthday = new GridBagConstraints();
		gbc_lblBirthday.insets = new Insets(0, 0, 0, 5);
		gbc_lblBirthday.anchor = GridBagConstraints.WEST;
		gbc_lblBirthday.gridx = 1;
		gbc_lblBirthday.gridy = 5;
		panelManage.add(lblBirthday, gbc_lblBirthday);
		
		txtMayth = new JTextField();
		txtMayth.setText("May 19th");
		GridBagConstraints gbc_txtMayth = new GridBagConstraints();
		gbc_txtMayth.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMayth.gridx = 2;
		gbc_txtMayth.gridy = 5;
		panelManage.add(txtMayth, gbc_txtMayth);
		txtMayth.setColumns(10);
		
		JPanel panelGenerateReport = new JPanel();
		panelGenerateReport.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JLabel lblReportTab = new JLabel("Reports");
		lblReportTab.setIcon(new ImageIcon(mainCommandWindow.class.getResource("/Images/collaborate.png")));
		//lblReportTab.setHorizontalTextPosition(10);
		lblReportTab.setPreferredSize(new Dimension(200,100));
		tabbedPane.add(panelGenerateReport);
		tabbedPane.setTabComponentAt(1, lblReportTab);
		tabbedPane.setEnabledAt(1, true);
		
		GridBagLayout gbl_panelGenerateReport = new GridBagLayout();
		gbl_panelGenerateReport.columnWidths = new int[]{10, 10,411, 300, 0};
		gbl_panelGenerateReport.rowHeights = new int[]{48, 28, 28, 31, 28, 0, 28, 28, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelGenerateReport.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0};
		gbl_panelGenerateReport.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelGenerateReport.setLayout(gbl_panelGenerateReport);
		
		JLabel label_3 = new JLabel("Generate Report:\n");
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.WEST;
		gbc_label_3.fill = GridBagConstraints.VERTICAL;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridwidth = 2;
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 0;
		panelGenerateReport.add(label_3, gbc_label_3);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.EAST;
		gbc_panel.gridheight = 21;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 3;
		gbc_panel.gridy = 0;
		panelGenerateReport.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton btnLoadExistingReport = new JButton("Load Existing Report");
		btnLoadExistingReport.setHorizontalAlignment(SwingConstants.LEADING);
		btnLoadExistingReport.setIcon(new ImageIcon(mainCommandWindow.class.getResource("/Images/load.png")));
		GridBagConstraints gbc_btnLoadExistingReport = new GridBagConstraints();
		gbc_btnLoadExistingReport.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLoadExistingReport.insets = new Insets(0, 0, 5, 0);
		gbc_btnLoadExistingReport.gridx = 0;
		gbc_btnLoadExistingReport.gridy = 0;
		panel.add(btnLoadExistingReport, gbc_btnLoadExistingReport);
		
		JButton btnSaveCurrentReport = new JButton("Save Current Report");
		btnSaveCurrentReport.setHorizontalAlignment(SwingConstants.LEADING);
		btnSaveCurrentReport.setIcon(new ImageIcon(mainCommandWindow.class.getResource("/Images/save.png")));
		GridBagConstraints gbc_btnSaveCurrentReport = new GridBagConstraints();
		gbc_btnSaveCurrentReport.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSaveCurrentReport.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveCurrentReport.gridx = 0;
		gbc_btnSaveCurrentReport.gridy = 1;
		panel.add(btnSaveCurrentReport, gbc_btnSaveCurrentReport);
		
		JButton btnDeleteAReport = new JButton("Delete a Report");
		btnDeleteAReport.setHorizontalAlignment(SwingConstants.LEADING);
		btnDeleteAReport.setIcon(new ImageIcon(mainCommandWindow.class.getResource("/Images/delete.png")));
		GridBagConstraints gbc_btnDeleteAReport = new GridBagConstraints();
		gbc_btnDeleteAReport.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeleteAReport.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeleteAReport.gridx = 0;
		gbc_btnDeleteAReport.gridy = 2;
		panel.add(btnDeleteAReport, gbc_btnDeleteAReport);
		
		JButton btnSendReport = new JButton("Send Report");
		btnSendReport.setHorizontalAlignment(SwingConstants.LEADING);
		btnSendReport.setIcon(new ImageIcon(mainCommandWindow.class.getResource("/Images/mail.png")));
		GridBagConstraints gbc_btnSendReport = new GridBagConstraints();
		gbc_btnSendReport.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSendReport.gridx = 0;
		gbc_btnSendReport.gridy = 3;
		panel.add(btnSendReport, gbc_btnSendReport);
		
		JLabel lblStartDate = new JLabel("Start Date:");
		GridBagConstraints gbc_lblStartDate = new GridBagConstraints();
		gbc_lblStartDate.anchor = GridBagConstraints.WEST;
		gbc_lblStartDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartDate.gridx = 1;
		gbc_lblStartDate.gridy = 1;
		panelGenerateReport.add(lblStartDate, gbc_lblStartDate);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.anchor = GridBagConstraints.NORTHWEST;
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 1;
		panelGenerateReport.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JLabel lblEndDate = new JLabel("End Date:");
		GridBagConstraints gbc_lblEndDate = new GridBagConstraints();
		gbc_lblEndDate.anchor = GridBagConstraints.WEST;
		gbc_lblEndDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblEndDate.gridx = 1;
		gbc_lblEndDate.gridy = 2;
		panelGenerateReport.add(lblEndDate, gbc_lblEndDate);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.anchor = GridBagConstraints.NORTHWEST;
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.gridx = 2;
		gbc_textField_3.gridy = 2;
		panelGenerateReport.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		JLabel label = new JLabel("Dimensions:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 4;
		panelGenerateReport.add(label, gbc_label);
		
		textField_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.anchor = GridBagConstraints.NORTH;
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.gridx = 2;
		gbc_textField_4.gridy = 4;
		panelGenerateReport.add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);
		
		JLabel lblMetric = new JLabel("Metric:");
		GridBagConstraints gbc_lblMetric = new GridBagConstraints();
		gbc_lblMetric.anchor = GridBagConstraints.WEST;
		gbc_lblMetric.insets = new Insets(0, 0, 5, 5);
		gbc_lblMetric.gridx = 1;
		gbc_lblMetric.gridy = 6;
		panelGenerateReport.add(lblMetric, gbc_lblMetric);
		
		textField_5 = new JTextField();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.anchor = GridBagConstraints.NORTH;
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.insets = new Insets(0, 0, 5, 5);
		gbc_textField_5.gridx = 2;
		gbc_textField_5.gridy = 6;
		panelGenerateReport.add(textField_5, gbc_textField_5);
		textField_5.setColumns(10);
		
		JLabel lblSort = new JLabel("Sort:");
		GridBagConstraints gbc_lblSort = new GridBagConstraints();
		gbc_lblSort.anchor = GridBagConstraints.WEST;
		gbc_lblSort.insets = new Insets(0, 0, 5, 5);
		gbc_lblSort.gridx = 1;
		gbc_lblSort.gridy = 7;
		panelGenerateReport.add(lblSort, gbc_lblSort);
		
		textField_6 = new JTextField();
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.anchor = GridBagConstraints.NORTH;
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.insets = new Insets(0, 0, 5, 5);
		gbc_textField_6.gridx = 2;
		gbc_textField_6.gridy = 7;
		panelGenerateReport.add(textField_6, gbc_textField_6);
		textField_6.setColumns(10);
		
		JLabel lblFilter = new JLabel("Filter:");
		GridBagConstraints gbc_lblFilter = new GridBagConstraints();
		gbc_lblFilter.anchor = GridBagConstraints.WEST;
		gbc_lblFilter.insets = new Insets(0, 0, 5, 5);
		gbc_lblFilter.gridx = 1;
		gbc_lblFilter.gridy = 8;
		panelGenerateReport.add(lblFilter, gbc_lblFilter);
		
		textField_7 = new JTextField();
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.insets = new Insets(0, 0, 5, 5);
		gbc_textField_7.anchor = GridBagConstraints.NORTH;
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.gridx = 2;
		gbc_textField_7.gridy = 8;
		panelGenerateReport.add(textField_7, gbc_textField_7);
		textField_7.setColumns(10);
		
		JSplitPane splitPanelDepartments = new JSplitPane();
		JLabel lblDepartmentsTab = new JLabel("Departments");
		lblDepartmentsTab.setIcon(new ImageIcon(mainCommandWindow.class.getResource("/Images/contacts.png")));
		tabbedPane.add(splitPanelDepartments);
		lblDepartmentsTab.setPreferredSize(new Dimension(200,100));
		tabbedPane.setTabComponentAt(2, lblDepartmentsTab);
		
		JPanel panelManageDepartments = new JPanel();
		panelManageDepartments.setLayout(null);
		panelManageDepartments.setBackground(Color.WHITE);
		splitPanelDepartments.setRightComponent(panelManageDepartments);
		
		JToolBar toolBar_1 = new JToolBar();
		toolBar_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		toolBar_1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		toolBar_1.setFloatable(false);
		toolBar_1.setBounds(0, 717, 809, 45);
		panelManageDepartments.add(toolBar_1);
		
		JButton btnCreateNewDepartment = new JButton("Create New Department");
		toolBar_1.add(btnCreateNewDepartment);
		
		JButton btnEditSelectedDepartment = new JButton("Edit Selected Department");
		toolBar_1.add(btnEditSelectedDepartment);
		
		JButton btnDeleteSelectedDepartment = new JButton("Delete Selected Department");
		toolBar_1.add(btnDeleteSelectedDepartment);
		
		JList list = new JList();
		list.setName("Departments:\n");
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"IT Department", "Hackers", "Marketing"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setPreferredSize(new Dimension(300,100));
		splitPanelDepartments.setLeftComponent(list);
		
		JPanel panelSettings = new JPanel();
		JLabel lblSettingsTab = new JLabel("Settings");
		lblSettingsTab.setIcon(new ImageIcon(mainCommandWindow.class.getResource("/Images/gear.png")));
		tabbedPane.add(panelSettings, null);
		lblSettingsTab.setPreferredSize(new Dimension(200,100));
		tabbedPane.setTabComponentAt(3, lblSettingsTab);
		tabbedPane.setEnabledAt(3, true);
		GridBagLayout gbl_panelSettings = new GridBagLayout();
		gbl_panelSettings.columnWidths = new int[]{10, 218, 61, 481, 0};
		gbl_panelSettings.rowHeights = new int[]{48, 0, 28, 0, 0, 0, 0, 0, 0, 0, 487, 29, 0};
		gbl_panelSettings.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelSettings.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelSettings.setLayout(gbl_panelSettings);
		
		JLabel lblSettings = new JLabel("Settings:");
		lblSettings.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		GridBagConstraints gbc_lblSettings = new GridBagConstraints();
		gbc_lblSettings.fill = GridBagConstraints.BOTH;
		gbc_lblSettings.insets = new Insets(0, 0, 5, 5);
		gbc_lblSettings.gridx = 1;
		gbc_lblSettings.gridy = 0;
		panelSettings.add(lblSettings, gbc_lblSettings);
		
		JLabel lblSqlDatabaseType = new JLabel("SQL Database Type:");
		GridBagConstraints gbc_lblSqlDatabaseType = new GridBagConstraints();
		gbc_lblSqlDatabaseType.anchor = GridBagConstraints.WEST;
		gbc_lblSqlDatabaseType.insets = new Insets(0, 0, 5, 5);
		gbc_lblSqlDatabaseType.gridx = 1;
		gbc_lblSqlDatabaseType.gridy = 1;
		panelSettings.add(lblSqlDatabaseType, gbc_lblSqlDatabaseType);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"MySQL"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		panelSettings.add(comboBox, gbc_comboBox);
		
		JLabel lblSqlServer = new JLabel("SQL Server:");
		GridBagConstraints gbc_lblSqlServer = new GridBagConstraints();
		gbc_lblSqlServer.anchor = GridBagConstraints.WEST;
		gbc_lblSqlServer.insets = new Insets(0, 0, 5, 5);
		gbc_lblSqlServer.gridx = 1;
		gbc_lblSqlServer.gridy = 2;
		panelSettings.add(lblSqlServer, gbc_lblSqlServer);
		
		textField = new JTextField();
		textField.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.NORTHWEST;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridwidth = 2;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		panelSettings.add(textField, gbc_textField);
		
		JLabel lblSqlDatabaseName = new JLabel("SQL Database Name:");
		GridBagConstraints gbc_lblSqlDatabaseName = new GridBagConstraints();
		gbc_lblSqlDatabaseName.anchor = GridBagConstraints.WEST;
		gbc_lblSqlDatabaseName.insets = new Insets(0, 0, 5, 5);
		gbc_lblSqlDatabaseName.gridx = 1;
		gbc_lblSqlDatabaseName.gridy = 3;
		panelSettings.add(lblSqlDatabaseName, gbc_lblSqlDatabaseName);
		
		textField_13 = new JTextField();
		GridBagConstraints gbc_textField_13 = new GridBagConstraints();
		gbc_textField_13.insets = new Insets(0, 0, 5, 5);
		gbc_textField_13.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_13.gridx = 2;
		gbc_textField_13.gridy = 3;
		panelSettings.add(textField_13, gbc_textField_13);
		textField_13.setColumns(10);
		
		JLabel lblMailServer = new JLabel("SQL Username:");
		GridBagConstraints gbc_lblMailServer = new GridBagConstraints();
		gbc_lblMailServer.anchor = GridBagConstraints.WEST;
		gbc_lblMailServer.insets = new Insets(0, 0, 5, 5);
		gbc_lblMailServer.gridx = 1;
		gbc_lblMailServer.gridy = 4;
		panelSettings.add(lblMailServer, gbc_lblMailServer);
		
		textField_8 = new JTextField();
		GridBagConstraints gbc_textField_8 = new GridBagConstraints();
		gbc_textField_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_8.insets = new Insets(0, 0, 5, 5);
		gbc_textField_8.gridx = 2;
		gbc_textField_8.gridy = 4;
		panelSettings.add(textField_8, gbc_textField_8);
		textField_8.setColumns(10);
		
		JLabel lblSqlUsername = new JLabel("SQL Password:");
		GridBagConstraints gbc_lblSqlUsername = new GridBagConstraints();
		gbc_lblSqlUsername.anchor = GridBagConstraints.WEST;
		gbc_lblSqlUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblSqlUsername.gridx = 1;
		gbc_lblSqlUsername.gridy = 5;
		panelSettings.add(lblSqlUsername, gbc_lblSqlUsername);
		
		textField_9 = new JTextField();
		GridBagConstraints gbc_textField_9 = new GridBagConstraints();
		gbc_textField_9.insets = new Insets(0, 0, 5, 5);
		gbc_textField_9.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_9.gridx = 2;
		gbc_textField_9.gridy = 5;
		panelSettings.add(textField_9, gbc_textField_9);
		textField_9.setColumns(10);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 2;
		gbc_separator.gridy = 6;
		panelSettings.add(separator, gbc_separator);
		
		JLabel lblEmailServer = new JLabel("Email Server:");
		GridBagConstraints gbc_lblEmailServer = new GridBagConstraints();
		gbc_lblEmailServer.anchor = GridBagConstraints.WEST;
		gbc_lblEmailServer.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmailServer.gridx = 1;
		gbc_lblEmailServer.gridy = 7;
		panelSettings.add(lblEmailServer, gbc_lblEmailServer);
		
		textField_10 = new JTextField();
		GridBagConstraints gbc_textField_10 = new GridBagConstraints();
		gbc_textField_10.insets = new Insets(0, 0, 5, 5);
		gbc_textField_10.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_10.gridx = 2;
		gbc_textField_10.gridy = 7;
		panelSettings.add(textField_10, gbc_textField_10);
		textField_10.setColumns(10);
		
		JLabel lblEmailUsername = new JLabel("Email Username:");
		GridBagConstraints gbc_lblEmailUsername = new GridBagConstraints();
		gbc_lblEmailUsername.anchor = GridBagConstraints.WEST;
		gbc_lblEmailUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmailUsername.gridx = 1;
		gbc_lblEmailUsername.gridy = 8;
		panelSettings.add(lblEmailUsername, gbc_lblEmailUsername);
		
		textField_11 = new JTextField();
		GridBagConstraints gbc_textField_11 = new GridBagConstraints();
		gbc_textField_11.insets = new Insets(0, 0, 5, 5);
		gbc_textField_11.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_11.gridx = 2;
		gbc_textField_11.gridy = 8;
		panelSettings.add(textField_11, gbc_textField_11);
		textField_11.setColumns(10);
		
		JLabel lblEmailPassword = new JLabel("Email Password:");
		GridBagConstraints gbc_lblEmailPassword = new GridBagConstraints();
		gbc_lblEmailPassword.anchor = GridBagConstraints.WEST;
		gbc_lblEmailPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmailPassword.gridx = 1;
		gbc_lblEmailPassword.gridy = 9;
		panelSettings.add(lblEmailPassword, gbc_lblEmailPassword);
		
		textField_12 = new JTextField();
		GridBagConstraints gbc_textField_12 = new GridBagConstraints();
		gbc_textField_12.insets = new Insets(0, 0, 5, 5);
		gbc_textField_12.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_12.gridx = 2;
		gbc_textField_12.gridy = 9;
		panelSettings.add(textField_12, gbc_textField_12);
		textField_12.setColumns(10);
		
		JButton btnRevertChanges = new JButton("Revert Changes");
		GridBagConstraints gbc_btnRevertChanges = new GridBagConstraints();
		gbc_btnRevertChanges.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnRevertChanges.insets = new Insets(0, 0, 0, 5);
		gbc_btnRevertChanges.gridwidth = 2;
		gbc_btnRevertChanges.gridx = 1;
		gbc_btnRevertChanges.gridy = 11;
		panelSettings.add(btnRevertChanges, gbc_btnRevertChanges);
		
		JButton btnSaveChanges = new JButton("Save Changes");
		GridBagConstraints gbc_btnSaveChanges = new GridBagConstraints();
		gbc_btnSaveChanges.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSaveChanges.gridx = 3;
		gbc_btnSaveChanges.gridy = 11;
		panelSettings.add(btnSaveChanges, gbc_btnSaveChanges);
	}
}
