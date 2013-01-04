/**
 * Name: eMailWindow.java
 * Description: Collects additional recipients and sends report
 * 
 * @author Pho Hale
 * Date Created: 12/5/12
 */

package com.gui;

import java.awt.EventQueue;
import java.awt.Shape;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.managers.ReportManager;
import com.pojos.Report;

import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import java.awt.Point;
import javax.swing.JScrollPane;

/**
 * eMailWindow
 * 
 * Opens a window for adding recipients and sending a report
 */
public class eMailWindow {

	private JFrame frmSendReport;
	private static ReportManager reportManager;
	private static Report preparedReport;
	private static boolean format;
	
	final JTextArea txtAdditionalRecipients = new JTextArea();
	final JFormattedTextField txtSubject = new JFormattedTextField();
	
	/**
	 * Constructor for the eMailWindow
	 * @param reportMan		A report manager
	 * @param preparedRep	A report object
	 * @param reportFormat	A boolean indicating the desired format of the email, Plain Text (0) or HTML (1)
	 */
	public eMailWindow(ReportManager reportMan, Report preparedRep, boolean reportFormat) {
		reportManager = reportMan;
		preparedReport = preparedRep;
		format = reportFormat;
		initialize();
		frmSendReport.setVisible(true);
	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSendReport = new JFrame();
		frmSendReport.setUndecorated(true);
		frmSendReport.setMinimumSize(new Dimension(200, 300));
		frmSendReport.setType(Type.UTILITY);
		frmSendReport.setAlwaysOnTop(true);
		frmSendReport.setTitle("Send Report");
		frmSendReport.setBounds(100, 100, 390, 450);
		//Shape windowShape = new Ellipse2D.Float(0.0f, 0.0f, 400.0f, 700.0f);
		
		//frmSendReport.setShape(windowShape);
		
		
		frmSendReport.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{390, 0};
		gridBagLayout.rowHeights = new int[]{354, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frmSendReport.getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(99, 130, 191), null), new MatteBorder(1, 1, 1, 1, (Color) new Color(163, 184, 204))));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frmSendReport.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{245, 0, 57, 0};
		gbl_panel.rowHeights = new int[]{2, 0, 0, 0, 157, 13, 16, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblSendReport = new JLabel("Send Report");
		GridBagConstraints gbc_lblSendReport = new GridBagConstraints();
		gbc_lblSendReport.anchor = GridBagConstraints.NORTH;
		gbc_lblSendReport.gridwidth = 3;
		gbc_lblSendReport.insets = new Insets(0, 0, 5, 0);
		gbc_lblSendReport.gridx = 0;
		gbc_lblSendReport.gridy = 0;
		panel.add(lblSendReport, gbc_lblSendReport);
		lblSendReport.setIcon(new ImageIcon(eMailWindow.class.getResource("/resources/images/mail.png")));
		lblSendReport.setFont(new Font("Dialog", Font.PLAIN, 25));
		
		JLabel lblSubject = new JLabel("Subject:");
		GridBagConstraints gbc_lblSubject = new GridBagConstraints();
		gbc_lblSubject.anchor = GridBagConstraints.WEST;
		gbc_lblSubject.insets = new Insets(0, 0, 5, 5);
		gbc_lblSubject.gridx = 0;
		gbc_lblSubject.gridy = 1;
		panel.add(lblSubject, gbc_lblSubject);
		GridBagConstraints gbc_txtSubject = new GridBagConstraints();
		gbc_txtSubject.anchor = GridBagConstraints.NORTH;
		gbc_txtSubject.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSubject.gridwidth = 3;
		gbc_txtSubject.insets = new Insets(0, 0, 5, 0);
		gbc_txtSubject.gridx = 0;
		gbc_txtSubject.gridy = 2;
		panel.add(txtSubject, gbc_txtSubject);
		
		
		txtSubject.setText(preparedReport.getTitle());
		
		JLabel lblAdditionalRecipients = new JLabel("Additional Recipients:");
		GridBagConstraints gbc_lblAdditionalRecipients = new GridBagConstraints();
		gbc_lblAdditionalRecipients.anchor = GridBagConstraints.WEST;
		gbc_lblAdditionalRecipients.gridwidth = 3;
		gbc_lblAdditionalRecipients.insets = new Insets(0, 0, 5, 0);
		gbc_lblAdditionalRecipients.gridx = 0;
		gbc_lblAdditionalRecipients.gridy = 3;
		panel.add(lblAdditionalRecipients, gbc_lblAdditionalRecipients);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 4;
		panel.add(scrollPane, gbc_scrollPane);
		scrollPane.setViewportView(txtAdditionalRecipients);
		txtAdditionalRecipients.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtAdditionalRecipients.setColumns(1);
		txtAdditionalRecipients.setText("");
		
		
		JLabel lblSeparateMultipleEmails = new JLabel("Separate addresses with a comma.");
		GridBagConstraints gbc_lblSeparateMultipleEmails = new GridBagConstraints();
		gbc_lblSeparateMultipleEmails.anchor = GridBagConstraints.WEST;
		gbc_lblSeparateMultipleEmails.gridwidth = 3;
		gbc_lblSeparateMultipleEmails.insets = new Insets(0, 0, 10, 0);
		gbc_lblSeparateMultipleEmails.gridx = 0;
		gbc_lblSeparateMultipleEmails.gridy = 5;
		panel.add(lblSeparateMultipleEmails, gbc_lblSeparateMultipleEmails);
		
		JButton btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 10, 5);
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 6;
		panel.add(btnCancel, gbc_btnCancel);
		
		JButton btnSend = new JButton("Send");
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 10, 0);
		gbc_btnSend.gridx = 2;
		gbc_btnSend.gridy = 6;
		panel.add(btnSend, gbc_btnSend);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Parse for additionalRecipients
				//String additionalRecipients = JOptionPane.showInputDialog(null, "Send report to the specificed recipients?\n" + "Add more recipients if desired:\n");
				//for(int i = 0; i< txtAdditionalRecipients.getLineCount(); i++){
					//additionalRecpients += txtAdditionalRecipients.getTe txtAdditionalRecipients.getLineStartOffset(line)
				//}
				
				String additionalRecipients = txtAdditionalRecipients.getText();
				String Title = txtSubject.getText();
				//String Sender = txtSender.getText();
				
				//Parse for preparedReport
				try {
					if (reportManager.sendReport(preparedReport, additionalRecipients, Title, format))
					{
						frmSendReport.dispose();
						JOptionPane.showMessageDialog(null, "Report sent successfully.");
					}
					else
						JOptionPane.showMessageDialog(null, "No recipients have been specificied.  Please add recipients in this window.");
				} catch (Throwable e1) {
					JOptionPane.showMessageDialog(null, "Unable to send report.\n" + e1.getMessage());
					e1.printStackTrace();
					frmSendReport.dispose();
				}
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSendReport.dispose();
			}
		});
	}
	
	private void removeLastCharacter(){
		String OldText = txtAdditionalRecipients.getText();
		int length = OldText.length();
		txtAdditionalRecipients.setText(OldText.substring(0, length-1));
	}

}
