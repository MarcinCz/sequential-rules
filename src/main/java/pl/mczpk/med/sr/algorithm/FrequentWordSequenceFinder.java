package pl.mczpk.med.sr.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.mczpk.med.sr.storage.SequenceStorage;
import pl.mczpk.med.sr.util.SequenceUtils;
import pl.mczpk.med.sr.util.SubsequenceWithMaxGapChecker;

public class FrequentWordSequenceFinder implements MaximalFrequentSequenceFinder{

	private List<Sequence> maximalFrequentSequences;
	private interface Gram {
		public Sequence getSequence();
		public List<Sequence> getSubsequences();
	};
	
	public FrequentWordSequenceFinder(){
		setMaximalFrequentSequences(new ArrayList<Sequence>());
	}
	
	@Override
	public List<Sequence> findMaximalFrequentSequence(SequenceStorage storage) {
		Set<Gram> consideredGramsSet = new HashSet<Gram>();
		
		for (final Sequence seq : storage.getFrequentPairSequences()){
			List<Sequence> subsequenceList = new ArrayList<>();
			subsequenceList.add(seq);
			consideredGramsSet.add(createGram(seq, subsequenceList));
		}
		
		while (consideredGramsSet.size() != 0){
			List<Gram> removeList = new ArrayList<Gram>();
			for (Gram gram : consideredGramsSet){
				if (!isSubsequenceOfMax(gram)){
					if (storage.getSequenceInfo(gram.getSequence(), gram.getSubsequences()).isFrequent()){
						Sequence max = storage.expand(gram.getSequence());
						getMaximalFrequentSequences().add(max);
						if (max.equals(gram.getSequence()))
							removeList.add(gram);
					}
					else
						removeList.add(gram);
				}
			}
			consideredGramsSet.removeAll(removeList);
//			prune(consideredGramsSet);
			consideredGramsSet = join(new ArrayList<Gram>(consideredGramsSet));
		}
		return getMaximalFrequentSequences();
	}

	private boolean isSubsequenceOfMax(Gram gram){
		for (Sequence maximalSequence : maximalFrequentSequences){
			if (SequenceUtils.checkIfSubsequence(maximalSequence, gram.getSequence())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param sequences
	 */
//	private void prune(Set<Gram> grams){
//		Set<Sequence> marked = new HashSet<Sequence>();
//		for (Sequence g : sequences){
//			Set<Sequence> LMax = new HashSet<Sequence>();
//			Set<Sequence> RMax = new HashSet<Sequence>();
//			Set<Sequence> LStr = new HashSet<Sequence>();
//			Set<Sequence> RStr = new HashSet<Sequence>();
//			Set<Sequence> LStr_P = new HashSet<Sequence>();
//			Set<Sequence> RStr_P = new HashSet<Sequence>();
//			
//			for (Sequence max : maximalFrequentSequences){
//				Sequence gTmp = new Sequence(g);
//				gTmp.removeLastItem();
//				if (SequenceUtils.checkIfSubsequence(max, gTmp))
//					LMax.add(max);
//			}
//			for (Sequence max : maximalFrequentSequences){
//				Sequence gTmp = new Sequence(g);
//				gTmp.removeFirstItem();
//				if (SequenceUtils.checkIfSubsequence(max, gTmp))
//					RMax.add(max);
//			}
//			for (Sequence max : LMax){
//				Sequence maxTmp = new Sequence(max);
//				Sequence gTmp = new Sequence(g);
//				
//				gTmp.removeLastItem();
//				
//				SubsequenceWithMaxGapChecker checker = new SubsequenceWithMaxGapChecker(maxTmp, gTmp, 0);
//				int i = checker.startingIndex();
//				
//				if(i>0){
//					for(int j = i; j<maxTmp.getSequenceItems().size(); ++j){
//						maxTmp.removeLastItem();
//					}
//					LStr_P.add(maxTmp);
//				}
//				
//			}
//			
//			for (Sequence max : RMax){
//				Sequence maxTmp = new Sequence(max);
//				Sequence gTmp = new Sequence(g);
//				
//				gTmp.removeFirstItem();;
//				
//				SubsequenceWithMaxGapChecker checker = new SubsequenceWithMaxGapChecker(maxTmp, gTmp, 0);
//				int i = checker.startingIndex();
//				
//				if(i>0){
//					for(int j = i + gTmp.getSequenceItems().size(); j>0; --j){
//						maxTmp.removeFirstItem();
//					}
//					RStr_P.add(maxTmp);
//				}
//			}
//			
////			krok 8, 9
//			
//			
//			for(Sequence s1 : LStr){
//				for(Sequence s2 : RStr){
//					Sequence sNew = new Sequence(g);
//					addAtBegin(s1, sNew);
//					addAtEnd(sNew, s2);
//					if (!isSubsequenceOfMax(sNew)){
////						for(Sequence subsequence : sNew){
////							if(!isSubsequenceOfMax(subsequence))
////								
////								
////						}
//					}
//				}
//			}
//		}
//		
//		grams.removeAll(marked);
//	}
//	
	static Gram createGram(final Sequence sequence, final List<Sequence> sequencesList){
		return new Gram(){

			@Override
			public Sequence getSequence() {
				return sequence;
			}

			@Override
			public List<Sequence> getSubsequences() {
				return sequencesList;
			}
			
		};
	}
	
	private void addAtBegin(Sequence prefix, Sequence seq){
		for(int i=prefix.getSequenceItems().size()-1; i>=0; --i){
			seq.addFirstItem(seq.getSequenceItemAt(i));
		}
	}
	
	private void addAtEnd(Sequence seq, Sequence postfix){
		for(int i=0; i<postfix.getSequenceItems().size(); --i){
			seq.addLastItem(seq.getSequenceItemAt(i));
		}
	}
	
	
	private Set<Gram> join(List<Gram> grams){
		Set<Gram> newSequencesList = new HashSet<Gram>();
		for(int i = 0; i < grams.size(); ++i){
			for(int j = i; j < grams.size(); ++j){
				for (Sequence seq : SequenceUtils.join(grams.get(i).getSequence(), grams.get(j).getSequence())){
					Set<Sequence> subsequencesList = new HashSet<Sequence>();
					subsequencesList.addAll(grams.get(i).getSubsequences());
					subsequencesList.addAll(grams.get(j).getSubsequences());
					newSequencesList.add(createGram(seq, new ArrayList(subsequencesList)));
				}
			}
		}
		return newSequencesList;
	}

	private Set<Gram> joinSequences(Gram g1, Gram g2){
		List<SequenceItem> s1 = g1.getSequence().getSequenceItems();
		List<SequenceItem> s2 = g2.getSequence().getSequenceItems();
		Set<Sequence> tmpSet = new HashSet<Sequence>();
		Set<Gram> newFrequentSequences = new HashSet<Gram>();

		tmpSet.addAll(joinSequenceItems(s1, s2));
		tmpSet.addAll(joinSequenceItems(s2, s1));
		
		ArrayList<Sequence> arrayList = new ArrayList<Sequence>(g1.getSubsequences());
		arrayList.addAll(g2.getSubsequences());
		for(Sequence seq : tmpSet){
			newFrequentSequences.add(createGram(seq, arrayList));
		}
		
		return newFrequentSequences;
	}
	
	private Set<Sequence> joinSequenceItems(List<SequenceItem> s1, List<SequenceItem> s2){
		Set<Sequence> newFrequentSequences = new HashSet<Sequence>();
		int length = s1.size();
		Sequence tmp = new Sequence();
		
		search: for(int i = 0; i < length-1; ++i){
			for(int j = 0; j <= i; ++j){
				if(!s1.get(length-i+j-1).equals(s2.get(j))){
					break search;
				}
			}
			
			for(int j = 0; j < length - (i+1); ++j){
				for(int firstSequenceItems = 0; firstSequenceItems <= j; ++firstSequenceItems){
					tmp.addFirstItem(s1.get(length-firstSequenceItems-1));
				}
				for(int secondSequenceItems = 0; secondSequenceItems <= length - j; ++secondSequenceItems){
					tmp.addLastItem(s2.get(secondSequenceItems));
				}
			}
			
		}
		return newFrequentSequences;
	}
	public List<Sequence> getMaximalFrequentSequences() {
		return maximalFrequentSequences;
	}

	public void setMaximalFrequentSequences(List<Sequence> maximalFrequentSequences) {
		this.maximalFrequentSequences = maximalFrequentSequences;
	}
	
}
