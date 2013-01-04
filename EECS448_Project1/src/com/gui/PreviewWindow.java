/**
 * Name: PreviewWindow.java
 * Description: Displays a preview of a report
 * 
 * @author Howard Grimberg
 * Date Created: 11/9/12
 */

package com.gui;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class PreviewWindow {

	private JFrame frmPreviewReport;
	private String contents;


	/**
	 * Create the application.
	 */
	public PreviewWindow(String content) {
		
		this.contents = content;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPreviewReport = new JFrame();
		frmPreviewReport.setAlwaysOnTop(true);
		frmPreviewReport.setTitle("Preview Report");
		frmPreviewReport.setBounds(100, 100, 898, 733);
		frmPreviewReport.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmPreviewReport.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("5dlu"),
				RowSpec.decode("max(75dlu;default):grow(3)"),
				RowSpec.decode("5dlu"),
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JScrollPane scrollPane = new JScrollPane();
		frmPreviewReport.getContentPane().add(scrollPane, "1, 2, default, fill");
		
		if (contents.startsWith("<html>"))
		{
			JLabel results = new JLabel(contents);
			results.setVerticalAlignment(JLabel.TOP);
			scrollPane.setViewportView(results);
		}
		else
		{
			JTextArea results = new JTextArea();
			results.setTabSize(5);
			results.setFont(new Font("Monospaced", Font.PLAIN, 12));
			results.setEditable(false);
			results.setText(contents);
			scrollPane.setViewportView(results);
		}
		
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmPreviewReport.dispose();
			}
		});
		frmPreviewReport.getContentPane().add(btnClose, "1, 4");
		frmPreviewReport.setVisible(true);
	}

}
