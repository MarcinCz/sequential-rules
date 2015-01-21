package pl.mczpk.med.sr;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import pl.mczpk.med.sr.algorithm.MaximalFrequentSequenceFinder;
import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.algorithm.SequenceItem;
import pl.mczpk.med.sr.config.AlgorithmConfig;
import pl.mczpk.med.sr.config.AlgorithmConfigReader;
import pl.mczpk.med.sr.storage.ConfigBasedSequenceStorage;
import pl.mczpk.med.sr.storage.SequenceStorage;

public class AppRunner {

	private MaximalFrequentSequenceFinder finder;

	public void run(String configFileName) {
		AlgorithmConfig config = AlgorithmConfigReader.readFromFile(configFileName);
		SequenceStorage storage = new ConfigBasedSequenceStorage(config);
        finder = new MaximalFrequentSequenceFinder() {
			
			@Override
			public List<Sequence> findMaximalFrequentSequence(SequenceStorage storage) {
				return Arrays.asList(new Sequence(new SequenceItem("a"), new SequenceItem("b")), new Sequence(new SequenceItem("c"), new SequenceItem("d")));
			}
		};
		
		Set<Sequence> sequences = storage.getFrequentPairSequences();
		System.out.println(String.format("Found %s maximal frequent sequences.", sequences.size()));
		String fileName = saveSequencesToFile(new ArrayList<Sequence>(sequences));
		System.out.println(String.format("Sequences saved in file '%s'.", fileName));
	}
	
	/**
	 * Saves sequences to file and returns filename.
	 * @param sequences
	 * @return
	 */
	private String saveSequencesToFile(List<Sequence> sequences) {
		String fileName = "sequences-" + new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-SSS").format(new Date());
		
		try {
			Writer writer = new FileWriter(fileName);
			
			for (int i = 0; i < sequences.size(); i++) {
				writer.write(i+1 + ": " + sequences.get(i) + "\n");
			}
			writer.close();
		} catch (IOException e) {
			throw new IllegalStateException("Error while saving sequences to file " + fileName, e);
		}
		return fileName;
	}
}
