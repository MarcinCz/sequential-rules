package pl.mczpk.med.sr.algorithm;

import java.util.LinkedList;

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
}
