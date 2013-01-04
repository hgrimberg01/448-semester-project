/**
 * Name: DepartmentsModal.java
 * Description: Connects to the SMTP server and sends email messages
 * 
 * @author Howard Grimberg
 * Date Created: 11/16/12
 */

package com.gui;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.ListModel;

import com.managers.DepartmentManager;
import com.managers.SortableListModel;

import com.pojos.Contact;
import com.pojos.Department;

import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

/**
 * DepartmentsModal
 * 
 * Portable display of departments list
 */
public class DepartmentsModal {

	private JFrame frmSelectDepartments;
	private DepartmentManager departmentManage;
	private JList<Department> selected;
	private WindowAdapter onClose;
	private JTextField departmentSearchField;
	private SortableListModel<Department> searchResults;
	private Vector<Department> potentialDepartments;
	final JList<Department> departmentList = new JList<Department>();
	SortableListModel<Department> departmentModel;

	/**
	 * Create the application.
	 * 
	 * @param lstDepartments
	 */
	public DepartmentsModal(DepartmentManager dmanage,
			JList<Department> lstDepartments) {
		departmentManage = dmanage;
		selected = lstDepartments;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSelectDepartments = new JFrame();

		frmSelectDepartments.setTitle("Select Departments");
		frmSelectDepartments.setBounds(100, 100, 275, 500);
		frmSelectDepartments.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{273, 0};
		gridBagLayout.rowHeights = new int[]{20, 426, 25, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		frmSelectDepartments.getContentPane().setLayout(gridBagLayout);
		SortableListModel<Department> departmentModel = departmentManage
				.getListModelDepartment();
		// Creates a vector containing elements of previous
		 potentialDepartments = new Vector<Department>();

		for (int i = 0; i < departmentModel.getSize(); i++) {
			potentialDepartments.add((Department) departmentModel
					.getElementAt(i));
		}

		// Remove instances of currently selected departments from the add list
		for (int i = 0; i < selected.getModel().getSize(); i++) {
			potentialDepartments.remove(selected.getModel().getElementAt(i));
		}

		// Clear the model, prepare for vector entries
		departmentModel.removeAllElements();

		for (int i = 0; i < potentialDepartments.size(); i++) {
			departmentModel.addElement(potentialDepartments.get(i));
		}
						
						JPanel panel = new JPanel();
						GridBagConstraints gbc_panel = new GridBagConstraints();
						gbc_panel.fill = GridBagConstraints.BOTH;
						gbc_panel.gridx = 0;
						gbc_panel.gridy = 0;
						frmSelectDepartments.getContentPane().add(panel, gbc_panel);
								GridBagLayout gbl_panel = new GridBagLayout();
								gbl_panel.columnWidths = new int[]{20, 273, 0};
								gbl_panel.rowHeights = new int[]{20, 0};
								gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
								gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
								panel.setLayout(gbl_panel);
								
								JLabel label = new JLabel("");
								label.setIcon(new ImageIcon(DepartmentsModal.class.getResource("/resources/images/search.png")));
								GridBagConstraints gbc_label = new GridBagConstraints();
								gbc_label.insets = new Insets(0, 0, 0, 5);
								gbc_label.anchor = GridBagConstraints.EAST;
								gbc_label.gridx = 0;
								gbc_label.gridy = 0;
								panel.add(label, gbc_label);
						
								departmentSearchField = new JTextField();
								GridBagConstraints gbc_departmentSearchField = new GridBagConstraints();
								gbc_departmentSearchField.fill = GridBagConstraints.BOTH;
								gbc_departmentSearchField.gridx = 1;
								gbc_departmentSearchField.gridy = 0;
								panel.add(departmentSearchField, gbc_departmentSearchField);
								departmentSearchField.addKeyListener(new KeyAdapter() {
									@Override
									public void keyReleased(KeyEvent e) {
										if (departmentSearchField.getText() != null
												&& !departmentSearchField.getText().isEmpty()) {
											searchResults = departmentManage
													.searchDepartmentsByName(departmentSearchField.getText());
											potentialDepartments = new Vector<Department>();
											for (int i = 0; i < searchResults.getSize(); i++) {
												potentialDepartments.add((Department) searchResults
														.getElementAt(i));
											}
											// Remove instances of currently selected departments from the
											// add list
											for (int i = 0; i < selected.getModel().getSize(); i++) {
												potentialDepartments.remove(selected.getModel()
														.getElementAt(i));
											}
											searchResults.removeAllElements();
											for (int i = 0; i < potentialDepartments.size(); i++) {
												searchResults.addElement(potentialDepartments.get(i));
											}
											departmentList.setModel(searchResults);
											if (departmentList.getModel().getSize() != 0) {
												departmentList.setSelectedIndex(departmentList
														.getFirstVisibleIndex());
											} else {
												departmentList.clearSelection();
											}
										} else {
											SortableListModel<Department> departmentModel = departmentManage.getListModelDepartment();
											// Creates a vector containing elements of previous
											potentialDepartments = new Vector<Department>();

											for (int i = 0; i < departmentModel.getSize(); i++) {
												potentialDepartments.add((Department) departmentModel
														.getElementAt(i));
											}

											// Remove instances of currently selected departments from the
											// add list
											for (int i = 0; i < selected.getModel().getSize(); i++) {
												potentialDepartments.remove(selected.getModel()
														.getElementAt(i));
											}

											// Clear the model, prepare for vector entries
											departmentModel.removeAllElements();

											for (int i = 0; i < potentialDepartments.size(); i++) {
												departmentModel.addElement(potentialDepartments.get(i));
											}

											// Set the model
											departmentList.setModel(departmentModel);
											if (departmentList.getModel().getSize() != 0) {
												departmentList.setSelectedIndex(departmentList
														.getFirstVisibleIndex());
											} else {
												departmentList.clearSelection();
											}
										
										
										
										
									}
								}});
								departmentSearchField.setColumns(10);
		
				JScrollPane scrollPane = new JScrollPane();
				GridBagConstraints gbc_scrollPane = new GridBagConstraints();
				gbc_scrollPane.fill = GridBagConstraints.BOTH;
				gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
				gbc_scrollPane.gridx = 0;
				gbc_scrollPane.gridy = 1;
				frmSelectDepartments.getContentPane().add(scrollPane, gbc_scrollPane);
				
						final JList<Department> departmentList = new JList<Department>();
						scrollPane.setViewportView(departmentList);
						
								// Set the model
								departmentList.setModel(departmentModel);
		
				JButton btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frmSelectDepartments.dispose();
					}
				});
				GridBagConstraints gbc_btnOk = new GridBagConstraints();
				gbc_btnOk.anchor = GridBagConstraints.NORTH;
				gbc_btnOk.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnOk.gridx = 0;
				gbc_btnOk.gridy = 2;
				frmSelectDepartments.getContentPane().add(btnOk, gbc_btnOk);

		frmSelectDepartments.setVisible(true);
		onClose = new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// selected.clearSelection();
				DefaultListModel<Department> temp = new DefaultListModel<Department>();
				List<Department> tempc = departmentList.getSelectedValuesList();
				Iterator<Department> tex = tempc.iterator();

				while (tex.hasNext()) {
					temp.addElement((Department) tex.next());
				}

				for (int i = 0; i < selected.getModel().getSize(); i++) {
					temp.addElement(selected.getModel().getElementAt(i));
				}

				selected.setModel(temp);
			}
		};
		frmSelectDepartments.addWindowListener(onClose);
	}
}
