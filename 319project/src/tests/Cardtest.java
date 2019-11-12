package tests;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.util.Stack;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import main.Card;
import main.CardImage;

class Cardtest{

	@BeforeEach
	public void setupclass() {
		final JFXPanel fxPanel = new JFXPanel();
	}
	@Test
	public void decktest() throws FileNotFoundException {
		Stack<Card> deck = new Stack<Card>();
		deck = Card.initdeck(52);
		Card[][] field = new Card[13][4];
		for (Card i: deck) {
			if (i.getColornum() > 4) {field[12][i.getCardtype()] = i;}
			else field[i.getCardtype()][i.getColornum()] = i;
		}
		for (Card[] i: field) {
			for (Card j: i) {
				try {
					j.getImage();
				} catch (NullPointerException e) {
					fail("Failed to generate every card!");
				}
			}
		}
	}
	@Test
	public void decktest() {
		Stack<Card> deck = new Stack<Card>();
		deck = Card.initdeck(52);
		
	}
}

