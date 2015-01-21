package pl.mczpk.med.sr.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Sequence{
	private List<SequenceItem> items = new ArrayList<SequenceItem>();
	
	public Sequence() {
	}
	
	public Sequence(Sequence sequence) {
		this.items = new ArrayList<SequenceItem>(sequence.items);
	}
	
	public Sequence(List<SequenceItem> sequenceItems) {
		this.items = sequenceItems;
	}
	
	public Sequence(SequenceItem... items) {
		for(SequenceItem item: items) {
			this.items.add(item);
		}
	}
	
	public void addItemAtIndex(int index, SequenceItem item) {
		items.add(index, item);
	}
	
	public void addFirstItem(SequenceItem item) {
		items.add(0, item);
	}
	
	public void addLastItem(SequenceItem item)  {
		items.add(item);
	}
	
	
	public SequenceItem getFirstSequenceItem() {
		return items.get(0);
	}
	
	public SequenceItem getLastSequenceItem() {
		return items.get(items.size() - 1);
	}
	
	public List<SequenceItem> getSequenceItems() {
		return items;
	}
	
	public SequenceItem getSequenceItemAt(int index) {
		return items.get(index);
	}
	 
	public void removeFirstItem() {
		items.remove(0);
	}
	
	public void removeLastItem() {
		items.remove(items.size() - 1);
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
