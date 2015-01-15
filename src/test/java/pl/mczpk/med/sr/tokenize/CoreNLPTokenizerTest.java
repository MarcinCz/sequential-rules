package pl.mczpk.med.sr.tokenize;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import pl.mczpk.med.sr.tokenizer.CoreNLPTokenizer;
import pl.mczpk.med.sr.tokenizer.Tokenizer.Token;

public class CoreNLPTokenizerTest {

	private CoreNLPTokenizer tokenizer = new CoreNLPTokenizer();
	private String testSentence = "I'm an example sentence, more words.";
	
	@Test
	public void shouldTokenizeWithousPOS() {
		List<Token> tokens = tokenizer.tokenize(testSentence, false);
		assertTrue(tokens.size() > 0);
		System.out.println("Tokens without POS:");
		printTokens(tokens);
	}
	
	@Test
	public void shouldTokenizeWithPOS() {
		List<Token> tokens = tokenizer.tokenize(testSentence, true);
		assertTrue(tokens.size() > 0);
		System.out.println("Tokens with POS:");
		printTokens(tokens);
	}
	
	private void printTokens(List<Token> tokens) {
		for(Token token: tokens) {
			System.out.println(String.format("Token: val='%s', POS='%s', isPOSAttached=%s", token.getValue(), token.getPOS(), token.isPOSAttached()));
		}
	}
	
	@Test
	public void shouldReturnSameTokenValues() {
		List<Token> tokensWithoutPOS = tokenizer.tokenize(testSentence, false);
		List<Token> tokensWithPOS = tokenizer.tokenize(testSentence, false);
		
		assertEquals(tokensWithoutPOS.size(), tokensWithPOS.size());
		for (int i = 0; i < tokensWithoutPOS.size(); i++) {
			assertEquals(tokensWithoutPOS.get(i).getValue(), tokensWithPOS.get(i).getValue());
		}
	}

}
