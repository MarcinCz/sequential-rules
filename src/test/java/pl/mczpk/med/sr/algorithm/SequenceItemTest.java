package pl.mczpk.med.sr.algorithm;

import static org.junit.Assert.*;

import org.junit.Test;

public class SequenceItemTest {

	@Test
	public void shouldBeEqual() {
		SequenceItem item1 = new SequenceItem("a");
		item1.addLastElement("b");
		SequenceItem item2 = new SequenceItem("a");
		item2.addLastElement("b");
		
		assertEquals(item1, item2);
		assertEquals(item1.hashCode(), item2.hashCode());
	}

}
