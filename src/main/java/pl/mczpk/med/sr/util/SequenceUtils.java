package pl.mczpk.med.sr.util;

import pl.mczpk.med.sr.algorithm.Sequence;

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
	
}
