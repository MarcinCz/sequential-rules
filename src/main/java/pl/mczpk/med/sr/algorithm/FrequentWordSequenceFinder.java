package pl.mczpk.med.sr.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.mczpk.med.sr.storage.SequenceStorage;
import pl.mczpk.med.sr.util.SequenceUtils;
import pl.mczpk.med.sr.util.SubsequenceWithMaxGapChecker;

public class FrequentWordSequenceFinder implements MaximalFrequentSequenceFinder{

	private Set<Sequence> maximalFrequentSequences;
	
	public FrequentWordSequenceFinder(){
		setMaximalFrequentSequences(new HashSet<Sequence>());
	}
	
	@Override
	public Set<Sequence> findMaximalFrequentSequence(SequenceStorage storage) {
		Integer k = 2;
		Map<Integer, Set<Sequence>> frequentSequencesSetsMap = new HashMap<Integer, Set<Sequence>>();
		
		frequentSequencesSetsMap.put(2, storage.getFrequentPairSequences());
		
		while (frequentSequencesSetsMap.get(k).size() != 0){
			for (Sequence gram : frequentSequencesSetsMap.get(k)){
				if (!isSubsequenceOfMax(gram)){
					if (storage.getSequenceInfo(gram).isFrequent()){
						Sequence max = storage.expand(gram);
						getMaximalFrequentSequences().add(max);
						if (max.equals(gram))
							frequentSequencesSetsMap.get(k).remove(gram);
					}
					else
						frequentSequencesSetsMap.get(k).remove(gram);
				}
			}
			prune(frequentSequencesSetsMap.get(k));
			frequentSequencesSetsMap.put(k+1, join(frequentSequencesSetsMap.get(k)));
			++k;
		}
		return getMaximalFrequentSequences();
	}

	private boolean isSubsequenceOfMax(Sequence gram){
		for (Sequence maximalSequence : maximalFrequentSequences){
			if (SequenceUtils.checkIfSubsequence(maximalSequence, gram)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param sequences
	 */
	private void prune(Set<Sequence> sequences){
		Set<Sequence> marked = new HashSet<Sequence>();
		for (Sequence g : sequences){
			Set<Sequence> LMax = new HashSet<Sequence>();
			Set<Sequence> RMax = new HashSet<Sequence>();
			Set<Sequence> LStr = new HashSet<Sequence>();
			Set<Sequence> RStr = new HashSet<Sequence>();
			Set<Sequence> LStr_P = new HashSet<Sequence>();
			Set<Sequence> RStr_P = new HashSet<Sequence>();
			
			for (Sequence max : maximalFrequentSequences){
				Sequence gTmp = new Sequence(g);
				gTmp.removeLastItem();
				if (SequenceUtils.checkIfSubsequence(max, gTmp))
					LMax.add(max);
			}
			for (Sequence max : maximalFrequentSequences){
				Sequence gTmp = new Sequence(g);
				gTmp.removeFirstItem();
				if (SequenceUtils.checkIfSubsequence(max, gTmp))
					RMax.add(max);
			}
			for (Sequence max : LMax){
				Sequence maxTmp = new Sequence(max);
				Sequence gTmp = new Sequence(g);
				
				gTmp.removeLastItem();
				
				SubsequenceWithMaxGapChecker checker = new SubsequenceWithMaxGapChecker(maxTmp, gTmp, 0);
				int i = checker.startingIndex();
				
				if(i>0){
					for(int j = i; j<maxTmp.getSequenceItems().size(); ++j){
						maxTmp.removeLastItem();
					}
					LStr_P.add(maxTmp);
				}
				
			}
			
			for (Sequence max : RMax){
				Sequence maxTmp = new Sequence(max);
				Sequence gTmp = new Sequence(g);
				
				gTmp.removeFirstItem();;
				
				SubsequenceWithMaxGapChecker checker = new SubsequenceWithMaxGapChecker(maxTmp, gTmp, 0);
				int i = checker.startingIndex();
				
				if(i>0){
					for(int j = i + gTmp.getSequenceItems().size(); j>0; --j){
						maxTmp.removeFirstItem();
					}
					RStr_P.add(maxTmp);
				}
			}
			
//			krok 8, 9
			
			
			for(Sequence s1 : LStr){
				for(Sequence s2 : RStr){
					Sequence sNew = new Sequence(g);
					addAtBegin(s1, sNew);
					addAtEnd(sNew, s2);
					if (!isSubsequenceOfMax(sNew)){
//						for(Sequence subsequence : sNew){
//							if(!isSubsequenceOfMax(subsequence))
//								
//								
//						}
					}
				}
			}
		}
		
		sequences.removeAll(marked);
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
	
	private Set<Sequence> join(Set<Sequence> sequences){
		return sequences;
	}

	public Set<Sequence> getMaximalFrequentSequences() {
		return maximalFrequentSequences;
	}

	public void setMaximalFrequentSequences(Set<Sequence> maximalFrequentSequences) {
		this.maximalFrequentSequences = maximalFrequentSequences;
	}
	
}
