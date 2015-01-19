package pl.mczpk.med.sr.util;

import pl.mczpk.med.sr.algorithm.Sequence;

public class SequenceUtils {
	
	public static boolean checkIfSubsequence(Sequence sequence, Sequence subsequence) {
		for (int i = 0, j = 0; i < sequence.getSequenceItems().size(); i++) {
			if(sequence.getSequenceItemAt(i).equals(subsequence.getSequenceItemAt(j))) {
				if(++j >= subsequence.getSequenceItems().size()) {
					return true;
				}
			}
		}
		return false;
	}
}
