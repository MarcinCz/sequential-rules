package pl.mczpk.med.sr.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.algorithm.SequenceItem;

public class SequenceUtils {

	public static boolean checkIfSubsequence(Sequence sequence, Sequence subsequence) {
		for (int i = 0, j = 0; i < sequence.getSequenceItems().size(); i++) {
			if (sequence.getSequenceItemAt(i).equals(subsequence.getSequenceItemAt(j))) {
				if (++j >= subsequence.getSequenceItems().size()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the second given sequence is a subsequence of the first one
	 * with maximal gap between items in found subsequence.
	 */
	public static boolean checkIfSubsequenceWithMaxGap(Sequence sequence, Sequence subsequence, int maxGap) {
		SubsequenceWithMaxGapChecker checker = new SubsequenceWithMaxGapChecker(sequence, subsequence, maxGap);
		return checker.check();
	}
	
	public static Set<Sequence> join(Sequence s1, Sequence s2) {
		Set<Sequence> sequences = new HashSet<Sequence>();
		Set<Sequence> joinOneDirection = joinOneDirection(s1, s2);
		sequences.addAll(joinOneDirection);
		Set<Sequence> joinOneDirection2 = joinOneDirection(s2, s1);
		sequences.addAll(joinOneDirection2);
		return sequences;
	}
	
	public static Set<Sequence> joinOneDirection(Sequence s1, Sequence s2) {
		Set<Sequence> sequences = new HashSet<Sequence>();
		for(int i = 0; i < s1.getSequenceItems().size() - 1; i++) {
			Sequence firstSubsequence = new Sequence(s1.getSequenceItems().subList(0, s1.getSequenceItems().size() - i));
			Sequence secondSubsequence = new Sequence(s2.getSequenceItems().subList(0, s2.getSequenceItems().size()));
			if(firstSubsequence.getLastSequenceItem().equals(secondSubsequence.getFirstSequenceItem())) {
				int firstSubsequenceSize = firstSubsequence.getSequenceItems().size();
				Sequence joinedSequence = new Sequence(new ArrayList<SequenceItem>(firstSubsequence.getSequenceItems()));
				joinedSequence.getSequenceItems().addAll(secondSubsequence.getSequenceItems());
				joinedSequence.getSequenceItems().remove(firstSubsequenceSize);
				int subJoinedSequences = joinedSequence.getSequenceItems().size() - s1.getSequenceItems().size();
				for(int j = 0; j < subJoinedSequences; j++) {
					sequences.add(new Sequence(joinedSequence.getSequenceItems().subList(j, j + 1 + s1.getSequenceItems().size())));
				}
			} 
		}
		return sequences;
	}
	
}
