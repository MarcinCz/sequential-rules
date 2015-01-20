package pl.mczpk.med.sr.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.algorithm.SequenceInfo;
import pl.mczpk.med.sr.algorithm.SequenceItem;
import pl.mczpk.med.sr.storage.StoredSequenceInfo.StoredSequenceInfoImpl;
import pl.mczpk.med.sr.util.LimitedQueue;
import pl.mczpk.med.sr.util.MapUtils;
import pl.mczpk.med.sr.util.SequenceUtils;

abstract class AbstractSequenceStorage implements SequenceStorage {

	private final Logger logger = Logger.getLogger(this.getClass());

	private List<Sequence> documentSequences = new ArrayList<Sequence>();

	private Map<Sequence, StoredSequenceInfo> frequentSequencesInfoMap = new HashMap<Sequence, StoredSequenceInfo>();
	private Set<Sequence> notFrequentSequences = new HashSet<Sequence>();

	private Map<SequenceItem, List<Sequence>> pairsStartingWithItemMap = new HashMap<SequenceItem, List<Sequence>>();

	private Map<SequenceItem, List<Sequence>> pairsEndingWithItemMap = new HashMap<SequenceItem, List<Sequence>>();

	@Override
	public SequenceInfo getSequenceInfo(Sequence sequence) {

		//check in already stored frequent sequences
		StoredSequenceInfo storedInfo = frequentSequencesInfoMap.get(sequence);
		if (storedInfo != null) {
			return storedInfo;
		}

		//check for already stored not frequent sequences
		for (Sequence notFrequentSeq : notFrequentSequences) {
			if (sequence.equals(notFrequentSeq) || SequenceUtils.checkIfSubsequence(sequence, notFrequentSeq)) {
				return SequenceInfo.NOT_FREQUENT_SEQUENCE;
			}
		}

		//check in all document sequences
		return getSequenceInfoFromDocumentSequences(sequence, documentSequences);

	}

	@Override
	public SequenceInfo getSequenceInfo(Sequence sequence, List<Sequence> subsequences) {
		//check in already stored frequent sequences
		StoredSequenceInfo storedInfo = frequentSequencesInfoMap.get(sequence);
		if (storedInfo != null) {
			return storedInfo;
		}

		//check for already stored not frequent sequences
		for (Sequence notFrequentSeq : notFrequentSequences) {
			if (sequence.equals(notFrequentSeq) || SequenceUtils.checkIfSubsequence(sequence, notFrequentSeq)) {
				return SequenceInfo.NOT_FREQUENT_SEQUENCE;
			}
		}
				
		List<Sequence> commonDocumentSequences = null;
		for (int i = 0; i < subsequences.size(); i++) {
			StoredSequenceInfo storedSubseqeunceInfo = frequentSequencesInfoMap.get(subsequences.get(i));

			if (storedSubseqeunceInfo == null) {
				logger.warn("Checking all documents. No info stored for subsequence " + subsequences.get(i));
				return getSequenceInfoFromDocumentSequences(sequence, documentSequences);
			}

			if (i == 0) {
				commonDocumentSequences = new ArrayList<Sequence>(storedSubseqeunceInfo.getStoringSequnces());
			} else {
				commonDocumentSequences.retainAll(storedSubseqeunceInfo.getStoringSequnces());
			}
		}

		return getSequenceInfoFromDocumentSequences(sequence, commonDocumentSequences);
	}

	private SequenceInfo getSequenceInfoFromDocumentSequences(Sequence sequence, List<Sequence> documentSequences) {
		int support = 0;
		final List<Sequence> storingSequences = new ArrayList<Sequence>();
		for (Sequence documentSequence : documentSequences) {
			if (SequenceUtils.checkIfSubsequenceWithMaxGap(documentSequence, sequence, getMaxGapBetweenSequenceItems())) {
				support += 1;
				storingSequences.add(documentSequence);
			}
		}

		final int finalSupport = support;
		if (support > getMinSequenceSupport()) {
			StoredSequenceInfo info = new StoredSequenceInfoImpl(finalSupport, true, documentSequences);
			frequentSequencesInfoMap.put(sequence, info);
			return info;
		} else {
			notFrequentSequences.add(sequence);
			return SequenceInfo.NOT_FREQUENT_SEQUENCE;
		}
	}

	@Override
	public Set<Sequence> getFrequentPairSequences() {
		logger.trace("Getting frequent pair-sequences.");
		Map<Sequence, Integer> sequenceCounterMap = new HashMap<Sequence, Integer>();
		Map<Sequence, List<Sequence>> sequenceDocumentsMap = new HashMap<Sequence, List<Sequence>>();

		//get all pair-sequences with their support
		for (Sequence documentSequence : documentSequences) {
			Set<Sequence> pairSequences = getPairSequences(documentSequence);
			for (Sequence pairSequence : pairSequences) {
				MapUtils.putNewValueToListMap(sequenceDocumentsMap, pairSequence, documentSequence);
				Integer currentSeqCount = sequenceCounterMap.get(pairSequence);
				if (currentSeqCount == null) {
					sequenceCounterMap.put(pairSequence, 1);
				} else {
					sequenceCounterMap.put(pairSequence, currentSeqCount + 1);
				}
			}
		}

		//find and return frequent pair-sequences
		int minSupport = getMinSequenceSupport();
		Set<Sequence> frequentPairSequences = new HashSet<Sequence>();
		for (Sequence pairSequence : sequenceCounterMap.keySet()) {
			int pairSupport = sequenceCounterMap.get(pairSequence);
			if (pairSupport > minSupport) {
				frequentPairSequences.add(pairSequence);

				//store found pairs in storage structures
				StoredSequenceInfo info = new StoredSequenceInfoImpl(pairSupport, true, sequenceDocumentsMap.get(pairSequence));
				MapUtils.putNewValueToListMap(pairsStartingWithItemMap, pairSequence.getFirstSequenceItem(), pairSequence);
				MapUtils.putNewValueToListMap(pairsEndingWithItemMap, pairSequence.getLastSequenceItem(), pairSequence);
				frequentSequencesInfoMap.put(pairSequence, info);
			}
		}
		logger.debug(String.format("Found %s frequent pairs", frequentPairSequences.size()));
		return frequentPairSequences;
	}

	private Set<Sequence> getPairSequences(Sequence sequence) {
		LimitedQueue<SequenceItem> visitedItemsWithinGap = new LimitedQueue<SequenceItem>(getMaxGapBetweenSequenceItems());
		Set<Sequence> sequencesSet = new HashSet<Sequence>();
		for (SequenceItem sequenceItem : sequence.getSequenceItems()) {
			for (SequenceItem visitedItem : visitedItemsWithinGap) {
				sequencesSet.addAll(getAllSequenceCombinations(visitedItem, sequenceItem));
			}
			visitedItemsWithinGap.add(sequenceItem);
		}
		return sequencesSet;
	}

	static Set<Sequence> getAllSequenceCombinations(SequenceItem itemFirst, SequenceItem itemSecond) {
		Set<Sequence> possibleSequences = new HashSet<Sequence>();
		for (int i = 0; i < itemFirst.getElements().size(); i++) {
			for (int j = 0; j < itemSecond.getElements().size(); j++) {
				SequenceItem newItemFirst = new SequenceItem(itemFirst.getElements().subList(i, itemFirst.getElements().size())
						.toArray(new String[0]));
				SequenceItem newItemSecond = new SequenceItem(itemSecond.getElements().subList(j, itemSecond.getElements().size())
						.toArray(new String[0]));
				possibleSequences.add(new Sequence(newItemFirst, newItemSecond));
			}
		}
		return possibleSequences;
	}

	@Override
	public Sequence expand(Sequence sequence) {
		Sequence expandedSequence = expandSequence(sequence);
		logger.debug(String.format("Sequence %s expanded to %s", sequence, expandedSequence));
		return expandedSequence;
	}

	private Sequence expandSequence(Sequence sequence) {
		if (frequentSequencesInfoMap.get(sequence) == null) {
			throw new IllegalStateException("Tried to expand sequence which is not frequent or wasn't checked before.");
		}
		for (int i = 0; i <= sequence.getSequenceItems().size(); i++) {

			if (i == 0) {
				if (pairsEndingWithItemMap.get(sequence.getFirstSequenceItem()) == null) {
					continue;
				}
				//adding item at front
				for (Sequence pairSequence : pairsEndingWithItemMap.get(sequence.getFirstSequenceItem())) {
					Sequence expanedSequence = new Sequence(sequence);
					expanedSequence.addFirstItem(pairSequence.getFirstSequenceItem());
					List<Sequence> commonDocumentSequences = new ArrayList<Sequence>(frequentSequencesInfoMap.get(sequence).getStoringSequnces());
					commonDocumentSequences.retainAll(frequentSequencesInfoMap.get(pairSequence).getStoringSequnces());
					if (getSequenceInfoFromDocumentSequences(expanedSequence, commonDocumentSequences).isFrequent()) {
						return expandSequence(expanedSequence);
					}
				}
			} else if (i == sequence.getSequenceItems().size()) {
				if (pairsStartingWithItemMap.get(sequence.getLastSequenceItem()) == null) {
					continue;
				}
				//adding item at tail
				for (Sequence pairSequence : pairsStartingWithItemMap.get(sequence.getLastSequenceItem())) {
					Sequence expanedSequence = new Sequence(sequence);
					expanedSequence.addLastItem(pairSequence.getLastSequenceItem());
					List<Sequence> commonDocumentSequences = new ArrayList<Sequence>(frequentSequencesInfoMap.get(sequence).getStoringSequnces());
					commonDocumentSequences.retainAll(frequentSequencesInfoMap.get(pairSequence).getStoringSequnces());
					if (getSequenceInfoFromDocumentSequences(expanedSequence, commonDocumentSequences).isFrequent()) {
						return expandSequence(expanedSequence);
					}
				}
			} else {
				if (pairsStartingWithItemMap.get(sequence.getSequenceItemAt(i - 1)) == null
						|| pairsEndingWithItemMap.get(sequence.getSequenceItemAt(i)) == null) {
					continue;
				}
				//adding item in the middle
				for (Sequence pairSequenceFirst : pairsStartingWithItemMap.get(sequence.getSequenceItemAt(i - 1))) {
					for (Sequence pairSequenceSecond : pairsEndingWithItemMap.get(sequence.getSequenceItemAt(i))) {
						//check if pairs can be connected
						if (!pairSequenceFirst.getLastSequenceItem().equals(pairSequenceSecond.getFirstSequenceItem())) {
							continue;
						}
						//get common storing documents for pairs
						List<Sequence> firstPairSequences = frequentSequencesInfoMap.get(pairSequenceFirst).getStoringSequnces();
						List<Sequence> secondPairSequences = frequentSequencesInfoMap.get(pairSequenceSecond).getStoringSequnces();
						List<Sequence> commonDocumentSequences = new ArrayList<Sequence>(frequentSequencesInfoMap.get(sequence).getStoringSequnces());
						commonDocumentSequences.retainAll(firstPairSequences);
						commonDocumentSequences.retainAll(secondPairSequences);
						if (commonDocumentSequences.size() == 0) {
							continue;
						}

						//expand for pairs
						Sequence expanedSequence = new Sequence(sequence);
						expanedSequence.addItemAtIndex(i, pairSequenceFirst.getLastSequenceItem());
						if (getSequenceInfoFromDocumentSequences(expanedSequence, commonDocumentSequences).isFrequent()) {
							return expandSequence(expanedSequence);
						}
					}
				}
			}
		}
		return sequence;
	}

	protected void addSequenceToStorage(Sequence sequence) {
		documentSequences.add(sequence);
	}

	protected abstract int getMaxGapBetweenSequenceItems();

	protected abstract int getMinSequenceSupport();
}
