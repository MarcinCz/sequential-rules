package pl.mczpk.med.sr.algorithm;

import java.util.LinkedList;
import java.util.List;

public class SequenceItem {
	private LinkedList<String> elements = new LinkedList<String>();

	public SequenceItem() {
	}

	public SequenceItem(String... elements) {
		for (String element : elements) {
			this.elements.add(element);
		}
	}

	public void addFirstElement(String element) {
		elements.addFirst(element);
	}

	public void addLastElement(String element) {
		elements.addLast(element);
	}

	public List<String> getElements() {
		return elements;
	}

	public void removeFirstElement() {
		elements.removeFirst();
	}

	public void removeLastElement() {
		elements.removeLast();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof SequenceItem)) {
			return false;
		} else {
			SequenceItem sequenceItem = (SequenceItem) obj;
			return this.elements.equals(sequenceItem.elements);
		}
	}

	@Override
	public int hashCode() {
		return elements.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<");
		for (int i = 0; i < elements.size(); i++) {
			builder.append(elements.get(i));
			if(i < elements.size() - 1) {
				builder.append(", ");
			}
		}
		builder.append(">");
		return builder.toString();
	}
}
