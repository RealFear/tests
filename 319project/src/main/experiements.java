package main;
import java.util.Random;

import javafx.application.Application;
import javafx.stage.Stage;

public class experiements extends Application {

	
	public static void main(String[] args) {
		int[] deck = new int[52];
		initdeck(deck);
		shuffle(deck);
		deckcheck(deck);
		Board.launch(args);
	}
	
	public static void shuffle(int[] deck) {
		Random rand = new Random();
		int[] result = new int[52];
		for (int i = 0;i < deck.length; i++) {
			int j = rand.nextInt(deck.length);
			int temp = deck[i];
			deck[i] = deck[j];
			deck[j] = temp;
		}
	}

	public static void initdeck(int[] deck) {
		for (int i = 0; i < deck.length; i++) {
			deck[i] = i;
		}
	}
	public static boolean deckcheck(int[] deck) {
		boolean check = false;
		for (int i = 0; i < deck.length; i++) {
			for (int j = 0; j < deck.length; j++) {
				if (deck [j] == deck[i] && i != j) check = true;
			}
		}
		return check;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
		
	

}
