/**
 * Name: ContactsModel.java
 * Description: A GUI component that displays a specialized contact list in a window
 * 
 * @author Howard Grimberg
 * Date Created: 11/13/12
 */

package com.gui;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.ListModel;

import com.managers.ContactManager;
import com.managers.SortableListModel;
import com.pojos.Contact;

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
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * ContactsModal
 * 
 * Portable display of contacts list
 */
public class ContactsModal {

	private JFrame frmSelectDepartmentalContacts;
	private ContactManager contactManage;
	private JList<Contact> selected;
	private WindowAdapter onClose;
	private JTextField contactSearchField;
	private SortableListModel<Contact> searchResults;
	private Vector<Contact> potentialContacts;
	final JList<Contact> contactList = new JList<Contact>();
	SortableListModel<Contact> contactModel;

	/**
	 * Create the application.
	 * 
	 * @param lstContacts
	 */
	public ContactsModal(ContactManager cmanage, JList<Contact> lstContacts) {
		contactManage = cmanage;
		selected = lstContacts;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSelectDepartmentalContacts = new JFrame();
		frmSelectDepartmentalContacts.setAlwaysOnTop(true);

		frmSelectDepartmentalContacts.setTitle("Select Contacts");
		frmSelectDepartmentalContacts.setBounds(100, 100, 275, 500);
		frmSelectDepartmentalContacts
				.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{273, 0};
		gridBagLayout.rowHeights = new int[]{20, 426, 25, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		frmSelectDepartmentalContacts.getContentPane().setLayout(gridBagLayout);
				
				JPanel panel = new JPanel();
				GridBagConstraints gbc_panel = new GridBagConstraints();
				gbc_panel.fill = GridBagConstraints.BOTH;
				gbc_panel.gridx = 0;
				gbc_panel.gridy = 0;
				frmSelectDepartmentalContacts.getContentPane().add(panel, gbc_panel);
				GridBagLayout gbl_panel = new GridBagLayout();
				gbl_panel.columnWidths = new int[]{10, 273, 0};
				gbl_panel.rowHeights = new int[]{20, 0};
				gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
				gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
				panel.setLayout(gbl_panel);
						
						JLabel label = new JLabel("");
						label.setIcon(new ImageIcon(ContactsModal.class.getResource("/resources/images/search.png")));
						GridBagConstraints gbc_label = new GridBagConstraints();
						gbc_label.insets = new Insets(0, 0, 0, 5);
						gbc_label.anchor = GridBagConstraints.EAST;
						gbc_label.gridx = 0;
						gbc_label.gridy = 0;
						panel.add(label, gbc_label);
				
						contactSearchField = new JTextField();
						GridBagConstraints gbc_contactSearchField = new GridBagConstraints();
						gbc_contactSearchField.fill = GridBagConstraints.BOTH;
						gbc_contactSearchField.gridx = 1;
						gbc_contactSearchField.gridy = 0;
						panel.add(contactSearchField, gbc_contactSearchField);
						contactSearchField.addKeyListener(new KeyAdapter() {

							@Override
							public void keyReleased(KeyEvent e) {

								if (contactSearchField.getText() != null
										&& !contactSearchField.getText().isEmpty()) {
									searchResults = contactManage
											.searchContactsByName(contactSearchField.getText());
									potentialContacts = new Vector<Contact>();
									for (int i = 0; i < searchResults.getSize(); i++) {
										potentialContacts.add((Contact) searchResults
												.getElementAt(i));
									}
									// Remove instances of currently selected contacts from the
									// add list
									for (int i = 0; i < selected.getModel().getSize(); i++) {
										potentialContacts.remove(selected.getModel()
												.getElementAt(i));
									}
									searchResults.removeAllElements();
									for (int i = 0; i < potentialContacts.size(); i++) {
										searchResults.addElement(potentialContacts.get(i));
									}
									contactList.setModel(searchResults);
									if (contactList.getModel().getSize() != 0) {
										contactList.setSelectedIndex(contactList
												.getFirstVisibleIndex());
									} else {
										contactList.clearSelection();
									}
								} else {
									SortableListModel<Contact> contactModel = contactManage
											.getListModelContacts();
									// Creates a vector containing elements of previous
									potentialContacts = new Vector<Contact>();

									for (int i = 0; i < contactModel.getSize(); i++) {
										potentialContacts.add((Contact) contactModel
												.getElementAt(i));
									}

									// Remove instances of currently selected contacts from the
									// add list
									for (int i = 0; i < selected.getModel().getSize(); i++) {
										potentialContacts.remove(selected.getModel()
												.getElementAt(i));
									}

									// Clear the model, prepare for vector entries
									contactModel.removeAllElements();

									for (int i = 0; i < potentialContacts.size(); i++) {
										contactModel.addElement(potentialContacts.get(i));
									}

									// Set the model
									contactList.setModel(contactModel);
									if (contactList.getModel().getSize() != 0) {
										contactList.setSelectedIndex(contactList
												.getFirstVisibleIndex());
									} else {
										contactList.clearSelection();
									}
								}

							}

						});
						contactSearchField.setColumns(10);
		
				JScrollPane scrollPane = new JScrollPane();
				GridBagConstraints gbc_scrollPane = new GridBagConstraints();
				gbc_scrollPane.fill = GridBagConstraints.BOTH;
				gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
				gbc_scrollPane.gridx = 0;
				gbc_scrollPane.gridy = 1;
				frmSelectDepartmentalContacts.getContentPane().add(scrollPane, gbc_scrollPane);
				
						
						scrollPane.setViewportView(contactList);
						
		
				JButton btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frmSelectDepartmentalContacts.dispose();
					}
				});
				GridBagConstraints gbc_btnOk = new GridBagConstraints();
				gbc_btnOk.anchor = GridBagConstraints.NORTH;
				gbc_btnOk.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnOk.gridx = 0;
				gbc_btnOk.gridy = 2;
				frmSelectDepartmentalContacts.getContentPane().add(btnOk, gbc_btnOk);
		contactModel = contactManage
				.getListModelContacts();
		// Creates a vector containing elements of previous
		potentialContacts = new Vector<Contact>();
		
		// Set the model
		contactList.setModel(contactModel);

		for (int i = 0; i < contactModel.getSize(); i++) {
			potentialContacts.add((Contact) contactModel.getElementAt(i));
		}

		// Remove instances of currently selected contacts from the add list
		for (int i = 0; i < selected.getModel().getSize(); i++) {
			potentialContacts.remove(selected.getModel().getElementAt(i));
		}

		// Clear the model, prepare for vector entries
		contactModel.removeAllElements();

		for (int i = 0; i < potentialContacts.size(); i++) {
			contactModel.addElement(potentialContacts.get(i));
		}

		frmSelectDepartmentalContacts.setVisible(true);
		onClose = new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// selected.clearSelection();
				SortableListModel<Contact> temp = new SortableListModel<Contact>();
				List<Contact> tempc = contactList.getSelectedValuesList();
				Iterator<Contact> tex = tempc.iterator();

				while (tex.hasNext()) {
					temp.addElement((Contact) tex.next());
				}

				for (int i = 0; i < selected.getModel().getSize(); i++) {
					temp.addElement(selected.getModel().getElementAt(i));
				}

				temp.sort();
				selected.setModel(temp);
			}
		};
		frmSelectDepartmentalContacts.addWindowListener(onClose);
	}
}
