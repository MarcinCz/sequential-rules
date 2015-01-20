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

	@Test
	public void shouldChecIfContainsSubsequenceWithMaxGap() {
		SequenceItem item1 = new SequenceItem("1");
		SequenceItem item2 = new SequenceItem("2");
		SequenceItem item3 = new SequenceItem("3");
		SequenceItem item4 = new SequenceItem("4");
		SequenceItem item5 = new SequenceItem("5");
		Sequence seq = new Sequence(item1, item4, item4, item2, item4, item4, item4, item1, item4, item4, item3, item1);

		assertTrue(SequenceUtils.checkIfSubsequenceWithMaxGap(seq, new Sequence(item1, item2), 2));
		assertTrue(SequenceUtils.checkIfSubsequenceWithMaxGap(seq, new Sequence(item1, item3), 2));
		assertTrue(SequenceUtils.checkIfSubsequenceWithMaxGap(seq, new Sequence(item3, item1), 2));
		assertTrue(SequenceUtils.checkIfSubsequenceWithMaxGap(seq, new Sequence(item1, item3, item1), 2));

		assertFalse(SequenceUtils.checkIfSubsequenceWithMaxGap(seq, new Sequence(item1, item1), 2));
		assertFalse(SequenceUtils.checkIfSubsequenceWithMaxGap(seq, new Sequence(item2, item1), 2));
		assertFalse(SequenceUtils.checkIfSubsequenceWithMaxGap(seq, new Sequence(item2, item3), 2));
		assertFalse(SequenceUtils.checkIfSubsequenceWithMaxGap(seq, new Sequence(item2, item5), 2));
		assertFalse(SequenceUtils.checkIfSubsequenceWithMaxGap(seq, new Sequence(item5, item5), 2));
	}
}
