package pl.mczpk.med.sr.storage;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.algorithm.SequenceItem;

public class AbstractSequenceStorageTest {

	@Test
	public void shouldProduceAllSequenceCombinations() {
		SequenceItem item1 = new SequenceItem("a", "b");
		SequenceItem item1prunned = new SequenceItem("b");
		SequenceItem item2 = new SequenceItem("c", "d");
		SequenceItem item2prunned = new SequenceItem("d");
		Set<Sequence> items = AbstractSequenceStorage.getAllSequenceCombinations(item1, item2);
		assertEquals(4, items.size());
		assertTrue(items.contains(new Sequence(item1, item2)));
		assertTrue(items.contains(new Sequence(item1prunned, item2)));
		assertTrue(items.contains(new Sequence(item1, item2prunned)));
		assertTrue(items.contains(new Sequence(item1prunned, item2prunned)));
	}

}
