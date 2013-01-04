/**
 * Name: SortableListModel.java
 * Description: Extends the Abstract List Model to allow sorting
 * 
 * @author Seth Polsley
 * Date Created: 11/27/12
 */

package com.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractListModel;

/**
 * Extends the AbstractListModel to allow sorting
 * 
 * @author spolsley
 *
 * @param <T>
 */
public class SortableListModel<T> extends AbstractListModel {
	private static final long serialVersionUID = 1L;
	List<Object> model;
	
	/**
	 * Initializes a SortableListModel object
	 */
	public SortableListModel() {
		model = new ArrayList<Object>();
	}
	
	/**	Initializes a SortableListModel from a vector of Objects
	 * 
	 * @param list		Vector of objects with which to initialize the SortableListModel object
	 */
	public SortableListModel(Vector<Object> list) {
		model = new ArrayList<Object>(list);
	}

	/**
	 * Sorts the items in the SortableListModel object
	 * using Collections.sort sort operations
	 */
	public void sort() {
		Collections.sort(model, new Comparator<Object>() {
			public int compare(Object a, Object b) {
				return a.toString().compareToIgnoreCase(b.toString());
			}
		});
	}
	
	
	/**
	 * Gets the size of the SortableListModel object
	 */
	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return model.size();
	}
	
	
	/**
	 * Returns the element at the given index
	 * 
	 * @param index		Index at which to find the element
	 */
	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Object getElementAt(int index) {
		return model.toArray()[index];
	}
	
	/** Adds an object to the SortableListModel object
	 * 
	 * @param element		Element to add to the SortableListModel
	 */
	public void addElement(Object element) {
		if (model.add(element)) {
			fireContentsChanged(this, 0, getSize());
		}
	}
	  
	/** Adds an array of elements to the SortableListModel object
	 * 
	 * @param elements
	 */
	public void addAll(Object elements[]) {
		Collection<Object> c = Arrays.asList(elements);
		model.addAll(c);
		fireContentsChanged(this, 0, getSize());
	}
	
	/** Determines if the Object is contained in the SortableListModel object
	 * 
	 * @param element		Object to verify its existence in the model
	 * @return				True if the Object is in the model, false if it is not
	 */
	public boolean contains(Object element) {
		return model.contains(element);
	}
	
	/** Removes the specified Object from the SortableListModel object
	 * 
	 * @param element		Object to remove
	 * @return				True if removal was successful, false if it was not
	 */
	public boolean removeElement(Object element) {
		boolean removed = model.remove(element);
		if (removed) {
			fireContentsChanged(this, 0, getSize());
		}
		return removed;
	}
	
	/**
	 * Removes all elements from the SortableListModel object
	 */
	public void removeAllElements() {
		model.clear();
		fireContentsChanged(this, 0, getSize());
	}
	
}