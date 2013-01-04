/**
 * Name: mainCommandWindow.java
 * Description: Holds the central GUI components of the main program.
 * 
 * @author Pho Hale
 * Date Created: 9/28/12
 */

package com.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.managers.ContactManager;
import com.managers.DepartmentManager;
import com.managers.ReportManager;
import com.managers.SettingsManager;
import com.managers.SettingsManager;
import com.managers.SortableListModel;
import com.pojos.Contact;
import com.pojos.Department;
import com.pojos.Report;
import com.pojos.Settings;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import javax.swing.JCheckBox;
import java.awt.Toolkit;
import java.awt.Window.Type;

/**
 * mainCommandWindow
 * 
 * The entire frontend of the program. Interfaces between user and managers.
 */
@SuppressWarnings("unchecked")
public class mainCommandWindow {
	private JFrame mainFrame;

	// Managers
	private ContactManager contactManager;
	private ReportManager reportManager;
	private DepartmentManager departmentManager;
	private SettingsManager improvedSettings;

	// Tabs
	private final JTabbedPane tabsPane = new JTabbedPane(JTabbedPane.LEFT);
	private final JSplitPane contactTab = new JSplitPane();
	private final JPanel contactListPane = new JPanel();
	private final JPanel contactDetailsPane = new JPanel();
	private final JSplitPane departmentTab = new JSplitPane();
	private final JPanel departmentListPane = new JPanel();
	private final JPanel departmentDetailsPane = new JPanel();
	private final JSplitPane reportTab = new JSplitPane();
	private final JPanel reportListPane = new JPanel();
	private final JPanel reportDetailsPane = new JPanel();
	private final JPanel settingTab = new JPanel();

	// Toolbars and Buttons
	private final JList<Contact> contactList = new JList<Contact>();
	private final JList<Department> departmentList = new JList<Department>();
	private final JList<Report> reportList = new JList<Report>();
	private final JToolBar contactListToolbar = new JToolBar();
	private final JToolBar contactSearch = new JToolBar();
	private final JToolBar contactToolbar = new JToolBar();
	private final JToolBar departmentListToolbar = new JToolBar();
	private final JToolBar departmentSearch = new JToolBar();
	private final JToolBar departmentToolbar = new JToolBar();
	private final JToolBar reportListToolbar = new JToolBar();
	private final JToolBar reportSearch = new JToolBar();
	private final JToolBar reportToolbar = new JToolBar();

	private final JButton btnCreateContact = new JButton("");
	private final JButton btnDeleteContact = new JButton("");
	private final JButton btnEditContact = new JButton("Edit");
	private final JButton btnCancelContact = new JButton("Cancel");
	private final JButton btnSaveContact = new JButton("Save");

	private final JButton btnCreateDepartment = new JButton("");
	private final JButton btnDeleteDepartment = new JButton("");
	private final JButton btnEditDepartment = new JButton("Edit");
	private final JButton btnCancelDepartment = new JButton("Cancel");
	private final JButton btnSaveDepartment = new JButton("Save");

	private final JButton btnCreateReport = new JButton("");
	private final JButton btnDeleteReport = new JButton("");
	private final JButton btnEditReport = new JButton("Edit");
	private final JButton btnCancelReport = new JButton("Cancel");
	private final JButton btnSaveReport = new JButton("Save");
	private final JButton btnPreviewReport = new JButton("Preview");
	private final JButton btnSendReport = new JButton("Send");

	private final JPanel departmentEmployeeListToolbar = new JPanel();
	private final JList<Contact> lstContacts = new JList<Contact>();
	private final JButton btnAddEmployeeToDept = new JButton("Add");
	private final JButton btnRemoveEmployeeFromDept = new JButton("Remove");
	private ListSelectionListener contactListener;

	private final JPanel reportContactListToolbar = new JPanel();
	private final JPanel reportDepartmentListToolbar = new JPanel();
	

	// Contact fields
	private JTextField txtContactName;
	private JTextField txtJobTitle;
	private JTextField txtHomePhone;
	private JTextField txtHomeAddress;
	private JTextField txtWorkPhone;
	private JTextField txtWorkAddress;
	private JTextField txtEmail;
	private JDateChooser txtBirthday;
	private Contact backupContact;
	private SortableListModel<Contact> searchResults;

	// Department fields
	private JTextField txtDepartmentName;
	private JTextField txtDepartmentPhone;
	private JTextField txtDepartmentAddress;
	private Department backupDepartment;
	private SortableListModel<Department> searchDepartmentResults;

	// Report fields
	private JTextField txtContactSearch;
	private JTextField txtDepartmentSearch;
	private JTextField txtReportSearch;
	private Report backupReport;
	private Report preparedReport;
	private JDateChooser boxStartDate;
	private JDateChooser boxEndDate;
	private JComboBox boxSortOn;
	private JComboBox boxSortOrder;
	private JComboBox boxFilterOn;
	private JComboBox boxFilterBy;
	private JTree lstMetrics;
	private JFormattedTextField txtFilterText;
	private JComboBox boxMaxResults;
	private JList<Contact> reportContactList;
	private JList<Department> reportDepartmentList;
	final JTree lstDimensions = new treeDimensions();
	final JTree lstMetrics_1 = new treeMetrics();
	private SortableListModel<Report> searchReportResults;

	// Settings fields
	private JTextField txtSqlServer;
	private JTextField txtSqlUsername;
	private JTextField txtSqlPassword;
	private JTextField txtEmailServer;
	private JTextField txtEmailUsername;
	private JTextField txtSqlDatabaseName;
	private JCheckBox chckBxFormat; 

	// Colors
	private Color standardPane = Color.white;
	private Color editPane = new Color(255, 249, 214);
	private Color goodField = Color.white;
	private Color badField = new Color(204, 106, 106);

	private boolean contactAddMode = false;
	private boolean contactEditMode = false;
	private boolean departmentAddMode = false;
	private boolean departmentEditMode = false;
	private boolean reportAddMode = false;
	private boolean reportEditMode = false;
	private FormValidator contactForm;
	private FormValidator departmentForm;
	private java.sql.Connection conn;
	private Vector<Department> Departments;

	private ActionListener saveDept;
	private JTextField txtReportTitleText;

	private ListSelectionListener departmentListSelectionListener;
	
	private Settings PreviousProps;
	private boolean isFirstRun;
	private ReadyDatabaseConnectionFactory connectionFactory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					UIManager.setLookAndFeel(UIManager
							.getCrossPlatformLookAndFeelClassName());
					mainCommandWindow window = new mainCommandWindow();
					window.mainFrame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public mainCommandWindow() {
		
		try {
			this.improvedSettings = new SettingsManager();
			Settings loaded = improvedSettings.getSettings();
			

			if(improvedSettings.isSettingFileExist() && improvedSettings.writeSettings(loaded)){
				
				isFirstRun = false;
				connectionFactory = new ReadyDatabaseConnectionFactory(loaded);
				contactManager = new ContactManager(connectionFactory.getReadyDatabaseConnection());
				departmentManager = new DepartmentManager(connectionFactory.getReadyDatabaseConnection(),
						contactManager);
				reportManager = new ReportManager(connectionFactory.getReadyDatabaseConnection(), contactManager, departmentManager);
				reportManager.configureMailConnection(loaded.getEmailServerHost(),loaded.getEmailUsername());
				
			}else{
				isFirstRun = true;
			}
			
			initializeAll();

		} catch (Throwable e) {
			JOptionPane.showMessageDialog(this.mainFrame,
					"Generic Error - Please Contact Support", "Generic Error",
					JOptionPane.ERROR_MESSAGE);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeAll() {
		mainFrame = new JFrame();
		mainFrame.setMinimumSize(new Dimension(1024, 700));
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(mainCommandWindow.class.getResource("/resources/images/collaborate.png")));
		mainFrame.setBackground(SystemColor.textHighlight);
		mainFrame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// Resize the main tabView when the window size is changed.
				tabsPane.setBounds(10, 10, (mainFrame.getWidth() - 20),
						(mainFrame.getHeight() - 100));

			}
		});
		mainFrame.setTitle("Team++");
		mainFrame.setBounds(100, 100, 1100, 700);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);

		tabsPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabsPane.setBackground(UIManager.getColor("CheckBox.background"));
		tabsPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		tabsPane.setBounds(10, 10, (mainFrame.getWidth() - 20),
				(mainFrame.getHeight() - 100));
		mainFrame.getContentPane().add(tabsPane);
		
		tabsPane.setBorder(BorderFactory.createLoweredSoftBevelBorder());

		initializeContactTab();

		initializeDepartmentTab();

		initializeReportTab();

		initializeSettingTab();

		tabsPane.setEnabledAt(0, true);
		
		//Set up the look and feel of the program in non-mutable mode.
		UIManager.put( "ComboBox.disabledForeground", Color.BLACK );
		UIManager.put( "JTree.disabledForeground", Color.BLACK );

	}

	/**
	 * Initialize the contacts tab
	 */
	private void initializeContactTab() {
		contactTab.setEnabled(false);

		// Contact tab
		tabsPane.add(contactTab);
		JLabel lblContacts = new JLabel("Contacts");
		lblContacts.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/singleHead.png")));
		lblContacts.setPreferredSize(new Dimension(200, 90));
		tabsPane.setTabComponentAt(0, lblContacts);
		contactListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (contactList.getSelectedValue() != null) {
					showAllComponents(contactDetailsPane);
					if (contactAddMode)
					{
						contactList.clearSelection();
						showAllComponents(contactDetailsPane);
						JOptionPane
						.showMessageDialog(
								null,
								"There are unsaved changes in the currently selected contact."
										+ " Select 'Cancel' or 'Save' to view other contacts.");
					}
					else if (contactEditMode) {
						if (contactList.getSelectedValue() != backupContact) {
							contactList.setSelectedValue(backupContact, true);
							JOptionPane
									.showMessageDialog(
											null,
											"There are unsaved changes in the currently selected contact."
													+ " Select 'Cancel' or 'Save' to view other contacts.");
					}
					} else {
						Contact temp = (Contact) contactList.getSelectedValue();
						btnEditContact.setVisible(true);
						if (temp != null)
							updateContactDetails(temp);
					}
					contactListToolbar.repaint();
				}
				else {
					hideAllComponents(contactDetailsPane);
				}
			}
		};
		
		if (isFirstRun)
			return;

		// Contact List Pane
		contactListPane
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		contactListPane.setBackground(UIManager.getColor("Button.background"));
		contactListPane.setMinimumSize(new Dimension(300, 10));
		contactTab.setLeftComponent(contactListPane);
		GridBagLayout gbl_contactListPane = new GridBagLayout();
		gbl_contactListPane.columnWidths = new int[] { 300, 0 };
		gbl_contactListPane.rowHeights = new int[] { 0, 50, 10, 0 };
		gbl_contactListPane.columnWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		gbl_contactListPane.rowWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		contactListPane.setLayout(gbl_contactListPane);

		contactList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		// Load Contacts into Contact menu
		contactList.setModel(contactManager.getListModelContacts());
		contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contactList.addListSelectionListener(contactListener);
		GridBagConstraints gbc_contactSearch = new GridBagConstraints();
		gbc_contactSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_contactSearch.gridx = 0;
		gbc_contactSearch.gridy = 0;
		contactListPane.add(contactSearch, gbc_contactSearch);
		contactSearch.setBorder(new BevelBorder(BevelBorder.RAISED, null, null,
				null, null));
		contactSearch.setBackground(SystemColor.controlHighlight);
		
				JLabel lblContactSearch = new JLabel("");
				lblContactSearch.setIcon(new ImageIcon(mainCommandWindow.class
						.getResource("/resources/images/search.png")));
				contactSearch.add(lblContactSearch);
				
						txtContactSearch = new JTextField();
						txtContactSearch.addKeyListener(new KeyAdapter() {
							@Override
							public void keyReleased(KeyEvent e) {
								if ((txtContactSearch.getText() != null)
										&& (txtContactSearch.getText() != "")) {
									searchResults = contactManager
											.searchContactsByName(txtContactSearch.getText()); 
									contactList.setModel(searchResults);
									// Set first selection
									if (contactList.getModel().getSize() != 0) {
										contactList.setSelectedIndex(contactList
												.getFirstVisibleIndex());
									}
									else
										contactList.clearSelection();
								} else {
									// replace entire unfiltered contact list
									contactList.setModel(contactManager.getListModelContacts());
									// Set first selection
									if (contactList.getModel().getSize() != 0) {
										contactList.setSelectedIndex(contactList
												.getFirstVisibleIndex());
									}
									else
										contactList.clearSelection();
								}
							}
						});
						contactSearch.add(txtContactSearch);
						txtContactSearch.setColumns(10);

		JScrollPane contactScrollPane = new JScrollPane(contactList);
		contactScrollPane.setOpaque(false);
		contactScrollPane
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		contactScrollPane.setViewportBorder(new MatteBorder(1, 1, 1, 1,
				(Color) new Color(0, 0, 0)));
		contactScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contactScrollPane.setPreferredSize(new Dimension(280, contactListPane
				.getHeight()));
		GridBagConstraints gbc_contactScrollPane = new GridBagConstraints();
		gbc_contactScrollPane.fill = GridBagConstraints.BOTH;
		gbc_contactScrollPane.gridx = 0;
		gbc_contactScrollPane.gridy = 1;
		contactListPane.add(contactScrollPane, gbc_contactScrollPane);
		updateContactDetails(contactList.getSelectedValue());
		GridBagConstraints gbc_contactListToolbar = new GridBagConstraints();
		gbc_contactListToolbar.anchor = GridBagConstraints.SOUTH;
		gbc_contactListToolbar.fill = GridBagConstraints.HORIZONTAL;
		gbc_contactListToolbar.gridx = 0;
		gbc_contactListToolbar.gridy = 2;
		contactListPane.add(contactListToolbar, gbc_contactListToolbar);

		contactListToolbar.setFloatable(false);
		contactListToolbar.setBorder(null);
		contactListToolbar.setBackground(new Color(238, 238, 238));

		// Contact Details Pane
		contactDetailsPane.setBackground(standardPane);
		contactTab.setRightComponent(contactDetailsPane);
		GridBagLayout gbl_contactDetailsPane = new GridBagLayout();
		gbl_contactDetailsPane.columnWidths = new int[] { 10, 150, 0, 0 };
		gbl_contactDetailsPane.rowHeights = new int[] { 10, 0, 10, 40, 40, 40,
				40, 40, 40, 40, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contactDetailsPane.columnWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_contactDetailsPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, Double.MIN_VALUE };
		contactDetailsPane.setLayout(gbl_contactDetailsPane);

		contactToolbar.setFloatable(false);
		GridBagConstraints gbc_toolBar_1 = new GridBagConstraints();
		gbc_toolBar_1.anchor = GridBagConstraints.SOUTH;
		gbc_toolBar_1.gridwidth = 3;
		gbc_toolBar_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_toolBar_1.gridx = 0;
		gbc_toolBar_1.gridy = 16;
		contactDetailsPane.add(contactToolbar, gbc_toolBar_1);

		txtContactName = new JTextField("");
		txtContactName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtContactName.setBackground(goodField);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!contactForm.validateName("Contact Name",
						txtContactName.getText())) {
					txtContactName.setBackground(badField);
				}
				if (!txtContactName.getText().contains(" ")) {
					txtContactName.setBackground(badField);
				}
			}
		});
		txtContactName.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		GridBagConstraints gbc_lblContactName = new GridBagConstraints();
		gbc_lblContactName.gridwidth = 2;
		gbc_lblContactName.anchor = GridBagConstraints.WEST;
		gbc_lblContactName.insets = new Insets(0, 0, 5, 0);
		gbc_lblContactName.gridx = 1;
		gbc_lblContactName.gridy = 1;
		contactDetailsPane.add(txtContactName, gbc_lblContactName);
		txtContactName.setColumns(25);
		txtContactName.setMinimumSize(new Dimension(500, 35));

		JLabel lblJobTitle = new JLabel("Job Title:");
		GridBagConstraints gbc_lblJobTitle = new GridBagConstraints();
		gbc_lblJobTitle.anchor = GridBagConstraints.WEST;
		gbc_lblJobTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblJobTitle.gridx = 1;
		gbc_lblJobTitle.gridy = 3;
		contactDetailsPane.add(lblJobTitle, gbc_lblJobTitle);

		txtJobTitle = new JTextField();
		txtJobTitle.setText("");
		GridBagConstraints gbc_txtJobTitle = new GridBagConstraints();
		gbc_txtJobTitle.insets = new Insets(0, 0, 5, 0);
		gbc_txtJobTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtJobTitle.gridx = 2;
		gbc_txtJobTitle.gridy = 3;
		contactDetailsPane.add(txtJobTitle, gbc_txtJobTitle);
		txtJobTitle.setColumns(10);

		JLabel lblHomePhone = new JLabel("Home Phone:");
		GridBagConstraints gbc_lblHomePhone = new GridBagConstraints();
		gbc_lblHomePhone.anchor = GridBagConstraints.WEST;
		gbc_lblHomePhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblHomePhone.gridx = 1;
		gbc_lblHomePhone.gridy = 4;
		contactDetailsPane.add(lblHomePhone, gbc_lblHomePhone);

		txtHomePhone = new JTextField();
		txtHomePhone.setText("");
		txtHomePhone.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtHomePhone.setBackground(goodField);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!contactForm.validatePhone("Contact Home Phone",
						txtHomePhone.getText()))
					txtHomePhone.setBackground(badField);
				else
					txtHomePhone.setText(contactForm.formatPhone(txtHomePhone
							.getText()));
			}
		});
		GridBagConstraints gbc_txtHomePhone = new GridBagConstraints();
		gbc_txtHomePhone.insets = new Insets(0, 0, 5, 0);
		gbc_txtHomePhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtHomePhone.gridx = 2;
		gbc_txtHomePhone.gridy = 4;
		contactDetailsPane.add(txtHomePhone, gbc_txtHomePhone);
		txtHomePhone.setColumns(10);

		JLabel lblWorkPhone = new JLabel("Work Phone:");
		GridBagConstraints gbc_lblWorkPhone = new GridBagConstraints();
		gbc_lblWorkPhone.anchor = GridBagConstraints.WEST;
		gbc_lblWorkPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblWorkPhone.gridx = 1;
		gbc_lblWorkPhone.gridy = 5;
		contactDetailsPane.add(lblWorkPhone, gbc_lblWorkPhone);

		txtWorkPhone = new JTextField();
		txtWorkPhone.setText("");
		txtWorkPhone.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtWorkPhone.setBackground(goodField);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!contactForm.validatePhone("Contact Work Phone",
						txtWorkPhone.getText()))
					txtWorkPhone.setBackground(badField);
				else
					txtWorkPhone.setText(contactForm.formatPhone(txtWorkPhone
							.getText()));
			}
		});
		GridBagConstraints gbc_txtWorkPhone = new GridBagConstraints();
		gbc_txtWorkPhone.insets = new Insets(0, 0, 5, 0);
		gbc_txtWorkPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtWorkPhone.gridx = 2;
		gbc_txtWorkPhone.gridy = 5;
		contactDetailsPane.add(txtWorkPhone, gbc_txtWorkPhone);
		txtWorkPhone.setColumns(10);

		JLabel lblHomeAddress = new JLabel("Home Address:");
		GridBagConstraints gbc_lblHomeAddress = new GridBagConstraints();
		gbc_lblHomeAddress.anchor = GridBagConstraints.WEST;
		gbc_lblHomeAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblHomeAddress.gridx = 1;
		gbc_lblHomeAddress.gridy = 6;
		contactDetailsPane.add(lblHomeAddress, gbc_lblHomeAddress);

		txtHomeAddress = new JTextField();
		txtHomeAddress.setText("");
		GridBagConstraints gbc_txtHomeAddress = new GridBagConstraints();
		gbc_txtHomeAddress.insets = new Insets(0, 0, 5, 0);
		gbc_txtHomeAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtHomeAddress.gridx = 2;
		gbc_txtHomeAddress.gridy = 6;
		contactDetailsPane.add(txtHomeAddress, gbc_txtHomeAddress);
		txtHomeAddress.setColumns(10);

		JLabel lblWorkAddress = new JLabel("Work Address:");
		GridBagConstraints gbc_lblWorkAddress = new GridBagConstraints();
		gbc_lblWorkAddress.anchor = GridBagConstraints.WEST;
		gbc_lblWorkAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblWorkAddress.gridx = 1;
		gbc_lblWorkAddress.gridy = 7;
		contactDetailsPane.add(lblWorkAddress, gbc_lblWorkAddress);

		txtWorkAddress = new JTextField();
		txtWorkAddress.setText("");
		GridBagConstraints gbc_txtWorkAddress = new GridBagConstraints();
		gbc_txtWorkAddress.insets = new Insets(0, 0, 5, 0);
		gbc_txtWorkAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtWorkAddress.gridx = 2;
		gbc_txtWorkAddress.gridy = 7;
		contactDetailsPane.add(txtWorkAddress, gbc_txtWorkAddress);
		txtWorkAddress.setColumns(10);

		JLabel lblEmail = new JLabel("eMail:");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 8;
		contactDetailsPane.add(lblEmail, gbc_lblEmail);

		txtEmail = new JTextField();
		txtEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtEmail.setBackground(goodField);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!contactForm.validateEmail("Contact Email Address",
						txtEmail.getText()))
					txtEmail.setBackground(badField);
			}
		});
		txtEmail.setText("");
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.insets = new Insets(0, 0, 5, 0);
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.gridx = 2;
		gbc_txtEmail.gridy = 8;
		contactDetailsPane.add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);

		JLabel lblBirthday = new JLabel("Birthday:");
		GridBagConstraints gbc_lblBirthday = new GridBagConstraints();
		gbc_lblBirthday.insets = new Insets(0, 0, 5, 5);
		gbc_lblBirthday.anchor = GridBagConstraints.WEST;
		gbc_lblBirthday.gridx = 1;
		gbc_lblBirthday.gridy = 9;
		contactDetailsPane.add(lblBirthday, gbc_lblBirthday);

		txtBirthday = new JDateChooser();
		txtBirthday.setDateFormatString("MMM dd");
		((JTextFieldDateEditor) ((JDateChooser) txtBirthday).getDateEditor())
		.setEditable(false);
		GridBagConstraints gbc_txtBirthday = new GridBagConstraints();
		gbc_txtBirthday.insets = new Insets(0, 0, 5, 0);
		gbc_txtBirthday.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBirthday.gridx = 2;
		gbc_txtBirthday.gridy = 9;
		contactDetailsPane.add(txtBirthday, gbc_txtBirthday);

		contactEditMode(false);
		
		initializeContactButtons();
		
		flattenSplitPane(contactTab);
		
		hideAllComponents(contactDetailsPane);
	}

	
	/**
	 * Initialize Contact Buttons
	 */
	private void initializeContactButtons() {

		btnCreateContact.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/plus.png")));
		btnCreateContact.setToolTipText("Create Contact");
		contactListToolbar.add(btnCreateContact);
		btnCreateContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contactList.clearSelection();
				showAllComponents(contactDetailsPane);

				contactForm = new FormValidator();
				contactAddMode = true;

				backupContact = new Contact();
				updateContactDetails(backupContact);
				contactEditMode(true);

			}
		});
		btnDeleteContact.setBackground(UIManager.getColor("Button.background"));
		btnDeleteContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (contactList.getSelectedValue() != null) {
					if (departmentEditMode || departmentAddMode || reportEditMode || reportAddMode)
					{
						JOptionPane.showMessageDialog(null, "Unable to delete selection.\n" +
								"Pending changes in another tab are preventing this action.\n" +
								"Submit changes and try again.");
					}
					else
					{
						int choice = JOptionPane.showOptionDialog(null, "Are you sure you want to delete '" +
								contactList.getSelectedValue().getFullName() + "'?\n" +
								"This action cannot be undone.", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
						
						if (choice == JOptionPane.YES_OPTION)
						{
							final int contactDeleteKey = contactList.getSelectedValue()
									.getEmployeeID();
							departmentList.clearSelection();
							reportList.clearSelection();
						
							contactManager.delete(contactDeleteKey);
							departmentManager.reloadDepartmentContactRelations();
							reportManager.reloadReportContactRelations();
		
							//Naughty Naughty
		
						
							contactList.clearSelection();
						}
					}
				}
			}

			private int JOptionPane(String string, int questionMessage,
					int yesNoOption) {
				// TODO Auto-generated method stub
				return 0;
			}
		});

		btnDeleteContact.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/delete.png")));
		btnDeleteContact.setToolTipText("Delete Contact");
		contactListToolbar.add(btnDeleteContact);

		btnEditContact.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/pencilTiny.png")));
		contactToolbar.add(btnEditContact);
		btnEditContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contactForm = new FormValidator();
				contactEditMode = true;

				backupContact = contactList.getSelectedValue();
				contactEditMode(true);
			}
		});

		btnCancelContact.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/thumbsDownTiny.png")));
		contactToolbar.add(btnCancelContact);
		btnCancelContact.setVisible(false);
		btnCancelContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (contactAddMode) {
					hideAllComponents(contactDetailsPane);
				}
				
				if (contactEditMode) {
					updateContactDetails(backupContact);
					contactList.setSelectedValue(backupContact, true);
				}
				contactEditMode(false);
				contactAddMode = false;
				contactEditMode = false;
			}
		});

		btnSaveContact.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/save.png")));
		contactToolbar.add(btnSaveContact);
		btnSaveContact.setVisible(false);
		btnSaveContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {		
					if (contactForm.valid()) {
						DateFormat format = new SimpleDateFormat("MMM dd");
						
						String birthday = "";
						
						if (!(txtBirthday.getDate() == null))
							birthday = format.format(txtBirthday.getDate());
						
						if (!txtContactName.getText().contains(" ")) {
							JOptionPane.showMessageDialog(null,
									"Contact name must contain a space.");
							return;
						}

						contactEditMode(false);
						if (contactEditMode) {
							int splitIndex = txtContactName.getText().indexOf(
									" ");
							String fullName = txtContactName.getText();
							String firstName = fullName
									.substring(0, splitIndex);
							String lastName = fullName
									.substring(splitIndex + 1);

							if (contactList.getSelectedIndex() != -1) {
								Integer updatedId = contactList
										.getSelectedValue().getEmployeeID();
								backupContact.updateFields(firstName, lastName,
										txtJobTitle.getText(),
										txtEmail.getText(),
										txtWorkPhone.getText(),
										txtHomePhone.getText(),
										txtWorkAddress.getText(),
										txtHomeAddress.getText(),
										birthday);
								updateContactDetails(backupContact);
								contactManager.update(backupContact);
							}
						} else {
							int splitIndex = txtContactName.getText().indexOf(
									" ");
							String fullName = txtContactName.getText();
							String firstName = fullName
									.substring(0, splitIndex);
							String lastName = fullName
									.substring(splitIndex + 1);

							backupContact.updateFields(firstName, lastName,
									txtJobTitle.getText(), txtEmail.getText(),
									txtWorkPhone.getText(),
									txtHomePhone.getText(),
									txtWorkAddress.getText(),
									txtHomeAddress.getText(),
									birthday);
							contactManager.insert(backupContact);
						}
						contactList
								.removeListSelectionListener(contactListener);
						contactList.setModel(contactManager
								.getListModelContacts());
						contactList.addListSelectionListener(contactListener);
						contactAddMode = false;
						contactEditMode = false;
						contactList.clearSelection();
						contactList.setSelectedValue(backupContact, true);
					}

				} catch (Exception k) {
					JOptionPane.showMessageDialog(null, k.getMessage());
				}
				contactListToolbar.setVisible(false);
				contactListToolbar.setVisible(true);
				contactListToolbar.repaint();
			}
		});

		JSeparator contactSeparator = new JSeparator();
		contactToolbar.add(contactSeparator);

	}

	/**
	 * Initialize the Department Tab
	 */
	private void initializeDepartmentTab() {
		departmentTab.setEnabled(false);

		// Department Tab
		tabsPane.add(departmentTab);
		JLabel lblDepartmentsTab = new JLabel("Departments");
		lblDepartmentsTab.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/contacts.png")));
		lblDepartmentsTab.setPreferredSize(new Dimension(200, 90));
		tabsPane.setTabComponentAt(1, lblDepartmentsTab);
		GridBagLayout gbl_departmentListPane = new GridBagLayout();
		gbl_departmentListPane.columnWidths = new int[] { 300, 0 };
		gbl_departmentListPane.rowHeights = new int[] { 0, 50, 10, 0 };
		gbl_departmentListPane.columnWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		gbl_departmentListPane.rowWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		
		if (isFirstRun)
			return;
		
		// Department List Pane
		departmentListPane.setLayout(gbl_departmentListPane);
		GridBagConstraints gbc_departmentSearch = new GridBagConstraints();
		gbc_departmentSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_departmentSearch.gridx = 0;
		gbc_departmentSearch.gridy = 0;
		departmentListPane.add(departmentSearch, gbc_departmentSearch);
		departmentSearch.setBorder(new BevelBorder(BevelBorder.RAISED, null,
				null, null, null));
		departmentSearch.setBackground(SystemColor.controlHighlight);
		
		JLabel lblDepartmentSearch = new JLabel("");
		lblDepartmentSearch.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/search.png")));
		departmentSearch.add(lblDepartmentSearch);
		
		txtDepartmentSearch = new JTextField();
		txtDepartmentSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if ((txtDepartmentSearch.getText() != null)
						&& (txtDepartmentSearch.getText() != "")) {
					searchDepartmentResults = departmentManager
							.searchDepartmentsByName(txtDepartmentSearch.getText()); 
					departmentList.setModel(searchDepartmentResults);
					// Set first selection
					if (departmentList.getModel().getSize() != 0) {
						departmentList.setSelectedIndex(departmentList
								.getFirstVisibleIndex());
					}
					else
						departmentList.clearSelection();
				} else {
					// replace entire unfiltered department list
					departmentList.setModel(departmentManager.getListModelDepartment());
					// Set first selection
					if (departmentList.getModel().getSize() != 0) {
						departmentList.setSelectedIndex(departmentList
								.getFirstVisibleIndex());
					}
					else
						departmentList.clearSelection();
				}
			}
		});
		txtDepartmentSearch.setColumns(10);
		departmentSearch.add(txtDepartmentSearch);

		departmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		departmentList.setModel(departmentManager.getListModelDepartment());
		departmentList
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		JScrollPane departmentScrollPane = new JScrollPane(departmentList);
		departmentScrollPane.setViewportBorder(new MatteBorder(1, 1, 1, 1,
				(Color) new Color(0, 0, 0)));
		departmentScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		departmentScrollPane.setPreferredSize(new Dimension(280, 0));
		departmentScrollPane.setOpaque(false);
		departmentScrollPane
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		GridBagConstraints gbc_departmentScrollPane = new GridBagConstraints();
		gbc_departmentScrollPane.fill = GridBagConstraints.BOTH;
		gbc_departmentScrollPane.gridx = 0;
		gbc_departmentScrollPane.gridy = 1;
		departmentListPane.add(departmentScrollPane, gbc_departmentScrollPane);
		departmentList.setToolTipText("List of all departments");
		departmentList
				.addListSelectionListener(departmentListSelectionListener);
		updateDepartmentDetails(departmentList.getSelectedValue());
		departmentListSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				// Populates the rightmost pane with information from selected
				// object
				if (departmentList.getSelectedValue() != null) {
					showAllComponents(departmentDetailsPane);
					if (departmentAddMode)
					{
						departmentList.clearSelection();
						showAllComponents(departmentDetailsPane);
						JOptionPane
						.showMessageDialog(
								null,
								"There are unsaved changes in the currently selected department."
										+ " Select 'Cancel' or 'Save' to view other contacts.");
					}
					else if (departmentEditMode) {
						if (departmentList.getSelectedValue() != backupDepartment) {
							departmentList.setSelectedValue(backupDepartment, true);
							JOptionPane
									.showMessageDialog(
											null,
											"There are unsaved changes in the currently selected department."
													+ " Select 'Cancel' or 'Save' to view other departments.");
						}
					} else {
						Department department = departmentList.getSelectedValue();
						departmentEditMode(false);
						if (department != null)
							updateDepartmentDetails(department);
					}
					departmentListToolbar.repaint();
				}
				else
					hideAllComponents(departmentDetailsPane);
			}
		};
		departmentList
				.addListSelectionListener(departmentListSelectionListener);
		departmentListPane.setMinimumSize(new Dimension(300, 10));
		departmentListPane
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		departmentListPane.setBackground(UIManager
				.getColor("Button.background"));
		departmentTab.setLeftComponent(departmentListPane);

		departmentListToolbar.setFloatable(false);
		departmentListToolbar.setBorder(null);
		departmentListToolbar.setBackground(new Color(238, 238, 238));
		GridBagConstraints gbc_departmentListToolbar = new GridBagConstraints();
		gbc_departmentListToolbar.anchor = GridBagConstraints.SOUTH;
		gbc_departmentListToolbar.fill = GridBagConstraints.HORIZONTAL;
		gbc_departmentListToolbar.gridx = 0;
		gbc_departmentListToolbar.gridy = 2;
		departmentListPane
				.add(departmentListToolbar, gbc_departmentListToolbar);

		// Department Details Pane
		departmentDetailsPane.setBackground(standardPane);
		departmentTab.setRightComponent(departmentDetailsPane);
		GridBagLayout gbl_departmentDetailsPane = new GridBagLayout();
		gbl_departmentDetailsPane.columnWidths = new int[] { 10, 200, 200, 10,
				0, 0 };
		gbl_departmentDetailsPane.rowHeights = new int[] { 10, 0, 10, 40, 40,
				0, 0, 40, 0, 0, 10, 0, 0 };
		gbl_departmentDetailsPane.columnWeights = new double[] { 0.0, 0.0, 1.0,
				0.0, 0.0, Double.MIN_VALUE };
		gbl_departmentDetailsPane.rowWeights = new double[] { 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		departmentDetailsPane.setLayout(gbl_departmentDetailsPane);

		txtDepartmentName = new JTextField("No Selection");
		txtDepartmentName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtDepartmentName.setBackground(goodField);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!departmentForm.validateName("Department Name",
						txtDepartmentName.getText()))
					txtDepartmentName.setBackground(badField);
			}
		});
		txtDepartmentName.setToolTipText("Department Title");
		txtDepartmentName.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		GridBagConstraints gbc_lblDepartmentName = new GridBagConstraints();
		gbc_lblDepartmentName.gridwidth = 2;
		gbc_lblDepartmentName.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblDepartmentName.insets = new Insets(0, 0, 5, 5);
		gbc_lblDepartmentName.gridx = 1;
		gbc_lblDepartmentName.gridy = 1;
		txtDepartmentName.setColumns(25);
		departmentDetailsPane.add(txtDepartmentName, gbc_lblDepartmentName);
		txtDepartmentName.setMinimumSize(new Dimension(500, 35));

		JLabel lblDepartmentPhone = new JLabel("Department Phone:");
		GridBagConstraints gbc_lblDepartmentPhone = new GridBagConstraints();
		gbc_lblDepartmentPhone.anchor = GridBagConstraints.WEST;
		gbc_lblDepartmentPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblDepartmentPhone.gridx = 1;
		gbc_lblDepartmentPhone.gridy = 3;
		departmentDetailsPane.add(lblDepartmentPhone, gbc_lblDepartmentPhone);

		txtDepartmentPhone = new JTextField();
		txtDepartmentPhone.setToolTipText("Enter a departmental telephone.");
		txtDepartmentPhone.setColumns(10);
		txtDepartmentPhone.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtDepartmentPhone.setBackground(goodField);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!departmentForm.validatePhone("Department Phone",
						txtDepartmentPhone.getText()))
					txtDepartmentPhone.setBackground(badField);
				else
					txtDepartmentPhone.setText(departmentForm
							.formatPhone(txtDepartmentPhone.getText()));
			}
		});
		GridBagConstraints gbc_txtDepartmentPhone = new GridBagConstraints();
		gbc_txtDepartmentPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDepartmentPhone.insets = new Insets(0, 0, 5, 5);
		gbc_txtDepartmentPhone.gridx = 2;
		gbc_txtDepartmentPhone.gridy = 3;
		departmentDetailsPane.add(txtDepartmentPhone, gbc_txtDepartmentPhone);

		JLabel lblDepartmentAddress = new JLabel("Mailing Address:");
		GridBagConstraints gbc_lblDepartmentAddress = new GridBagConstraints();
		gbc_lblDepartmentAddress.anchor = GridBagConstraints.WEST;
		gbc_lblDepartmentAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblDepartmentAddress.gridx = 1;
		gbc_lblDepartmentAddress.gridy = 4;
		departmentDetailsPane.add(lblDepartmentAddress,
				gbc_lblDepartmentAddress);

		txtDepartmentAddress = new JTextField();
		txtDepartmentAddress.setToolTipText("Enter a department's address.");
		txtDepartmentAddress.setColumns(10);
		GridBagConstraints gbc_txtDepartmentAddress = new GridBagConstraints();
		gbc_txtDepartmentAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDepartmentAddress.insets = new Insets(0, 0, 5, 5);
		gbc_txtDepartmentAddress.gridx = 2;
		gbc_txtDepartmentAddress.gridy = 4;
		departmentDetailsPane.add(txtDepartmentAddress,
				gbc_txtDepartmentAddress);

		JLabel label_6 = new JLabel("");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.anchor = GridBagConstraints.WEST;
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 1;
		gbc_label_6.gridy = 5;
		departmentDetailsPane.add(label_6, gbc_label_6);

		JLabel lblEmployees = new JLabel("Employees:");
		GridBagConstraints gbc_lblEmployees = new GridBagConstraints();
		gbc_lblEmployees.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblEmployees.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmployees.gridx = 1;
		gbc_lblEmployees.gridy = 7;
		departmentDetailsPane.add(lblEmployees, gbc_lblEmployees);

		lstContacts.setBorder(new LineBorder(new Color(0, 0, 0)));
		lstContacts.setModel(new AbstractListModel() {
			String[] values = new String[] { "" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		JScrollPane lstContactsScrollPane = new JScrollPane(lstContacts);
		lstContactsScrollPane.setViewportBorder(new MatteBorder(1, 1, 1, 1,
				(Color) new Color(0, 0, 0)));
		lstContactsScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_lstEmployees = new GridBagConstraints();
		gbc_lstEmployees.gridwidth = 2;
		gbc_lstEmployees.insets = new Insets(0, 0, 5, 5);
		gbc_lstEmployees.fill = GridBagConstraints.BOTH;
		gbc_lstEmployees.gridx = 1;
		gbc_lstEmployees.gridy = 8;
		departmentDetailsPane.add(lstContactsScrollPane, gbc_lstEmployees);

		departmentEmployeeListToolbar.setBorder(null);
		GridBagConstraints gbc_departmentEmployeeListToolbar = new GridBagConstraints();
		gbc_departmentEmployeeListToolbar.gridwidth = 2;
		gbc_departmentEmployeeListToolbar.fill = GridBagConstraints.BOTH;
		gbc_departmentEmployeeListToolbar.insets = new Insets(0, 0, 5, 5);
		gbc_departmentEmployeeListToolbar.gridx = 1;
		gbc_departmentEmployeeListToolbar.gridy = 9;
		departmentDetailsPane.add(departmentEmployeeListToolbar,
				gbc_departmentEmployeeListToolbar);
		GridBagLayout gbl_departmentEmployeeListToolbar = new GridBagLayout();
		gbl_departmentEmployeeListToolbar.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_departmentEmployeeListToolbar.rowHeights = new int[] { 0, 0 };
		gbl_departmentEmployeeListToolbar.columnWeights = new double[] { 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		gbl_departmentEmployeeListToolbar.rowWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		departmentEmployeeListToolbar
				.setLayout(gbl_departmentEmployeeListToolbar);

		departmentToolbar.setFloatable(false);
		GridBagConstraints gbc_toolBar_3 = new GridBagConstraints();
		gbc_toolBar_3.anchor = GridBagConstraints.SOUTH;
		gbc_toolBar_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_toolBar_3.gridwidth = 8;
		gbc_toolBar_3.gridx = 0;
		gbc_toolBar_3.gridy = 11;
		departmentDetailsPane.add(departmentToolbar, gbc_toolBar_3);

		departmentEditMode(false);
		if (departmentList.getModel().getSize() != 0) {

			departmentList.setSelectedIndex(departmentList
					.getFirstVisibleIndex());
		}

		initializeDepartmentButtons();
		flattenSplitPane(departmentTab);
		
		hideAllComponents(departmentDetailsPane);
	}

	
	/**
	 * Initialize Department buttons
	 */
	private void initializeDepartmentButtons() {

		btnCreateDepartment.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/plus.png")));
		btnCreateDepartment.setToolTipText("Create New Department");
		departmentListToolbar.add(btnCreateDepartment);
		btnCreateDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// User has pressed the button to create a new department, not
				// available in edit mode			
				departmentList.clearSelection();
				showAllComponents(departmentDetailsPane);

				departmentForm = new FormValidator();
				departmentAddMode = true;

				// Clear all the current fields for entry of new data
				backupDepartment = new Department();
				updateDepartmentDetails(backupDepartment);
				SortableListModel<Contact> employees = new SortableListModel(backupDepartment.getEmployees());
				employees.sort();
				lstContacts.setModel(employees);

				// We must enter the editable mode.
				departmentEditMode(true);

				// From here the choice is up to the user, it can be saved or
				// discarded, once this is done the list is reloaded, which is
				// again handled by the save functionality.
			}
		});

		btnDeleteDepartment.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/delete.png")));
		btnDeleteDepartment.setToolTipText("Delete the selected department.");
		departmentListToolbar.add(btnDeleteDepartment);
		btnDeleteDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (departmentList.getSelectedValue() != null) {
					if (reportEditMode || reportAddMode)
					{
						JOptionPane.showMessageDialog(null, "Unable to delete selection.\n" +
								"Pending changes in another tab are preventing this action.\n" +
								"Submit changes and try again.");
					}
					else
					{
						int choice = JOptionPane.showOptionDialog(null, "Are you sure you want to delete '" +
								departmentList.getSelectedValue().getName() + "'?\n" +
								"This action cannot be undone.", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
						
						if (choice == JOptionPane.YES_OPTION)
						{
							final int departmentToDeleteKey = departmentList
									.getSelectedValue().getId();
							departmentManager.delete(departmentToDeleteKey);
							reportManager.reloadReportDepartmentRelations();
							reportList.getListSelectionListeners()[0].valueChanged(null);
							departmentList.clearSelection();
							departmentList.setModel(departmentManager.getListModelDepartment());
						}
					}
				}
				return;
			}
		});
		btnEditDepartment.setToolTipText("Edit the selected report.");

		btnEditDepartment.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/pencilTiny.png")));
		departmentToolbar.add(btnEditDepartment);
		btnEditDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// user has clicked the edit button
				departmentForm = new FormValidator();
				departmentEditMode = true;

				backupDepartment = departmentList.getSelectedValue();
				departmentEditMode(true);

			}
		});
		btnCancelDepartment
				.setToolTipText("Cancel all changes to current report.");

		btnCancelDepartment.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/thumbsDownTiny.png")));
		departmentToolbar.add(btnCancelDepartment);
		btnCancelDepartment.setVisible(false);
		btnCancelDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (departmentAddMode) {
					hideAllComponents(departmentDetailsPane);
				}
				// Revert all information from previous object.
				if (departmentEditMode) {
					updateDepartmentDetails(backupDepartment);
					departmentList.setSelectedValue(backupDepartment, true);
				}
				departmentEditMode(false);
				departmentAddMode = false;
				departmentEditMode = false;
			}
		});
		btnSaveDepartment.setToolTipText("Save the report to the database.");

		btnSaveDepartment.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/save.png")));
		departmentToolbar.add(btnSaveDepartment);
		btnSaveDepartment.setVisible(false);
		saveDept = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// User has clicked the SAVE button.

				// Lock down the changes
				try {
					if (lstContacts.getModel().getSize() == 0)
						departmentForm
								.addError("Select at least one employee.");
					else
						departmentForm
								.removeError("Select at least one employee.");
					if (departmentForm.valid()) {

						departmentEditMode(false);

						// Repopulate the list with the new results
						String name = txtDepartmentName.getText();
						String phone = txtDepartmentPhone.getText();
						String address = txtDepartmentAddress.getText();
						Vector<Contact> temp = new Vector<Contact>();
						for (int i = 0; i < lstContacts.getModel().getSize(); i++) {
							temp.addElement(lstContacts.getModel()
									.getElementAt(i));
						}

						if (departmentEditMode) {
							Department toUpdate = departmentList
									.getSelectedValue();
							toUpdate.setName(name);
							toUpdate.setPhone(phone);
							toUpdate.setAddress(address);
							toUpdate.setEmployees(temp);
							departmentManager.update(toUpdate);
						} else {
							Department departmentToInsert  = new Department(-1,name,"",phone,address,temp);

							departmentManager.insert(departmentToInsert);

						}

						departmentList
								.removeListSelectionListener(departmentListSelectionListener);
						departmentList.setModel(departmentManager
								.getListModelDepartment());
						departmentList
								.addListSelectionListener(departmentListSelectionListener);

						departmentAddMode = false;
						departmentEditMode = false;
						departmentList.clearSelection();
						departmentList.setSelectedValue(backupDepartment, true);

					}

				} catch (Exception k) {
					JOptionPane.showMessageDialog(null, k.getMessage());
				}
			}
		};
		btnSaveDepartment.addActionListener(saveDept);
		btnAddEmployeeToDept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContactsModal contactSelection = new ContactsModal(
						contactManager, lstContacts);
			}
		});

		btnAddEmployeeToDept
				.setToolTipText("Add employee contact to department");
		GridBagConstraints gbc_btnAddEmployeeToDept = new GridBagConstraints();
		gbc_btnAddEmployeeToDept.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddEmployeeToDept.gridx = 0;
		gbc_btnAddEmployeeToDept.gridy = 0;
		departmentEmployeeListToolbar.add(btnAddEmployeeToDept,
				gbc_btnAddEmployeeToDept);
		btnRemoveEmployeeFromDept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Remove the currently selected employees
				DefaultListModel<Contact> temp = new DefaultListModel<Contact>();

				for (int i = 0; i < lstContacts.getModel().getSize(); i++) {
					if (!lstContacts.getSelectedValuesList().contains(
							lstContacts.getModel().getElementAt(i))) {
						temp.addElement(lstContacts.getModel().getElementAt(i));
					}
				}

				lstContacts.setModel(temp);
			}
		});

		btnRemoveEmployeeFromDept
				.setToolTipText("Remove Employee from department");
		GridBagConstraints gbc_btnRemoveEmployeeFromDept = new GridBagConstraints();
		gbc_btnRemoveEmployeeFromDept.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemoveEmployeeFromDept.gridx = 1;
		gbc_btnRemoveEmployeeFromDept.gridy = 0;
		departmentEmployeeListToolbar.add(btnRemoveEmployeeFromDept,
				gbc_btnRemoveEmployeeFromDept);

		JSeparator departmentSeparator = new JSeparator();
		departmentToolbar.add(departmentSeparator);

	}

	/**
	 * Initialize the Report Tab
	 */
	@SuppressWarnings("rawtypes")
	private void initializeReportTab() {
		reportTab.setEnabled(false);

		Report selectedReport;
		preparedReport = new Report(-1, "", "", "", "", "", "", "", "", new Vector<Department>(),new Vector<Contact>());
		final FormValidator FormCheck = new FormValidator();

		// Report Tab
		JLabel lblReportTab = new JLabel("Reports");
		lblReportTab.setPreferredSize(new Dimension(200, 90));
		lblReportTab.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/collaborate.png")));
		// lblReportTab.setHorizontalTextPosition(10);
		tabsPane.addTab(null, null, reportTab, null);
		tabsPane.setTabComponentAt(2, lblReportTab);
		tabsPane.setEnabledAt(2, true);
		
		if (isFirstRun)
			return;
		
		// Report List Pane
		GridBagLayout gbl_reportListPane = new GridBagLayout();
		gbl_reportListPane.columnWidths = new int[] { 300, 0 };
		gbl_reportListPane.rowHeights = new int[] { 10, 50, 10, 0 };
		gbl_reportListPane.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_reportListPane.rowWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		reportListPane.setLayout(gbl_reportListPane);
		reportList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reportList.setModel(reportManager.getListModelReports());
		reportList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				Report mySelect = reportList.getSelectedValue();
				
				if(mySelect != null){
					showAllComponents(reportDetailsPane);
					if (reportAddMode)
					{
						reportList.clearSelection();
						showAllComponents(reportDetailsPane);
						JOptionPane
						.showMessageDialog(
								null,
								"There are unsaved changes in the currently selected report."
										+ " Select 'Cancel' or 'Save' to view other reports.");
					}
					else if(reportEditMode){
						if (mySelect != backupReport){
							//Do not allow the user to change the selection during edit mode
							reportList.setSelectedValue(backupReport, true);
							JOptionPane
							.showMessageDialog(
									null,
									"There are unsaved changes in the currently selected report."
											+ " Select 'Cancel' or 'Save' to view other reports.");
						}
					}
					else{
						//NOT in edit mode
						final Report temp = new Report(mySelect.getReportId(),mySelect.getTitle(), mySelect.getStartDate(),mySelect.getEndDate(),mySelect.getDimensions(),mySelect.getMetrics()
								,mySelect.getFilter(),mySelect.getSort(),Integer.toString(mySelect.getMaxResults()),mySelect.getDepartments(),mySelect.getRecipients());
						
						updateReportDetails(mySelect);
						//Workaround for editing the selected object
						
						preparedReport = temp;
						//Otherwise no change has really been made...
					}
					reportListToolbar.repaint();
					
				}
				else{
					hideAllComponents(reportDetailsPane);
				}
			}
		});
		
		JLabel lblReportSearch = new JLabel("");
		lblReportSearch.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/search.png")));
		GridBagConstraints gbc_reportSearch = new GridBagConstraints();
		gbc_reportSearch.anchor = GridBagConstraints.NORTH;
		gbc_reportSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_reportSearch.gridx = 0;
		gbc_reportSearch.gridy = 0;
		reportSearch.setFloatable(false);
		reportListPane.add(reportSearch, gbc_reportSearch);
		reportSearch.add(lblReportSearch);
		
		txtReportSearch = new JTextField();
		reportSearch.add(txtReportSearch);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		txtReportSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if ((txtReportSearch.getText() != null)
						&& (txtReportSearch.getText() != "")) {
					searchReportResults = reportManager
							.searchReportsByName(txtReportSearch.getText()); 
					reportList.setModel(searchReportResults);
					// Set first selection
					if (reportList.getModel().getSize() != 0) {
						reportList.setSelectedIndex(reportList
								.getFirstVisibleIndex());
					}
					else
						reportList.clearSelection();
				} else {
					// replace entire unfiltered report list
					reportList.setModel(reportManager.getListModelReports());
					// Set first selection
					if (reportList.getModel().getSize() != 0) {
						reportList.setSelectedIndex(reportList
								.getFirstVisibleIndex());
					}
					else
						reportList.clearSelection();
				}
			}//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		});
		txtReportSearch.setColumns(10);

		reportSearch.setBorder(new BevelBorder(BevelBorder.RAISED, null, null,
				null, null));
		reportSearch.setBackground(SystemColor.controlHighlight);
		
		reportList.setToolTipText("All report profiles.");
		

		JScrollPane reportScrollPane = new JScrollPane(reportList);
		reportScrollPane.setViewportBorder(new MatteBorder(1, 1, 1, 1,
				(Color) new Color(0, 0, 0)));
		reportScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		reportScrollPane.setPreferredSize(new Dimension(280, 0));
		reportScrollPane.setOpaque(false);
		reportScrollPane
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		GridBagConstraints gbc_reportScrollPane = new GridBagConstraints();
		gbc_reportScrollPane.fill = GridBagConstraints.BOTH;
		gbc_reportScrollPane.gridx = 0;
		gbc_reportScrollPane.gridy = 1;
		reportListPane.add(reportScrollPane, gbc_reportScrollPane);
		reportListPane.setMinimumSize(new Dimension(300, 10));
		reportListPane.setPreferredSize(new Dimension(300, 15));
		reportListPane
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		reportListPane.setBackground(SystemColor.textHighlight);
		reportTab.setLeftComponent(reportListPane);

		reportListToolbar.setFloatable(false);
		reportListToolbar.setBorder(null);
		reportListToolbar.setBackground(new Color(238, 238, 238));
		GridBagConstraints gbc_reportListToolbar = new GridBagConstraints();
		gbc_reportListToolbar.anchor = GridBagConstraints.SOUTH;
		gbc_reportListToolbar.fill = GridBagConstraints.HORIZONTAL;
		gbc_reportListToolbar.gridx = 0;
		gbc_reportListToolbar.gridy = 2;
		reportListPane.add(reportListToolbar, gbc_reportListToolbar);

		// Report Details Pane
		reportDetailsPane.setBackground(standardPane);
		reportDetailsPane.setBorder(null);
		reportTab.setRightComponent(reportDetailsPane);
		GridBagLayout gbl_reportDetailsPane = new GridBagLayout();
		gbl_reportDetailsPane.columnWidths = new int[] { 10, 110, 253, 271,
				185, 10 };
		gbl_reportDetailsPane.rowHeights = new int[] { 10, 0, 10, 31, 28, 0, 0,
				28, 16, 16, 28, 28, 0, 0, 37, 0, 100, 50, 0, 0, 0 };
		gbl_reportDetailsPane.columnWeights = new double[] { 0.0, 0.0, 1.0,
				1.0, 1.0, 0.0 };
		gbl_reportDetailsPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		reportDetailsPane.setLayout(gbl_reportDetailsPane);

		txtReportTitleText = new JTextField("New Report");
		txtReportTitleText.setToolTipText("Enter a report title.");
		txtReportTitleText.setHorizontalAlignment(SwingConstants.LEFT);
		txtReportTitleText.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		txtReportTitleText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (txtReportTitleText.getText().length() < 0) {
					txtReportTitleText.setBackground(FormCheck.getBadColor());
				} else {
					txtReportTitleText.setBackground(FormCheck.getGoodColor());
					preparedReport.SetTitle(txtReportTitleText.getText());
				}
			}
		});
		GridBagConstraints gbc_txtReportTitleText = new GridBagConstraints();
		gbc_txtReportTitleText.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtReportTitleText.insets = new Insets(0, 0, 5, 5);
		gbc_txtReportTitleText.gridwidth = 4;
		gbc_txtReportTitleText.gridx = 1;
		gbc_txtReportTitleText.gridy = 1;
		reportDetailsPane.add(txtReportTitleText, gbc_txtReportTitleText);
		txtReportTitleText.setColumns(25);
		txtReportTitleText.setMinimumSize(new Dimension(600, 35));
		final SimpleDateFormat fmat = new SimpleDateFormat("yyyy-MM-dd");

		JLabel lblDimensions = new JLabel("Dimensions:");
		GridBagConstraints gbc_lblDimensions = new GridBagConstraints();
		gbc_lblDimensions.gridwidth = 2;
		gbc_lblDimensions.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblDimensions.insets = new Insets(0, 0, 5, 5);
		gbc_lblDimensions.gridx = 1;
		gbc_lblDimensions.gridy = 3;
		reportDetailsPane.add(lblDimensions, gbc_lblDimensions);

		JLabel lblMetrics = new JLabel("Metric:");
		GridBagConstraints gbc_lblMetrics = new GridBagConstraints();
		gbc_lblMetrics.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblMetrics.insets = new Insets(0, 0, 5, 5);
		gbc_lblMetrics.gridx = 3;
		gbc_lblMetrics.gridy = 3;
		reportDetailsPane.add(lblMetrics, gbc_lblMetrics);

		
		lstMetrics_1.setToolTipText("Select at least one metric.");
		lstMetrics_1.setSelectionModel(new treeMultiSelectModel());
		lstMetrics_1.addTreeSelectionListener(new TreeSelectionListener() {
			@SuppressWarnings("null")
			public void valueChanged(TreeSelectionEvent e) {
				TreePath[] selected = lstMetrics_1.getSelectionModel()
						.getSelectionPaths();

				boolean Max = false;

				if (selected == null) {
					preparedReport.RemoveAllMetrics();
				} else {
					preparedReport.RemoveAllMetrics();

					for (int i = 0; i < selected.length; i++) {
						if (selected[i].getLastPathComponent().toString()
								.matches("ga:.+")) {
							if (!preparedReport.AddMetric(selected[i]
									.getLastPathComponent().toString())) {
								lstMetrics_1.removeSelectionPath(selected[i]);
								Max = true;
							}
						}
					}

				}
				ActionListener listenSortOn = boxSortOn.getActionListeners()[0];
				boxSortOn.removeActionListener(listenSortOn);
				ActionListener listenFilterOn = boxFilterOn
						.getActionListeners()[0];
				boxFilterOn.removeActionListener(listenFilterOn);

				ActionListener listenFilterBy = boxFilterBy
						.getActionListeners()[0];
				boxFilterBy.removeActionListener(listenFilterBy);

				boxSortOn.removeAllItems();
				boxFilterOn.removeAllItems();
				for (int i = 0; i < preparedReport.DimensionCount(); i++) {
					boxSortOn.addItem(preparedReport.getDimension(i));
					boxFilterOn.addItem(preparedReport.getDimension(i));
				}
				for (int i = 0; i < preparedReport.MetricCount(); i++) {
					boxSortOn.addItem(preparedReport.getMetric(i));
					boxFilterOn.addItem(preparedReport.getMetric(i));
				}
				if (Max) {
					JOptionPane.showMessageDialog(null, "Error",
							"You selected too many metrics.",
							JOptionPane.ERROR_MESSAGE);
				}
				boxFilterOn.addActionListener(listenFilterOn);
				boxFilterBy.addActionListener(listenFilterBy);
				boxSortOn.addActionListener(listenSortOn);
			}
		});
		lstMetrics_1.setModel(new DefaultTreeModel(treeMetrics.metrics));
		lstMetrics_1.setBorder(UIManager
				.getBorder("InternalFrame.optionDialogBorder"));

		JScrollPane lstMetricsContainer = new JScrollPane(lstMetrics_1);
		lstMetricsContainer.setMinimumSize(new Dimension(20, 110));
		GridBagConstraints gbc_lstMetricsContainer = new GridBagConstraints();
		gbc_lstMetricsContainer.gridwidth = 2;
		gbc_lstMetricsContainer.fill = GridBagConstraints.BOTH;
		gbc_lstMetricsContainer.insets = new Insets(0, 0, 5, 5);
		gbc_lstMetricsContainer.gridx = 3;
		gbc_lstMetricsContainer.gridy = 4;
		reportDetailsPane.add(lstMetricsContainer, gbc_lstMetricsContainer);

		JLabel lblStartDate = new JLabel("Start Date:");
		GridBagConstraints gbc_lblStartDate = new GridBagConstraints();
		gbc_lblStartDate.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblStartDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartDate.gridx = 1;
		gbc_lblStartDate.gridy = 8;
		reportDetailsPane.add(lblStartDate, gbc_lblStartDate);
		boxStartDate = new JDateChooser();
		boxStartDate.setToolTipText("Select a start date.");

		boxStartDate.setDateFormatString("yyyy-MM-dd");
		((JTextFieldDateEditor) ((JDateChooser) boxStartDate).getDateEditor())
		.setEditable(false);
		boxStartDate.setMaxSelectableDate(new Date(
				System.currentTimeMillis() - 1440 * 60000));
		boxStartDate.setDate(new Date(
				System.currentTimeMillis() - 10080 * 60000));
		preparedReport.SetStartDate(fmat.format(boxStartDate.getDate()));

		boxStartDate.getDateEditor().addPropertyChangeListener(
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {

						if ("date".equals(e.getPropertyName())) {

							preparedReport.SetStartDate(fmat.format(boxStartDate
									.getDate()));
						}
					}
				});
		GridBagConstraints gbc_boxStartDate = new GridBagConstraints();
		gbc_boxStartDate.fill = GridBagConstraints.BOTH;
		gbc_boxStartDate.insets = new Insets(0, 0, 5, 5);
		gbc_boxStartDate.gridx = 2;
		gbc_boxStartDate.gridy = 8;
		reportDetailsPane.add(boxStartDate, gbc_boxStartDate);

		JLabel lblEndDate = new JLabel("End Date:");
		GridBagConstraints gbc_lblEndDate = new GridBagConstraints();
		gbc_lblEndDate.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblEndDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblEndDate.gridx = 1;
		gbc_lblEndDate.gridy = 9;
		reportDetailsPane.add(lblEndDate, gbc_lblEndDate);

		boxEndDate = new JDateChooser();
		boxEndDate.setToolTipText("Select an end date.");
		boxEndDate.setDateFormatString("yyyy-MM-dd");
		((JTextFieldDateEditor) ((JDateChooser) boxEndDate).getDateEditor())
		.setEditable(false);
		boxEndDate.setMaxSelectableDate(new Date());
		boxEndDate.setDate(new Date(System.currentTimeMillis() - 1440 * 60000));
		preparedReport.SetEndDate(fmat.format(boxEndDate.getDate()));

		boxEndDate.getDateEditor().addPropertyChangeListener(
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {

						if ("date".equals(e.getPropertyName())) {

							preparedReport.SetEndDate(fmat.format(boxEndDate
									.getDate()));
						}
					}
				});
		GridBagConstraints gbc_boxEndDate = new GridBagConstraints();
		gbc_boxEndDate.fill = GridBagConstraints.BOTH;
		gbc_boxEndDate.insets = new Insets(0, 0, 5, 5);
		gbc_boxEndDate.gridx = 2;
		gbc_boxEndDate.gridy = 9;
		reportDetailsPane.add(boxEndDate, gbc_boxEndDate);

		boxSortOn = new JComboBox();
		boxSortOn.setToolTipText("Select an item to sort by.");

		boxSortOn.setModel(new DefaultComboBoxModel(new String[] { "" }));
		
		GridBagConstraints gbc_boxSortOn = new GridBagConstraints();
		gbc_boxSortOn.fill = GridBagConstraints.HORIZONTAL;
		gbc_boxSortOn.insets = new Insets(0, 0, 5, 5);
		gbc_boxSortOn.gridx = 2;
		gbc_boxSortOn.gridy = 10;
		reportDetailsPane.add(boxSortOn, gbc_boxSortOn);

		boxSortOrder = new JComboBox();
		boxSortOrder.setToolTipText("Select a direction to sort.");
		boxSortOrder.setModel(new DefaultComboBoxModel(new String[] {"Ascending", "Descending"}));
		GridBagConstraints gbc_boxSortOrder = new GridBagConstraints();
		gbc_boxSortOrder.fill = GridBagConstraints.HORIZONTAL;
		gbc_boxSortOrder.insets = new Insets(0, 0, 5, 5);
		gbc_boxSortOrder.gridx = 3;
		gbc_boxSortOrder.gridy = 10;

		boxSortOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (boxSortOrder.getSelectedItem().toString()
						.equalsIgnoreCase("ascending")) {
					// preparedReport.SetSort(boxSortOn.getSelectedItem().toString());
				} else {
					// preparedReport.SetSort("-".concat(boxSortOn.getSelectedItem().toString()));
				}

			}
		});

		boxSortOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (boxSortOrder.getSelectedItem().toString()
						.equalsIgnoreCase("ascending")) {
					// preparedReport.SetSort(boxSortOn.getSelectedItem().toString());
				} else {
					// preparedReport.SetSort("-".concat(boxSortOn.getSelectedItem().toString()));
				}

			}
		});
		reportDetailsPane.add(boxSortOrder, gbc_boxSortOrder);

		JLabel lblFilter = new JLabel("Filter:");
		GridBagConstraints gbc_lblFilter = new GridBagConstraints();
		gbc_lblFilter.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblFilter.insets = new Insets(0, 0, 5, 5);
		gbc_lblFilter.gridx = 1;
		gbc_lblFilter.gridy = 11;
		reportDetailsPane.add(lblFilter, gbc_lblFilter);

		boxFilterOn = new JComboBox();
		boxFilterOn.setToolTipText("Select an element according to filter.");

		boxFilterOn.setModel(new DefaultComboBoxModel(new String[] { "" }));
		GridBagConstraints gbc_boxFilterOn = new GridBagConstraints();
		gbc_boxFilterOn.fill = GridBagConstraints.HORIZONTAL;
		gbc_boxFilterOn.insets = new Insets(0, 0, 5, 5);
		gbc_boxFilterOn.gridx = 2;
		gbc_boxFilterOn.gridy = 11;
		reportDetailsPane.add(boxFilterOn, gbc_boxFilterOn);
		boxFilterBy = new JComboBox();
		boxFilterBy.setToolTipText("Select an operator to filter.");
		
		lstDimensions.setToolTipText("Select a dimension.");
		lstDimensions.setSelectionModel(new treeMultiSelectModel());
		lstDimensions.addTreeSelectionListener(new TreeSelectionListener() {
			@SuppressWarnings("null")
			public void valueChanged(TreeSelectionEvent e) {
				TreePath[] selected = lstDimensions.getSelectionModel()
						.getSelectionPaths();

				boolean Max = false;

				if (selected == null) {
					preparedReport.RemoveAllDimensions();
				} else {
					preparedReport.RemoveAllDimensions();
					for (int i = 0; i < selected.length; i++) {

						if (selected[i].getLastPathComponent().toString()
								.matches("ga:.+")) {
							if (!preparedReport.addDimension(selected[i]
									.getLastPathComponent().toString())) {
								lstDimensions.removeSelectionPath(selected[i]);
								Max = true;
							}
						} else if (selected[i].getPath().length == 2) {
							// Deal with the selection of a parent node
							//selected[i].getPath()
							// lstDimensions.getSelectionModel().addSelectionPaths(childrenPaths)
						}

					}
				}
				ActionListener listenFilterOn = boxFilterOn
						.getActionListeners()[0];
				boxFilterOn.removeActionListener(listenFilterOn);

				ActionListener listenFilterBy = boxFilterBy
						.getActionListeners()[0];
				boxFilterBy.removeActionListener(listenFilterBy);

				ActionListener listenSortOn = boxSortOn.getActionListeners()[0];
				boxSortOn.removeActionListener(listenSortOn);

				boxSortOn.removeAllItems();
				boxFilterOn.removeAllItems();

				boxSortOn.removeAllItems();
				boxFilterOn.removeAllItems();
				for (int i = 0; i < preparedReport.DimensionCount(); i++) {
					boxSortOn.addItem(preparedReport.getDimension(i));
					boxFilterOn.addItem(preparedReport.getDimension(i));
				}
				for (int i = 0; i < preparedReport.MetricCount(); i++) {
					boxSortOn.addItem(preparedReport.getMetric(i));
					boxFilterOn.addItem(preparedReport.getMetric(i));
				}
				if (Max) {
					JOptionPane.showMessageDialog(null, "Error",
							"You selected too many dimensions",
							JOptionPane.ERROR_MESSAGE);
				}
				boxFilterOn.addActionListener(listenFilterOn);
				boxFilterBy.addActionListener(listenFilterBy);
				boxSortOn.addActionListener(listenSortOn);
				return;
			}

		});
		lstDimensions.setModel(new DefaultTreeModel(treeDimensions.dimensions));
		lstDimensions.setBorder(UIManager
				.getBorder("InternalFrame.optionDialogBorder"));

		JScrollPane lstDimensionsContainer = new JScrollPane(lstDimensions);
		lstDimensionsContainer.setMinimumSize(new Dimension(20, 110));
		GridBagConstraints gbc_lstDimensionsContainer = new GridBagConstraints();
		gbc_lstDimensionsContainer.gridwidth = 2;
		gbc_lstDimensionsContainer.fill = GridBagConstraints.BOTH;
		gbc_lstDimensionsContainer.insets = new Insets(0, 0, 5, 5);
		gbc_lstDimensionsContainer.gridx = 1;
		gbc_lstDimensionsContainer.gridy = 4;
		reportDetailsPane.add(lstDimensionsContainer,
				gbc_lstDimensionsContainer);

		JSeparator separator1 = new JSeparator();
		GridBagConstraints gbc_separator1 = new GridBagConstraints();
		gbc_separator1.insets = new Insets(0, 0, 5, 5);
		gbc_separator1.gridx = 1;
		gbc_separator1.gridy = 5;
		reportDetailsPane.add(separator1, gbc_separator1);

		JLabel lblSort = new JLabel("Sort:");
		GridBagConstraints gbc_lblSort = new GridBagConstraints();
		gbc_lblSort.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblSort.insets = new Insets(0, 0, 5, 5);
		gbc_lblSort.gridx = 1;
		gbc_lblSort.gridy = 10;
		reportDetailsPane.add(lblSort, gbc_lblSort);

		boxFilterBy.setModel(new DefaultComboBoxModel(new String[] { "!=",
				"==", "<", ">" }));
		GridBagConstraints gbc_boxFilterBy = new GridBagConstraints();
		gbc_boxFilterBy.fill = GridBagConstraints.HORIZONTAL;
		gbc_boxFilterBy.insets = new Insets(0, 0, 5, 5);
		gbc_boxFilterBy.gridx = 3;
		gbc_boxFilterBy.gridy = 11;
		reportDetailsPane.add(boxFilterBy, gbc_boxFilterBy);

		txtFilterText = new JFormattedTextField("");
		txtFilterText.setToolTipText("Enter a filter expression.");

		boxFilterOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				preparedReport.SetFilter(boxFilterOn
						.getSelectedItem()
						.toString()
						.concat(boxFilterBy.getSelectedItem().toString()
								.concat(txtFilterText.getText())));
			}
		});
		boxFilterBy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				preparedReport.SetFilter(boxFilterOn
						.getSelectedItem()
						.toString()
						.concat(boxFilterBy.getSelectedItem().toString()
								.concat(txtFilterText.getText())));
			}
		});
		txtFilterText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				preparedReport.SetFilter(boxFilterOn
						.getSelectedItem()
						.toString()
						.concat(boxFilterBy.getSelectedItem().toString()
								.concat(txtFilterText.getText())));
			}
		});
		txtFilterText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (txtFilterText.getText().length() == 0) {
					preparedReport.SetFilter("");
				} else {
					preparedReport.SetFilter(boxFilterOn
							.getSelectedItem()
							.toString()
							.concat(boxFilterBy.getSelectedItem().toString()
									.concat(txtFilterText.getText())));
				}
			}
		});

		GridBagConstraints gbc_txtFilterText = new GridBagConstraints();
		gbc_txtFilterText.fill = GridBagConstraints.BOTH;
		gbc_txtFilterText.insets = new Insets(0, 0, 5, 5);
		gbc_txtFilterText.gridx = 4;
		gbc_txtFilterText.gridy = 11;
		reportDetailsPane.add(txtFilterText, gbc_txtFilterText);

		JLabel lblMaxResults = new JLabel("Max Results:");
		GridBagConstraints gbc_lblMaxResults = new GridBagConstraints();
		gbc_lblMaxResults.anchor = GridBagConstraints.WEST;
		gbc_lblMaxResults.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxResults.gridx = 1;
		gbc_lblMaxResults.gridy = 13;
		reportDetailsPane.add(lblMaxResults, gbc_lblMaxResults);

		boxMaxResults = new JComboBox();
		boxMaxResults.setToolTipText("Select the maximum number of results.");
		boxMaxResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				preparedReport.SetMaxResults(boxMaxResults.getSelectedItem()
						.toString());
			}
		});
		boxMaxResults.setModel(new DefaultComboBoxModel(new String[] { "10",
				"20", "50", "100", "1000" }));
		GridBagConstraints gbc_boxMaxResults = new GridBagConstraints();
		gbc_boxMaxResults.insets = new Insets(0, 0, 5, 5);
		gbc_boxMaxResults.fill = GridBagConstraints.HORIZONTAL;
		gbc_boxMaxResults.gridx = 2;
		gbc_boxMaxResults.gridy = 13;
		reportDetailsPane.add(boxMaxResults, gbc_boxMaxResults);

		JLabel lblRecipients = new JLabel("Recipients:");
		GridBagConstraints gbc_lblRecipients = new GridBagConstraints();
		gbc_lblRecipients.anchor = GridBagConstraints.WEST;
		gbc_lblRecipients.insets = new Insets(0, 0, 5, 5);
		gbc_lblRecipients.gridx = 1;
		gbc_lblRecipients.gridy = 14;
		reportDetailsPane.add(lblRecipients, gbc_lblRecipients);

		JLabel lblReportContactList = new JLabel("Contacts");
		GridBagConstraints gbc_lblReportContactList = new GridBagConstraints();
		gbc_lblReportContactList.gridwidth = 2;
		gbc_lblReportContactList.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblReportContactList.insets = new Insets(0, 0, 5, 5);
		gbc_lblReportContactList.gridx = 1;
		gbc_lblReportContactList.gridy = 15;
		reportDetailsPane.add(lblReportContactList, gbc_lblReportContactList);

		JLabel lblReportDepartmentList = new JLabel("Departments");
		GridBagConstraints gbc_lblReportDepartmentList = new GridBagConstraints();
		gbc_lblReportDepartmentList.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblReportDepartmentList.insets = new Insets(0, 0, 5, 5);
		gbc_lblReportDepartmentList.gridx = 3;
		gbc_lblReportDepartmentList.gridy = 15;
		reportDetailsPane.add(lblReportDepartmentList,
				gbc_lblReportDepartmentList);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 16;
		reportDetailsPane.add(scrollPane, gbc_scrollPane);
		// /svn commit
		reportContactList = new JList<Contact>();
		reportContactList.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(reportContactList);

		reportDepartmentList = new JList<Department>();
		reportDepartmentList.setBorder(new LineBorder(new Color(0, 0, 0)));

		GridBagConstraints gbc_reportDepartmentList = new GridBagConstraints();
		gbc_reportDepartmentList.gridwidth = 2;
		gbc_reportDepartmentList.insets = new Insets(0, 0, 5, 5);
		gbc_reportDepartmentList.fill = GridBagConstraints.BOTH;
		gbc_reportDepartmentList.gridx = 3;
		gbc_reportDepartmentList.gridy = 16;
		reportDetailsPane.add(reportDepartmentList, gbc_reportDepartmentList);

		reportContactListToolbar.setBorder(null);
		GridBagConstraints gbc_reportContactListToolbar = new GridBagConstraints();
		gbc_reportContactListToolbar.gridwidth = 2;
		gbc_reportContactListToolbar.insets = new Insets(0, 0, 5, 5);
		gbc_reportContactListToolbar.fill = GridBagConstraints.BOTH;
		gbc_reportContactListToolbar.gridx = 1;
		gbc_reportContactListToolbar.gridy = 17;
		reportDetailsPane.add(reportContactListToolbar,
				gbc_reportContactListToolbar);
		GridBagLayout gbl_reportContactListToolbar = new GridBagLayout();
		gbl_reportContactListToolbar.columnWidths = new int[] { 0, 0, 0 };
		gbl_reportContactListToolbar.rowHeights = new int[] { 0, 0 };
		gbl_reportContactListToolbar.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_reportContactListToolbar.rowWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		reportContactListToolbar.setLayout(gbl_reportContactListToolbar);

		JButton btnContactAdd = new JButton("Add");
		btnContactAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				ContactsModal reportContact = new ContactsModal(contactManager,
						reportContactList);
			}
		});
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 0, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 0;
		reportContactListToolbar.add(btnContactAdd, gbc_btnAdd);

		JButton btnContactRemove = new JButton("Remove");
		btnContactRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Remove the currently selected employees
				DefaultListModel<Contact> temp = new DefaultListModel<Contact>();

				for (int i = 0; i < reportContactList.getModel().getSize(); i++) {
					if (!reportContactList.getSelectedValuesList().contains(
							reportContactList.getModel().getElementAt(i))) {
						temp.addElement(reportContactList.getModel()
								.getElementAt(i));
					}
				}

				reportContactList.setModel(temp);
			}
		});
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.gridx = 1;
		gbc_btnRemove.gridy = 0;
		reportContactListToolbar.add(btnContactRemove, gbc_btnRemove);

		reportDepartmentListToolbar.setBorder(null);
		GridBagConstraints gbc_reportDepartmentListToolbar = new GridBagConstraints();
		gbc_reportDepartmentListToolbar.gridwidth = 2;
		gbc_reportDepartmentListToolbar.insets = new Insets(0, 0, 5, 5);
		gbc_reportDepartmentListToolbar.fill = GridBagConstraints.BOTH;
		gbc_reportDepartmentListToolbar.gridx = 3;
		gbc_reportDepartmentListToolbar.gridy = 17;
		reportDetailsPane.add(reportDepartmentListToolbar,
				gbc_reportDepartmentListToolbar);
		GridBagLayout gbl_reportDepartmentListToolbar = new GridBagLayout();
		gbl_reportDepartmentListToolbar.columnWidths = new int[] { 0, 0, 0 };
		gbl_reportDepartmentListToolbar.rowHeights = new int[] { 0, 0 };
		gbl_reportDepartmentListToolbar.columnWeights = new double[] { 0.0,
				0.0, Double.MIN_VALUE };
		gbl_reportDepartmentListToolbar.rowWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		reportDepartmentListToolbar.setLayout(gbl_reportDepartmentListToolbar);

		JButton btnDepartmentAdd = new JButton("Add");
		btnDepartmentAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DepartmentsModal reportDepartments = new DepartmentsModal(
						departmentManager, reportDepartmentList);
			}
		});
		GridBagConstraints gbc_btnDepartmentAdd = new GridBagConstraints();
		gbc_btnDepartmentAdd.insets = new Insets(0, 0, 0, 5);
		gbc_btnDepartmentAdd.gridx = 0;
		gbc_btnDepartmentAdd.gridy = 0;
		reportDepartmentListToolbar.add(btnDepartmentAdd, gbc_btnDepartmentAdd);

		JButton btnDepartmentRemove = new JButton("Remove");
		btnDepartmentRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Remove the currently selected employees
				DefaultListModel<Department> temp = new DefaultListModel<Department>();

				for (int i = 0; i < reportDepartmentList.getModel().getSize(); i++) {
					if (!reportDepartmentList.getSelectedValuesList().contains(
							reportDepartmentList.getModel().getElementAt(i))) {
						temp.addElement(reportDepartmentList.getModel()
								.getElementAt(i));
					}
				}

				reportDepartmentList.setModel(temp);
			}
		});
		GridBagConstraints gbc_btnDepartmentRemove = new GridBagConstraints();
		gbc_btnDepartmentRemove.gridx = 1;
		gbc_btnDepartmentRemove.gridy = 0;
		reportDepartmentListToolbar.add(btnDepartmentRemove, gbc_btnDepartmentRemove);

		reportToolbar.setBackground(new Color(238, 238, 238));
		reportToolbar.setFloatable(false);
		GridBagConstraints gbc_reportToolbar = new GridBagConstraints();
		gbc_reportToolbar.anchor = GridBagConstraints.SOUTH;
		gbc_reportToolbar.fill = GridBagConstraints.HORIZONTAL;
		gbc_reportToolbar.gridwidth = 6;
		gbc_reportToolbar.gridx = 0;
		gbc_reportToolbar.gridy = 19;
		reportDetailsPane.add(reportToolbar, gbc_reportToolbar);
		initializeReportButtons();
		preparedReport.SetMaxResults(boxMaxResults.getSelectedItem().toString());
		preparedReport.SetTitle(txtReportTitleText.getText());
		
		//Allow JTrees to change on selection
		lstDimensions.setExpandsSelectedPaths(true);
		lstMetrics_1.setExpandsSelectedPaths(true);

		//Expand by default
		for (int i = 0; i < lstDimensions.getRowCount(); i++) {
		         lstDimensions.expandRow(i);
		}
		
		for (int i = 0; i < lstMetrics_1.getRowCount(); i++) {
	         lstMetrics_1.expandRow(i);
		}

		flattenSplitPane(reportTab);
		//Initially display in non-mutable mode
		reportEditMode(false);
		//Hide the components in the reports panel
		
		hideAllComponents(reportDetailsPane);
		
	}

	
	/**
	 * Initializes Report buttons
	 */
	private void initializeReportButtons() {
		btnEditReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reportEditMode = true;
				backupReport = reportList.getSelectedValue();
				reportEditMode(true);
				return;
			}
		});
		btnEditReport.setToolTipText("Edit report profile.");
		btnEditReport.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/pencilTiny.png")));
		reportToolbar.add(btnEditReport);
		btnCancelReport.setToolTipText("Cancel all changes.");

		btnCancelReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (reportAddMode)
				{
					reportList.clearSelection();
					hideAllComponents(reportDetailsPane);
				}
				else
				{
					updateReportDetails(backupReport);
					reportList.setSelectedValue(backupReport, true);
				}
				reportAddMode = false;
				reportEditMode = false;
				reportEditMode(false);
			}
		});
		btnCancelReport.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/thumbsDownTiny.png")));
		reportToolbar.add(btnCancelReport);
		btnSaveReport.setToolTipText("Save report to database.");

		btnSaveReport.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/save.png")));
		reportToolbar.add(btnSaveReport);

		JSeparator reportSeparator = new JSeparator();
		reportSeparator.setOrientation(SwingConstants.VERTICAL);
		reportToolbar.add(reportSeparator);
		btnPreviewReport.setToolTipText("Preview Report.");
		btnPreviewReport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (checkReportFields()) {
					processReport();
					try {
						if(txtFilterText.getText() == null || txtFilterText.getText().equals("")){
							preparedReport.SetFilter("EMPTY");
						}
						
						PreviewWindow prev = new PreviewWindow(reportManager.previewReport(preparedReport,
								improvedSettings.getSettings().getFormatResults()));
					} catch (Throwable e1) {
						JOptionPane.showMessageDialog(null, "Unable to preview.\n" + e1.getMessage());
						e1.printStackTrace();
					}
				}

			}
		});

		btnPreviewReport.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/loadTiny.png")));
		reportToolbar.add(btnPreviewReport);
		btnSendReport.setToolTipText("Send Report via email.");
		btnSendReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (checkReportFields()) {		
					processReport();
					if(txtFilterText.getText() == null || txtFilterText.getText().equals("")){
						preparedReport.SetFilter("EMPTY");
					}
					//Open the interface window, and pass it the reportManager and the preparedReport
					eMailWindow emailWindow = new eMailWindow(reportManager, preparedReport, 
							improvedSettings.getSettings().getFormatResults());
					
				}
			}
		});

		btnSendReport.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/mailTiny.png")));
		reportToolbar.add(btnSendReport);
		btnCreateReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	
		btnCreateReport.setToolTipText("Add a new report.");
		btnCreateReport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				backupReport = new Report();
				reportList.clearSelection();
				showAllComponents(reportDetailsPane);
				
				reportEditMode(true);
				updateReportDetails(backupReport);
				reportAddMode = true;
				
			}
		});

		reportListToolbar.add(btnCreateReport);
		btnCreateReport.setHorizontalAlignment(SwingConstants.LEADING);
		btnCreateReport.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/plus.png")));
		btnDeleteReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(reportList.getSelectedIndex() != -1)
				{
					int choice = JOptionPane.showOptionDialog(null, "Are you sure you want to delete '" +
							reportList.getSelectedValue().getTitle() + "'?\n" +
							"This action cannot be undone.", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					
					if (choice == JOptionPane.YES_OPTION)
					{
						reportManager.delete(reportList.getSelectedValue().getReportId());
						reportList.clearSelection();
						hideAllComponents(reportDetailsPane);
					}
				}
			}
		});
		btnDeleteReport.setToolTipText("Delete the selected report.");

		btnDeleteReport.setBackground(UIManager.getColor("Button.background"));
		reportListToolbar.add(btnDeleteReport);
		btnDeleteReport.setHorizontalAlignment(SwingConstants.LEADING);
		btnDeleteReport.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/delete.png")));

		

		btnSaveReport.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				try {
					if (checkReportFields()) {
						processReport();

						Vector<Contact> insertContact  = new Vector<Contact>();
						for (int i = 0; i < reportContactList.getModel()
								.getSize(); i++) {
							insertContact.addElement(reportContactList.getModel().getElementAt(i));
						}
						Vector<Department> insertDepartment = new Vector<Department>();
						
						for (int i = 0; i < reportDepartmentList.getModel()
								.getSize(); i++) {
							insertDepartment.addElement(reportDepartmentList.getModel().getElementAt(i));
						
						}
						preparedReport.setRecipients(insertContact);
						preparedReport.setDepartments(insertDepartment);

						if (boxSortOrder.getSelectedItem().toString()
								.equals("Descending")) {
							preparedReport.SetSort("-" + preparedReport.getSort());
						}
						
						if(reportAddMode){
							reportManager.insert(preparedReport);
						}else{
							System.out.println(preparedReport.getReportId());
							reportManager.update(preparedReport);
						}
						reportList.setModel(reportManager.getListModelReports());
						reportEditMode(false);
						reportAddMode = false;
						reportEditMode = false;
						reportList.clearSelection();
						reportList.setSelectedValue(preparedReport, true);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					e1.printStackTrace();
				}

			}
		});

	}

	/**
	 * Initialize the Settings Tab
	 */
	private void initializeSettingTab() {
		JLabel lblSettingsTab = new JLabel("Settings");
		lblSettingsTab.setIcon(new ImageIcon(mainCommandWindow.class
				.getResource("/resources/images/gear.png")));
		lblSettingsTab.setPreferredSize(new Dimension(200, 90));

		settingTab.setBackground(standardPane);
		tabsPane.add(settingTab, null);
		tabsPane.setTabComponentAt(3, lblSettingsTab);
		tabsPane.setEnabledAt(3, true);
		GridBagLayout gbl_settingTab = new GridBagLayout();
		gbl_settingTab.columnWidths = new int[] { 10, 184, 50, 50, 10, 0 };
		gbl_settingTab.rowHeights = new int[] { 10, 48, 30, 28, 0, 0, 0, 50, 30, 0,
				0, 0, 14, 0, 487, 29, 10, 0 };
		gbl_settingTab.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_settingTab.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		settingTab.setLayout(gbl_settingTab);
		
		JButton btnRevertChanges = new JButton("Revert Changes");
		JButton btnSaveChanges = new JButton("Apply");
		
		if (isFirstRun)
		{
			tabsPane.setSelectedIndex(3);
			tabsPane.setEnabled(false);
			btnRevertChanges.setVisible(false);
			
			JOptionPane.showMessageDialog(null, "No valid settings file detected. Select 'Ok' to enter settings.");
		}

		JLabel lblSettings = new JLabel("Settings:");
		lblSettings.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		GridBagConstraints gbc_lblSettings = new GridBagConstraints();
		gbc_lblSettings.anchor = GridBagConstraints.BASELINE;
		gbc_lblSettings.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSettings.insets = new Insets(0, 0, 5, 5);
		gbc_lblSettings.gridx = 1;
		gbc_lblSettings.gridy = 1;
		settingTab.add(lblSettings, gbc_lblSettings);
		
		JLabel lblDatabaseOptions = new JLabel("Database Options:");
		lblDatabaseOptions.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblDatabaseOptions = new GridBagConstraints();
		gbc_lblDatabaseOptions.anchor = GridBagConstraints.WEST;
		gbc_lblDatabaseOptions.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatabaseOptions.gridx = 1;
		gbc_lblDatabaseOptions.gridy = 2;
		settingTab.add(lblDatabaseOptions, gbc_lblDatabaseOptions);

		JLabel lblSqlServer = new JLabel("SQL Server:");
		GridBagConstraints gbc_lblSqlServer = new GridBagConstraints();
		gbc_lblSqlServer.anchor = GridBagConstraints.WEST;
		gbc_lblSqlServer.insets = new Insets(0, 10, 5, 5);
		gbc_lblSqlServer.gridx = 1;
		gbc_lblSqlServer.gridy = 3;
		settingTab.add(lblSqlServer, gbc_lblSqlServer);

		txtSqlServer = new JTextField();
		txtSqlServer.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.anchor = GridBagConstraints.NORTH;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 3;
		settingTab.add(txtSqlServer, gbc_textField);

		JLabel lblSqlDatabaseName = new JLabel("SQL Database Name:");
		GridBagConstraints gbc_lblSqlDatabaseName = new GridBagConstraints();
		gbc_lblSqlDatabaseName.anchor = GridBagConstraints.WEST;
		gbc_lblSqlDatabaseName.insets = new Insets(0, 10, 5, 5);
		gbc_lblSqlDatabaseName.gridx = 1;
		gbc_lblSqlDatabaseName.gridy = 4;
		settingTab.add(lblSqlDatabaseName, gbc_lblSqlDatabaseName);

		txtSqlDatabaseName = new JTextField();
		GridBagConstraints gbc_textField_13 = new GridBagConstraints();
		gbc_textField_13.insets = new Insets(0, 0, 5, 5);
		gbc_textField_13.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_13.gridx = 2;
		gbc_textField_13.gridy = 4;
		settingTab.add(txtSqlDatabaseName, gbc_textField_13);
		txtSqlDatabaseName.setColumns(10);

		JLabel lblSqlUsername = new JLabel("SQL Username:");
		GridBagConstraints gbc_lblSqlUsername = new GridBagConstraints();
		gbc_lblSqlUsername.anchor = GridBagConstraints.WEST;
		gbc_lblSqlUsername.insets = new Insets(0, 10, 5, 5);
		gbc_lblSqlUsername.gridx = 1;
		gbc_lblSqlUsername.gridy = 5;
		settingTab.add(lblSqlUsername, gbc_lblSqlUsername);

		txtSqlUsername = new JTextField();
		GridBagConstraints gbc_textField_8 = new GridBagConstraints();
		gbc_textField_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_8.insets = new Insets(0, 0, 5, 5);
		gbc_textField_8.gridx = 2;
		gbc_textField_8.gridy = 5;
		settingTab.add(txtSqlUsername, gbc_textField_8);
		txtSqlUsername.setColumns(10);

		JLabel lblSqlPassword = new JLabel("SQL Password:");
		GridBagConstraints gbc_lblSqlPassword = new GridBagConstraints();
		gbc_lblSqlPassword.anchor = GridBagConstraints.WEST;
		gbc_lblSqlPassword.insets = new Insets(0, 10, 5, 5);
		gbc_lblSqlPassword.gridx = 1;
		gbc_lblSqlPassword.gridy = 6;
		settingTab.add(lblSqlPassword, gbc_lblSqlPassword);

		txtSqlPassword = new JPasswordField();
		GridBagConstraints gbc_textField_9 = new GridBagConstraints();
		gbc_textField_9.insets = new Insets(0, 0, 5, 5);
		gbc_textField_9.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_9.gridx = 2;
		gbc_textField_9.gridy = 6;
		settingTab.add(txtSqlPassword, gbc_textField_9);
		txtSqlPassword.setColumns(10);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 3;
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 7;
		settingTab.add(separator, gbc_separator);
		
		JLabel lblEmailOptions = new JLabel("Email Options:");
		lblEmailOptions.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblEmailOptions = new GridBagConstraints();
		gbc_lblEmailOptions.anchor = GridBagConstraints.WEST;
		gbc_lblEmailOptions.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmailOptions.gridx = 1;
		gbc_lblEmailOptions.gridy = 8;
		settingTab.add(lblEmailOptions, gbc_lblEmailOptions);

		JLabel lblEmailServer = new JLabel("Email Server:");
		GridBagConstraints gbc_lblEmailServer = new GridBagConstraints();
		gbc_lblEmailServer.anchor = GridBagConstraints.WEST;
		gbc_lblEmailServer.insets = new Insets(0, 10, 5, 5);
		gbc_lblEmailServer.gridx = 1;
		gbc_lblEmailServer.gridy = 9;
		settingTab.add(lblEmailServer, gbc_lblEmailServer);

		txtEmailServer = new JTextField();
		GridBagConstraints gbc_textField_10 = new GridBagConstraints();
		gbc_textField_10.insets = new Insets(0, 0, 5, 5);
		gbc_textField_10.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_10.gridx = 2;
		gbc_textField_10.gridy = 9;
		settingTab.add(txtEmailServer, gbc_textField_10);
		txtEmailServer.setColumns(10);

		JLabel lblEmailUsername = new JLabel("Email Username:");
		GridBagConstraints gbc_lblEmailUsername = new GridBagConstraints();
		gbc_lblEmailUsername.anchor = GridBagConstraints.WEST;
		gbc_lblEmailUsername.insets = new Insets(0, 10, 5, 5);
		gbc_lblEmailUsername.gridx = 1;
		gbc_lblEmailUsername.gridy = 10;
		settingTab.add(lblEmailUsername, gbc_lblEmailUsername);

		txtEmailUsername = new JTextField();
		GridBagConstraints gbc_textField_11 = new GridBagConstraints();
		gbc_textField_11.insets = new Insets(0, 0, 5, 5);
		gbc_textField_11.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_11.gridx = 2;
		gbc_textField_11.gridy = 10;
		settingTab.add(txtEmailUsername, gbc_textField_11);
		txtEmailUsername.setColumns(10);

		btnRevertChanges.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				populateSettings();
			}
		});
		
		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.insets = new Insets(0, 0, 5, 5);
		gbc_separator_1.gridx = 2;
		gbc_separator_1.gridy = 11;
		settingTab.add(separator_1, gbc_separator_1);
		
		JLabel label = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 12;
		settingTab.add(label, gbc_label);
		
		chckBxFormat = new JCheckBox("Format outgoing reports as HTML tables");
		chckBxFormat.setBorder(null);
		chckBxFormat.setBackground(UIManager.getColor("Button.light"));
		chckBxFormat.setSelected(true);
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox.gridwidth = 2;
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox.gridx = 1;
		gbc_chckbxNewCheckBox.gridy = 13;
		settingTab.add(chckBxFormat, gbc_chckbxNewCheckBox);
		GridBagConstraints gbc_btnRevertChanges = new GridBagConstraints();
		gbc_btnRevertChanges.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnRevertChanges.insets = new Insets(0, 0, 5, 5);
		gbc_btnRevertChanges.gridwidth = 2;
		gbc_btnRevertChanges.gridx = 1;
		gbc_btnRevertChanges.gridy = 15;
		settingTab.add(btnRevertChanges, gbc_btnRevertChanges);

		btnSaveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					PreviousProps = improvedSettings.getSettings();
					
					Settings newSettings = new Settings(txtSqlServer.getText(), txtSqlUsername.getText(), txtSqlPassword.getText(),
							txtSqlDatabaseName.getText(), txtEmailServer.getText(), txtEmailUsername.getText(), chckBxFormat.isSelected());
					
					if(improvedSettings.writeSettings(newSettings)){

						PreviousProps = improvedSettings.getSettings(); //changes for next revert.
					
						isFirstRun = false;
						mainFrame.dispose();
						mainCommandWindow.main(null);
					
					}
					else{
						JOptionPane.showMessageDialog(null, "Invalid settings detected.  Could not apply settings.", "Error", JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		
		GridBagConstraints gbc_btnSaveChanges = new GridBagConstraints();
		gbc_btnSaveChanges.insets = new Insets(0, 0, 5, 5);
		gbc_btnSaveChanges.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSaveChanges.anchor = GridBagConstraints.NORTH;
		gbc_btnSaveChanges.gridx = 3;
		gbc_btnSaveChanges.gridy = 15;
		settingTab.add(btnSaveChanges, gbc_btnSaveChanges);
		if (contactList.getFirstVisibleIndex() == -1) {

			btnEditContact.setVisible(false);
		}

		if (departmentList.getFirstVisibleIndex() == -1) {

			btnEditDepartment.setVisible(false);
		}
		
		populateSettings();
		
	}
	
	
	/**
	 * Populate the fields in the settings tab
	 */
	private void populateSettings(){
		//Fill in the settings from the initial load
		if(improvedSettings.isSettingFileExist()){
			//Everything is good
			Settings setting = improvedSettings.getSettings();
			txtEmailServer.setText(setting.getEmailServerHost());
			txtEmailUsername.setText(setting.getEmailUsername());
			txtSqlServer.setText(setting.getDatabaseHost());
			txtSqlUsername.setText(setting.getDatabaseUser());
			txtSqlPassword.setText(setting.getDatabasePassword());
			txtSqlDatabaseName.setText(setting.getDatabaseSchema());
			chckBxFormat.setSelected(setting.getFormatResults());
		}
	}

	/**
	 * Update the fields in the Contacts tab
	 * @param selection		Contact to update the display for
	 */
	private void updateContactDetails(Contact selection) {
		if (selection != null) {
			DateFormat format = new SimpleDateFormat("MMM dd");
			
			txtContactName.setText(selection.getFirstName().concat(
					" ".concat(selection.getLastName())));
			txtJobTitle.setText(selection.getJobTitle());

			txtHomePhone.setText(selection.getHomePhone());
			txtWorkPhone.setText(selection.getWorkPhone());
			txtWorkAddress.setText(selection.getWorkAddress());
			txtHomeAddress.setText(selection.getHomeAddress());
			
			if (selection.getBirthDate() == "")
				txtBirthday.setDate(null);
			else
			{
				try {
					txtBirthday.setDate(format.parse(selection.getBirthDate()));
				} catch (ParseException e) {
				}
			}
			txtEmail.setText(selection.getEmail());
		}
	}

	/**
	 * Toggle the display of the Contact tab between edit mode and view mode
	 * @param isOn boolean toggles the edit mode of the contact tab
	 */
	protected void contactEditMode(boolean isOn) {
		if (isOn) {
			btnEditContact.setVisible(false);
			btnSaveContact.setVisible(true);
			btnCancelContact.setVisible(true);
			btnCreateContact.setEnabled(false);
			btnDeleteContact.setEnabled(false);
			makeFieldsMutable(contactDetailsPane);

		} else {
			btnEditContact.setVisible(true);
			btnSaveContact.setVisible(false);
			btnCancelContact.setVisible(false);
			btnCreateContact.setEnabled(true);
			btnDeleteContact.setEnabled(true);
			makeFieldsImmutable(contactDetailsPane);
		}
	}

	/**
	 * Update the fields in the Departments tab
	 * @param selection		Department to update the display for
	 */
	private void updateDepartmentDetails(Department selection) {
		if (selection != null) {
			txtDepartmentName.setText(selection.getName());
			txtDepartmentPhone.setText(selection.getPhone());
			txtDepartmentAddress.setText(selection.getMailingAddress());
/*
			if (selection.getEmployees() == null) {
				// Create a blank vector and associate it, now we have
				// safely cleaned up the null.
				Vector<Contact> temp = new Vector<Contact>();
				selection.setEmployees(temp);
			}
			SortableListModel<Contact> employees = new SortableListModel(selection.getEmployees());
			employees.sort();*/
			System.out.println(selection.getEmployees());
			
			lstContacts.setModel(selection.getEmbeddedContactsModel());
		}

	}

	/**
	 * Toggle the display of the Department tab between edit mode and view mode
	 * @param isOn boolean toggles the edit mode of the department tab
	 */
	protected void departmentEditMode(boolean isOn) {
		if (isOn) {
			btnEditDepartment.setVisible(false);
			btnSaveDepartment.setVisible(true);
			btnCancelDepartment.setVisible(true);
			btnCreateDepartment.setEnabled(false);
			btnDeleteDepartment.setEnabled(false);
			makeFieldsMutable(departmentDetailsPane);
			departmentEmployeeListToolbar.setVisible(true);
			departmentEmployeeListToolbar.setBackground(editPane);
			btnAddEmployeeToDept.setVisible(true);
			btnRemoveEmployeeFromDept.setVisible(true);
		} else {
			btnEditDepartment.setVisible(true);
			btnSaveDepartment.setVisible(false);
			btnCancelDepartment.setVisible(false);
			btnCreateDepartment.setEnabled(true);
			btnDeleteDepartment.setEnabled(true);
			makeFieldsImmutable(departmentDetailsPane);
			departmentEmployeeListToolbar.setVisible(false);
			departmentEmployeeListToolbar.setBackground(standardPane);
			btnAddEmployeeToDept.setVisible(false);
			btnRemoveEmployeeFromDept.setVisible(false);
		}
	}
	
	/**
	 * Update the fields in the Reports tab
	 * @param selection		Report to update the display for
	 */
	private void updateReportDetails(Report mySelect) {
		if (mySelect != null) {
			
			txtReportTitleText.setText(mySelect.getTitle());
			boxStartDate.setDate(convertStringToDate(mySelect.getStartDate(), "yyyy-MM-dd"));
			boxEndDate.setDate(convertStringToDate(mySelect.getEndDate(), "yyyy-MM-dd"));
			boxSortOn.setModel(mySelect.getDimensionMetricsMenuModel());
			boxFilterOn.setModel(mySelect.getDimensionMetricsMenuModel());
			//Load the trees
			TreeModel temp = lstDimensions.getModel();
			DefaultTreeModel temp2 = (DefaultTreeModel) lstDimensions.getModel();
			TreeNode[] nodes = null;
			TreePath path = null;
			DefaultMutableTreeNode tempNode = null;
			String[] Dimensions = mySelect.getDimensions().split(", ");
			treeMultiSelectModel updatedSelections = new treeMultiSelectModel();
			
			for(int i=0; i < Dimensions.length; i++){
				 tempNode = getNodeFromString(temp, Dimensions[i].replaceAll(" ", ""));
				 if(tempNode != null){
					 nodes = temp2.getPathToRoot(tempNode);
					 path = new TreePath(nodes);
					 updatedSelections.addSelectionPath(path);
				 }
				 else{
					 System.out.println("Error" + Dimensions[i]);
				 }
			}
			
			lstDimensions.setSelectionModel(updatedSelections);
	
			temp = lstMetrics_1.getModel();
			temp2 = (DefaultTreeModel) lstMetrics_1.getModel();
			String[] Metrics = mySelect.getMetrics().split(", ");
			treeMultiSelectModel updatedMetricSelections = new treeMultiSelectModel();
			
			for(int i=0; i < Metrics.length; i++){
				 tempNode = getNodeFromString(temp, Metrics[i].replaceAll(" ", ""));
				 if(tempNode != null){
					 nodes = temp2.getPathToRoot(tempNode);
					 path = new TreePath(nodes);
					 updatedMetricSelections.addSelectionPath(path);
				 }
				 else{
					 System.out.println("Error" + Metrics[i]);
				 }
			}
			
			lstMetrics_1.setSelectionModel(updatedMetricSelections);
			
						
			// If new report, just clear the remaining fields
			if ((mySelect.getSort() == null) || (mySelect.getFilter() == null))
			{
				boxSortOrder.setSelectedIndex(1);
				boxMaxResults.setSelectedIndex(0);
				txtFilterText.setText("");
				reportContactList.setModel(new SortableListModel<Contact>());
				reportDepartmentList.setModel(new SortableListModel<Department>());
				return;
			}
			
			//Depends on successful loading of trees
			String sort = mySelect.getSort();
			
			if(sort.startsWith("-")){
				//Set the sort order descending
				boxSortOrder.setSelectedIndex(1);
				sort = sort.substring(1); //sort returns without the -
			}
			else
			{
				//set the sort order ascending
				boxSortOrder.setSelectedIndex(0);
			}
	
		
			boxSortOn.getModel().setSelectedItem(sort);
			
			System.out.println(sort);
			
			//Parse text for filter
			String[] filter = null;
			
			//Sets operator box and establishes the filter parameters
			if(mySelect.getFilter().contains("!=")){
				filter = mySelect.getFilter().split("!=");
				boxFilterBy.setSelectedIndex(0);
			}else if (mySelect.getFilter().contains("<")){
				filter = mySelect.getFilter().split("<");
				boxFilterBy.setSelectedIndex(2);
			}else if (mySelect.getFilter().contains(">")){
				filter = mySelect.getFilter().split(">");
				boxFilterBy.setSelectedIndex(3);
			}else{
				filter = mySelect.getFilter().split("==");
				boxFilterBy.setSelectedIndex(1);
			}
			
			boxFilterOn.getModel().setSelectedItem(filter[0]);
			if(filter.length < 2){
				txtFilterText.setText("");
			}else
			{
				txtFilterText.setText(filter[1]);
			}
			
			boxMaxResults.setSelectedItem(Integer.toString(mySelect.getMaxResults()));
			
			
			//Populate Contacts and Departments lists
			reportContactList.setModel(mySelect.getContactListModelForReports());
			reportDepartmentList.setModel(mySelect.getDepartListModelForReports());
			
		}

	}

	private static String getTreeText(TreeModel model, Object object, String indent) {
	    String myRow = indent + object + "\n";
	    for (int i = 0; i < model.getChildCount(object); i++) {
	        myRow += getTreeText(model, model.getChild(object, i), indent + "  ");
	    }
	    return myRow;
	}
	
	private static DefaultMutableTreeNode getNodeFromString(TreeModel temp, String usrString){
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)temp.getRoot();

		DefaultMutableTreeNode node = null;

		if (root != null)
		    for (Enumeration e = root.breadthFirstEnumeration(); e.hasMoreElements();)
		    {
		        DefaultMutableTreeNode current = (DefaultMutableTreeNode)e.nextElement();

		        if (current.toString().equalsIgnoreCase(usrString))
		        {
		            node = current;
		            break;
		        }
		    }

		return node;
	}
	
	/**
	 * Toggle the display of the Report tab between edit mode and view mode
	 * @param isOn boolean toggles the edit mode of the report tab
	 */
	protected void reportEditMode(boolean isOn) {
		if (isOn) {
			btnEditReport.setVisible(false);
			btnSaveReport.setVisible(true);
			btnCancelReport.setVisible(true);
			btnCreateReport.setEnabled(false);
			btnDeleteReport.setEnabled(false);
			makeFieldsMutable(reportDetailsPane);
			reportContactListToolbar.setVisible(true);
			reportContactListToolbar.setBackground(editPane);
			showAllComponents(reportContactListToolbar);
			reportDepartmentListToolbar.setVisible(true);
			reportDepartmentListToolbar.setBackground(editPane);
			showAllComponents(reportDepartmentListToolbar);

		} else {
			btnEditReport.setVisible(true);
			btnSaveReport.setVisible(false);
			btnCancelReport.setVisible(false);
			btnCreateReport.setEnabled(true);
			btnDeleteReport.setEnabled(true);
			makeFieldsImmutable(reportDetailsPane);
			reportContactListToolbar.setVisible(false);
			hideAllComponents(reportContactListToolbar);
			reportContactListToolbar.setBackground(standardPane);
			reportDepartmentListToolbar.setVisible(false);
			reportDepartmentListToolbar.setBackground(standardPane);
			hideAllComponents(reportDepartmentListToolbar);
		}
	}

	/**
	 * Helper function for converting between edit and view modes.
	 * Makes all of the fields in a JComponent mutable.
	 * @param currentPane The JComponent parent of the fields to update
	 */
	public void makeFieldsMutable(JComponent currentPane) {
		if(currentPane instanceof JPanel){
			currentPane.setBackground(editPane);
		}
		
		Component[] components = currentPane.getComponents();
		Border border = BorderFactory.createEtchedBorder();

		for (Component c : components) {
			if (c instanceof JTextField) {
				((JTextField) c).setBorder(border);
				((JTextField) c).setEditable(true);
			}
			else if (c instanceof JComboBox){
				((JComboBox) c).setEnabled(true);
			}
			else if (c instanceof JDateChooser){
				((JDateChooser) c).setEnabled(true);
				//Show the button
				(((JDateChooser) c).getCalendarButton())
				.setVisible(true);
				
				//Remove boundary border
				((JTextFieldDateEditor) ((JDateChooser) c).getDateEditor())
				.setBorder(BorderFactory.createLineBorder(Color.gray));
			}
			else if (c instanceof JTree){
				((JTree) c).setEnabled(true);
			}
			else if (c instanceof JScrollPane){
				//Need to search inside this view to find components to make Mutable
				makeFieldsMutable((JComponent) c);
				//Recursive call to this function
			}
			else if (c instanceof JViewport){
				//Need to search inside this view to find components to make Mutable
				makeFieldsMutable((JComponent) c);
				//Recursive call to this function
			}
			
		}
	}

	/**
	 * Helper function for converting between edit and view modes.
	 * Makes all of the fields in a JComponent non-mutable.
	 * @param currentPane The JComponent parent of the fields to update
	 */
	public void makeFieldsImmutable(JComponent currentPane) {
		if(currentPane instanceof JPanel){
			currentPane.setBackground(standardPane);
		}
		
		Component[] components = currentPane.getComponents();
		Border border = BorderFactory.createEmptyBorder();

		for (Component c : components) {
			if (c instanceof JTextField) {
				((JTextField) c).setBorder(border);
				c.setBackground(goodField);
				((JTextField) c).setEditable(false);
			}
			else if (c instanceof JComboBox){
				((JComboBox) c).setEnabled(false);
			}
			else if (c instanceof JDateChooser){
				((JDateChooser) c).setEnabled(false);
				//Hide the button
				(((JDateChooser) c).getCalendarButton())
				.setVisible(false);
				// Make the field render black
				((JTextFieldDateEditor) ((JDateChooser) c).getDateEditor())
						.setDisabledTextColor(Color.black);
				//Remove boundary border
				((JTextFieldDateEditor) ((JDateChooser) c).getDateEditor())
				.setBorder(null);
				//Fixes background color
				((JTextFieldDateEditor) ((JDateChooser) c).getDateEditor())
				.setBackground(Color.white);
				
			}
			else if (c instanceof JTree){
				((JTree) c).setEnabled(false);
			}
			else if (c instanceof JScrollPane){
				//Need to search inside this view to find components to make immutable
				makeFieldsImmutable((JComponent) c);
				//Recursive call to this function
			}
			else if (c instanceof JViewport){
				//Need to search inside this view to find components to make immutable
				makeFieldsImmutable((JComponent) c);
				//Recursive call to this function
			}
			
			
		}
	}
	
	/**
	 * Hides all components in a pane
	 * @param currentPane	The JPanel parent to update
	 */
	public void hideAllComponents(JPanel currentPane) {
		for (Component c : currentPane.getComponents()) {
			c.setVisible(false);
		}
	}
	
	/**
	 * Shows all components in a pane
	 * @param currentPane	The JPanel parent to update
	 */
	public void showAllComponents(JPanel currentPane) {
		for (Component c : currentPane.getComponents()) {
			c.setVisible(true);
		}
	}

	/**
	 * Processes the fields in the reports tab to prepare for sending
	 */
	public void processReport() {
		String filter;
			filter = boxFilterOn.getSelectedItem().toString()
					+ boxFilterBy.getSelectedItem().toString()
					+ txtFilterText.getText();
		preparedReport.SetSort(boxSortOn.getSelectedItem()
				.toString());
		preparedReport.SetFilter(filter);
		preparedReport.SetMaxResults(boxMaxResults.getSelectedItem().toString());
		preparedReport.SetTitle(txtReportTitleText.getText());

		if (boxSortOrder.getSelectedItem().toString().equals("Decending")) {
			preparedReport.SetSort("-" + preparedReport.getSort());
		}
	}

	/**
	 * Checks the validity of the reports tab
	 * @return boolean indicating whether or not the report tab is valid
	 */
	public Boolean checkReportFields() {
		if (boxStartDate.getDate() == null || boxEndDate.getDate().equals(null)) {
			JOptionPane.showMessageDialog(null,
					"One or both of the Dates are not" + " specified.");
			return false;
		} else if (txtReportTitleText.getText() == null
				|| txtReportTitleText.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "Report title needs to be at"
					+ " one character long.");
			return false;
		} else if (boxSortOn.getSelectedItem() == null
				|| boxSortOn.getSelectedItem().toString().length() < 3) {
			JOptionPane.showMessageDialog(null, "You need to select"
					+ " at least one metric.");
			return false;
		} else if (preparedReport.MetricCount() == 0) {
			JOptionPane.showMessageDialog(null, "You need to select"
					+ " at least one metric.");
			return false;
		} else if (preparedReport.DimensionCount() == 0) {
			JOptionPane.showMessageDialog(null, "You need to select"
					+ " at least one dimension.");
			return false;
		} else if (boxFilterOn.getSelectedItem() == null
				|| boxFilterOn.getSelectedItem().toString().length() < 3) {
			JOptionPane.showMessageDialog(null, "You need to select"
					+ " at least one metric or dimension to filter on.");
			return false;
		} else {

			return true;

		}

	}
	
	private Date convertStringToDate(String stringDate, String pattern) {
		  Date date = null;

		  if (stringDate != null) {
		    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		    try {
		      date = sdf.parse(stringDate);
		    } catch (ParseException e) {
		    }
		  }

		  return date;
		}
	
	
	/** Formats the JSplitPane to size and color correctly
	 * 
	 * @param jSplitPane		Pane to format
	 */
	private static void flattenSplitPane(JSplitPane jSplitPane) {
	    jSplitPane.setUI(new BasicSplitPaneUI() {
	        public BasicSplitPaneDivider createDefaultDivider() {
	            return new BasicSplitPaneDivider(this) {
	            	@Override
	                public void setBorder(Border b) {
	                }
	                
	                @Override
                    public void paint(Graphics g) {
                    g.setColor(UIManager.getColor("CheckBox.background"));
                    g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
	                
	            };
	        }
	    });
	    jSplitPane.setBorder(null);
	    jSplitPane.setDividerSize(4);
	    
	    JComponent DetailsPane = (JComponent) jSplitPane.getRightComponent();
	    JComponent ListPane = (JComponent) jSplitPane.getLeftComponent();
	    DetailsPane.setBorder(BorderFactory.createLoweredSoftBevelBorder());
	    //ListPane.setBorder(BorderFactory.createRaisedBevelBorder());
	    jSplitPane.setBorder(BorderFactory.createLoweredSoftBevelBorder());
	}
}


