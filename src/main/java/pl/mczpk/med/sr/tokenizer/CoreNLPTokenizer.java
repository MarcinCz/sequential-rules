package pl.mczpk.med.sr.tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.ValueAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class CoreNLPTokenizer implements Tokenizer {
	
	public List<Token> tokenize(String text, boolean attachPOS) {
		Properties props = new Properties();
		if(attachPOS) {
			props.put("annotators", "tokenize, ssplit, pos");
		} else {
			props.put("annotators", "tokenize");
		}
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(text);
		pipeline.annotate(document);

		List<CoreLabel> labels = document.get(TokensAnnotation.class);

		List<Token> tokens = new ArrayList<CoreNLPTokenizer.Token>(); 
		for (CoreLabel label : labels) {
			tokens.add(getToken(label, attachPOS));
		}
		
		return tokens;
	}
	
	private Token getToken(CoreLabel label, boolean attachPOS) {
		final String value = label.get(ValueAnnotation.class);
		String pos = null;
		boolean isPOSAttached = false;
		if(attachPOS) {
			pos = label.get(PartOfSpeechAnnotation.class); //http://stackoverflow.com/questions/1833252/java-stanford-nlp-part-of-speech-labels
			if(!pos.equals(value)) {
				isPOSAttached = true;
			}
		}
		
		final boolean finalPOSAttached = isPOSAttached;
		final String finalPOS = pos;
		
		return new Token() {
			
			@Override
			public boolean isPOSAttached() {
				return finalPOSAttached;
			}
			
			@Override
			public String getValue() {
				return value;
			}
			
			@Override
			public String getPOS() {
				return finalPOS;
			}
		};
	}
} 
