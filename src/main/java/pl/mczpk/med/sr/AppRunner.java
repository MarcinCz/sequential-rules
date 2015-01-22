package pl.mczpk.med.sr;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.mczpk.med.sr.algorithm.FrequentWordSequenceFinder;
import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.config.AlgorithmConfig;
import pl.mczpk.med.sr.config.AlgorithmConfigReader;
import pl.mczpk.med.sr.storage.ConfigBasedSequenceStorage;
import pl.mczpk.med.sr.storage.SequenceStorage;

public class AppRunner {


	public void run(String configFileName) {
		AlgorithmConfig config = AlgorithmConfigReader.readFromFile(configFileName);
		SequenceStorage storage = new ConfigBasedSequenceStorage(config);
		FrequentWordSequenceFinder finder = new FrequentWordSequenceFinder();
		
		List<Sequence> sequences = finder.findMaximalFrequentSequence(storage);
		System.out.println(String.format("Found %s maximal frequent sequences.", sequences.size()));
		String fileName = saveSequencesToFile(configFileName, new ArrayList<Sequence>(sequences));
		System.out.println(String.format("Sequences saved in file '%s'.", fileName));
	}
	
	/**
	 * Saves sequences to file and returns filename.
	 * @param sequences
	 * @return
	 */
	private String saveSequencesToFile(String configName, List<Sequence> sequences) {
		String fileName = "configName-" + new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()) + ".seq";
		
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
