package pl.mczpk.med.sr.util;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.algorithm.SequenceItem;

public class SequenceUtilsTest {

	@Test
	public void shouldChecIfContainsSubsequence() {
		SequenceItem item1 = new SequenceItem("1");
		SequenceItem item2 = new SequenceItem("2");
		SequenceItem item3 = new SequenceItem("3");
		SequenceItem item4 = new SequenceItem("4");
		Sequence seq = new Sequence(item1, item2, item1, item3);
		
		assertTrue(SequenceUtils.checkIfSubsequence(seq, new Sequence(item1, item2)));
		assertTrue(SequenceUtils.checkIfSubsequence(seq, new Sequence(item1, item2, item3)));
		assertTrue(SequenceUtils.checkIfSubsequence(seq, new Sequence(item1, item3)));
		assertTrue(SequenceUtils.checkIfSubsequence(seq, new Sequence(item2, item1)));
		
		assertFalse(SequenceUtils.checkIfSubsequence(seq, new Sequence(item2, item1, item4)));
	}
}
