package pl.mczpk.med.sr.algorithm;

import java.util.LinkedList;
import java.util.List;

public class Sequence {
	private LinkedList<SequenceItem> items = new LinkedList<SequenceItem>();
	
	public Sequence() {
	}
	
	public Sequence(SequenceItem... items) {
		for(SequenceItem item: items) {
			this.items.add(item);
		}
	}
	
	public void addFirstItem(SequenceItem item) {
		items.addFirst(item);
	}
	
	public void addLastItem(SequenceItem item)  {
		items.addLast(item);
	}
	
	public List<SequenceItem> getSequenceItems() {
		return items;
	}
	
	public SequenceItem getSequenceItemAt(int index) {
		return items.get(index);
	}
	 
	public void removeFirstItem() {
		items.removeFirst();
	}
	
	public void removeLastItem() {
		items.removeLast();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Sequence)) {
			return false;
		} else {
			Sequence sequence = (Sequence) obj;
			return this.items.equals(sequence.items);
		}
	}
	
	@Override
	public int hashCode() {
		return items.hashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(SequenceItem item: items) {
			builder.append(item + " ");
		}
		return builder.toString();
	}
}
