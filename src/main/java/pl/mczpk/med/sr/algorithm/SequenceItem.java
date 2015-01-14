package pl.mczpk.med.sr.algorithm;

import java.util.LinkedList;

public class SequenceItem {
	private LinkedList<String> elements = new LinkedList<String>();
	
	public SequenceItem() {
	}
	
	public SequenceItem(String... elements) {
		for(String element: elements) {
			this.elements.add(element);
		}
	}
	
	public void addFirstElement(String element) {
		elements.addFirst(element);
	}
	
	public void addLastElement(String element)  {
		elements.addLast(element);
	}
	
	public void removeFirstElement() {
		elements.removeFirst();
	}
	
	public void removeLastElement() {
		elements.removeLast();
	}
}
