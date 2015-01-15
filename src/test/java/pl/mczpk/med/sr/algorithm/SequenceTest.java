package pl.mczpk.med.sr.algorithm;

import static org.junit.Assert.*;

import org.junit.Test;

public class SequenceTest {

	@Test
	public void shouldBeEqual() {
		SequenceItem item1 = new SequenceItem("a");
		SequenceItem item2 = new SequenceItem("b");
		Sequence s1 = new Sequence();
		s1.addLastItem(item1);
		s1.addLastItem(item2);
		Sequence s2 = new Sequence();
		s2.addLastItem(item1);
		s2.addLastItem(item2);
		
		assertEquals(s1, s2);
		assertEquals(s1.hashCode(), s2.hashCode());
	}

}
