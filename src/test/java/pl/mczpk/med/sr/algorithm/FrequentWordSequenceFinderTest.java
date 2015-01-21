package pl.mczpk.med.sr.algorithm;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import pl.mczpk.med.sr.util.SequenceUtils;

public class FrequentWordSequenceFinderTest {

//	@Ignore
//	@Test
//	public void shouldJoinSequences2(){
//		SequenceItem item1 = new SequenceItem("1");
//		SequenceItem item2 = new SequenceItem("2");
//		SequenceItem item3 = new SequenceItem("3");
//		SequenceItem item4 = new SequenceItem("4");
//		SequenceItem item5 = new SequenceItem("5");
//		
//		Sequence seq1 = new Sequence(item1, item2, item3, item4);
//		Sequence seq2 = new Sequence(item3, item4, item5, item1);
//		FrequentWordSequenceFinder.Gram g1 = FrequentWordSequenceFinder.createGram(seq1, null);
//		FrequentWordSequenceFinder.Gram g2 = FrequentWordSequenceFinder.createGram(seq2, null);
//		
//		Set<FrequentWordSequenceFinder.Gram> sequences2 = FrequentWordSequenceFinder.joinSequences(g1, g2);
//		Set<Sequence> sequences = new HashSet<Sequence>();
//		for(FrequentWordSequenceFinder.Gram gram : sequences2){
//			sequences.add(sequences2.iterator().next().getSequence());
//		}
//		assertEquals(5, sequences.size());
//		assertTrue(sequences.contains(new Sequence(item1, item2, item3, item4, item5)));
//		assertTrue(sequences.contains(new Sequence(item2, item3, item4, item5, item1)));
//		assertTrue(sequences.contains(new Sequence(item5, item1, item2, item3, item4)));
//		assertTrue(sequences.contains(new Sequence(item4, item5, item1, item2, item3)));
//		assertTrue(sequences.contains(new Sequence(item3, item4, item5, item1, item2)));
//		
//		sequences2 = FrequentWordSequenceFinder.joinSequences(g1, g2);
//		sequences = new HashSet<Sequence>();
//		for(FrequentWordSequenceFinder.Gram gram : sequences2){
//			sequences.add(sequences2.iterator().next().getSequence());
//		}
//		assertEquals(8, sequences.size());
//		assertTrue(sequences.contains(new Sequence(item1, item2, item4, item4, item4)));
//		assertTrue(sequences.contains(new Sequence(item2, item4, item4, item4, item5)));
//		assertTrue(sequences.contains(new Sequence(item4, item4, item4, item5, item1)));
//		assertTrue(sequences.contains(new Sequence(item4, item4, item5, item1, item2)));
//		
//		sequences2 = FrequentWordSequenceFinder.joinSequences(g1, g2);
//		sequences = new HashSet<Sequence>();
//		for(FrequentWordSequenceFinder.Gram gram : sequences2){
//			sequences.add(sequences2.iterator().next().getSequence());
//		}
//		assertEquals(1, sequences.size());
//		assertTrue(sequences.contains(new Sequence(item1, item1, item1)));
//	}

}
