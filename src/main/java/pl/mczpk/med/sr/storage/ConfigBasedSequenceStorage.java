package pl.mczpk.med.sr.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Logger;

import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.algorithm.SequenceItem;
import pl.mczpk.med.sr.config.AlgorithmConfig;
import pl.mczpk.med.sr.tokenizer.CoreNLPTokenizer;
import pl.mczpk.med.sr.tokenizer.Tokenizer;
import pl.mczpk.med.sr.tokenizer.Tokenizer.Token;

public class ConfigBasedSequenceStorage extends AbstractSequenceStorage {

	private final Logger logger = Logger.getLogger(this.getClass());

	private final AlgorithmConfig config;

	private final Tokenizer tokenizer = new CoreNLPTokenizer();

	public ConfigBasedSequenceStorage(AlgorithmConfig config) {
		this.config = config;
		buildSequenceStorage();
	}

	private void buildSequenceStorage() {
		logger.debug("Started sequence storing");
		for (File textFile : config.getTextFiles()) {
			try {
				String content = new String(Files.readAllBytes(Paths.get(textFile.getPath())));
				List<Token> tokens = tokenizer.tokenize(content, config.isTaxonomyEnabled());
				addSequencesFromTokens(tokens);
				logger.trace(String.format("Sequence from file '%s' stored", textFile));
			} catch (FileNotFoundException e) {
				logger.warn(String.format("File not found '%s'. File was ignored.", textFile));
			} catch (IOException e) {
				logger.warn(String.format("Error while reading file '%s'. File was ignored.", textFile));
			}
		}
		logger.debug("Sequences from all files stored");
	}

	private void addSequencesFromTokens(List<Token> tokens) {
		Sequence sequence = new Sequence();
		for (Token token : tokens) {
			SequenceItem sequenceItem = new SequenceItem(token.getValue());
			if (token.isPOSAttached()) {
				sequenceItem.addLastElement(token.getPOS());
			}
		}
		addSequenceToStorage(sequence);
	}

	@Override
	protected int getMaxGapBetweenSequenceItems() {
		return config.getMaxGapBetweenSequenceItems();
	}

	@Override
	protected int getMinSequenceSupport() {
		return config.getMinRuleSupport();
	}
}
