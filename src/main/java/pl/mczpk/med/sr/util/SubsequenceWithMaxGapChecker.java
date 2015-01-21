package pl.mczpk.med.sr.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.algorithm.SequenceItem;

public class SubsequenceWithMaxGapChecker {
	private Sequence sequence;
	private Sequence subseqeunce;
	private int maxGap;
	private Map<SequenceItem, List<Integer>> subsequenceItemsPositionsInSequence;

	public SubsequenceWithMaxGapChecker(Sequence sequence, Sequence subseqeunce, int maxGap) {
		this.sequence = sequence;
		this.subseqeunce = subseqeunce;
		this.maxGap = maxGap;
	}

	public boolean check() {
		subsequenceItemsPositionsInSequence = new HashMap<SequenceItem, List<Integer>>();

		for (int i = 0; i < sequence.getSequenceItems().size(); i++) {
			SequenceItem item = sequence.getSequenceItemAt(i);

			if (subseqeunce.getSequenceItems().contains(item)) {
				MapUtils.putNewValueToListMap(subsequenceItemsPositionsInSequence, item, i);
			}
		}

		if (subsequenceItemsPositionsInSequence.get(subseqeunce.getSequenceItemAt(0)) == null) {
			//first item from subsequence was not found in sequence
			return false;
		}

		for (int sequencePosition : subsequenceItemsPositionsInSequence.get(subseqeunce.getSequenceItemAt(0))) {
			if (checkNextSubsequenceItem(1, sequencePosition)) {
				return true;
			}
		}
		return false;
	}
	
	public int startingIndex() {
		subsequenceItemsPositionsInSequence = new HashMap<SequenceItem, List<Integer>>();

		for (int i = 0; i < sequence.getSequenceItems().size(); i++) {
			SequenceItem item = sequence.getSequenceItemAt(i);

			if (subseqeunce.getSequenceItems().contains(item)) {
				MapUtils.putNewValueToListMap(subsequenceItemsPositionsInSequence, item, i);
			}
		}

		if (subsequenceItemsPositionsInSequence.get(subseqeunce.getSequenceItemAt(0)) == null) {
			//first item from subsequence was not found in sequence
			return -1;
		}

		for (int sequencePosition : subsequenceItemsPositionsInSequence.get(subseqeunce.getSequenceItemAt(0))) {
			if (checkNextSubsequenceItem(1, sequencePosition)) {
				return sequencePosition;
			}
		}
		return -1;
	}

	private boolean checkNextSubsequenceItem(int currentSubsequencePosition, int currentSequencePosition) {
		if (currentSubsequencePosition >= subseqeunce.getSequenceItems().size()) {
			return true;
		}

		List<Integer> subseqeunceItemPositions = subsequenceItemsPositionsInSequence.get(subseqeunce.getSequenceItemAt(currentSubsequencePosition));
		if (subseqeunceItemPositions == null) {
			//one of the items from subsequence was not found in sequence
			return false;
		}
		for (int nextPossiblePosition : subseqeunceItemPositions) {
			if (nextPossiblePosition > currentSequencePosition && nextPossiblePosition <= currentSequencePosition + maxGap + 1)
				if (checkNextSubsequenceItem(++currentSubsequencePosition, nextPossiblePosition)) {
					return true;
				}
		}

		return false;
	}

}
