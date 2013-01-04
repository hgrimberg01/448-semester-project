/**
 * Name: treeMultiSelectModel.java
 * Description: A selection model to make multi-selection easy
 * 
 * @author Seth Polsley
 * Date Created: 11/15/12
 */

package com.gui;

import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;

/**
 * Overrides the built-in tree selection model to make multi-selection easy
 *
 */
public class treeMultiSelectModel extends DefaultTreeSelectionModel {

	private static final long serialVersionUID = 1L;
	
	
	/**
	 *  Adds selection path
	 */
	@Override
	public void addSelectionPath(TreePath path) {
		super.addSelectionPath(path);
		return;
	}
	
	
	/**
	 * Adds selection paths of there is more than one selected
	 */
	@Override
	public void addSelectionPaths(TreePath[] paths) {
		if(paths != null) {
			for(TreePath path : paths) {
				
				TreePath[] toAdd = new TreePath[1];
				toAdd[0] = path;

				if (isPathSelected(path)) {
					// Remove already selected item
					super.removeSelectionPaths(toAdd);
				} else {
					// Add any new item
					super.addSelectionPaths(toAdd);
				}
			}
		}
		return;
	}
	
}
