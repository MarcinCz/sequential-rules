package pl.mczpk.med.sr.taxonomy;

import java.util.List;
import java.util.Properties;

import org.junit.Test;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.ValueAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class CoreNLPTest {

	@Test
	public void shouldTagPOS() {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		String text = "I'm an example sentence, more words.";
		Annotation document = new Annotation(text);
		pipeline.annotate(document);

		List<CoreLabel> tokens = document.get(TokensAnnotation.class);

		for (CoreLabel token : tokens) {
			String value = token.get(ValueAnnotation.class);
			String pos = token.get(PartOfSpeechAnnotation.class); //http://stackoverflow.com/questions/1833252/java-stanford-nlp-part-of-speech-labels
			System.out.println("POS for value " + value + " is " + pos);
		}
	}

}
