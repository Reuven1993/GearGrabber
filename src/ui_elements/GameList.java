package ui_elements;

import java.awt.Color;
import java.awt.Font;
import java.util.LinkedList;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import base.Game;

/*
 * The GameList is a class that handles dashboard Lists (multiple selection).
 * To have a specific behavior to a List, you should create a class that extends GameList
 * and overrides it listAction method.
 */

public class GameList extends UIElement{

	protected JList<String> list;
	// Keeping an ordered list of the indicies chosen in the list, by the order of their selection 
	protected LinkedList<Number> currentChosenPathIndicies; 
	protected String[] options;

	// why can we not care for the type safety? we define it in the lambda
	@SuppressWarnings("unchecked")
	public GameList(String id, String name, int posX, int posY, int width, int height, String[] options) {
		super(id, posX, posY, width, height, new JList<String>(options));

		this.list = (JList<String>) getJComponent();
		this.options = options;
		this.currentChosenPathIndicies = new LinkedList<>();
		this.list.setFont(new Font("Ariel", Font.BOLD, 14));
		this.list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.list.setVisibleRowCount(4);
		this.list.setBackground(Color.darkGray);
		this.list.setSelectedIndex(0);
		this.list.setForeground(Color.WHITE);
		this.list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				action();
				// Making changes in a temp linked list to avoid concurrent modification error
				LinkedList<Number> temp = new LinkedList<>();

				for(Number num : currentChosenPathIndicies) {
					temp.add(num);
				}
				// Remove unchoden idicies:
				// Go over all indicies in the previous see paths list
				// for every index, check if it was removed and if so, remove it here also
				boolean didSee = false;
				for(Number index: currentChosenPathIndicies) {
					didSee = false;
					for(int i = 0; i< list.getSelectedIndices().length;i++) {
						if(index.intValue() == list.getSelectedIndices()[i]) {
							didSee = true;
						}
					}
					if(!didSee) {
						temp.remove(index);
					}
				}

				// Add newly chosen idicies:
				// Go over all indicies in the new indicies list
				// for every index, check if it was added and if so, add it here also
				for(int i = 0; i< list.getSelectedIndices().length;i++) {
					didSee = false;
					for(Number index: currentChosenPathIndicies) {
						if(index.intValue() == list.getSelectedIndices()[i]) {
							didSee = true;
						}
					}
					
					if(!didSee) {
						temp.add((Number) list.getSelectedIndices()[i]);
					}
 				}
				currentChosenPathIndicies = temp;
			}
		});
	}

	public String[] getSelectedOptions() {
		String[] selectedOptions = new String[currentChosenPathIndicies.size()];
		for (int i = 0; i < currentChosenPathIndicies.size(); i++) {
			selectedOptions[i] = String.valueOf(list.getModel().getElementAt(currentChosenPathIndicies.get(i).intValue()));
		}
		return selectedOptions;
	}

		//This function is a placeholder and should be overriden in derived specific buttons
	public void action() {
		System.out.println(id + " clicked");
		Game.UI().frame().requestFocus();
	}
}
