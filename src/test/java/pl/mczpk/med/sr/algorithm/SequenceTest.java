package pl.mczpk.med.sr.algorithm;

import static org.junit.Assert.*;

import org.junit.Test;

public class SequenceTest {

	@Test
	public void shouldBeEqual() {
		SequenceItem item1 = new SequenceItem("a");
		SequenceItem item2 = new SequenceItem("b");
		Sequence s1 = new Sequence(item1, item2);
		Sequence s2 = new Sequence(item1, item2);
		
		assertEquals(s1, s2);
		assertEquals(s1.hashCode(), s2.hashCode());
	}

}
