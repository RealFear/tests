package main;

import java.awt.TextField;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class Board extends Application {

	static int[] gamedeck;
	static Stack<Card> drawdeck;
	static Stack<Card> discards = new Stack<Card>();
	static ArrayList<ArrayList<Card>> hands;
	static HBox handbox;
	static VBox vbox;
	static int size = 52;
	static int curplayer = 0;
	static GridPane layout = new GridPane();
	static Button disbut = new Button();

    static FileInputStream input;
	
	public static void main(String[] args) throws IOException {
		drawdeck = Card.initdeck(size);
		shuffle();
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("HBox Experiment 1");
		pregame(primaryStage);
	}
	
	public static void shuffle() {
		Random rand = new Random();
		int length = drawdeck.size();
		Card[] result = new Card[length];
		for (int i = 0;i < length; i++) {
			result[i] = drawdeck.pop();
		}
		for (int i = 0;i < length; i++) {
			int j = rand.nextInt(result.length);
			Card temp = result[i];
			result[i] = result[j];
			result[j] = temp;
		}
		drawdeck.clear();
		for (int i = 0;i < length; i++) {
			drawdeck.push(result[i]);
		}
		
	}
	public static boolean deckcheck(int[] deck) {
		boolean check = false;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (deck [j] == deck[i] && i != j) check = true;
			}
		}
		return check;
	}
	public static void draw(int hand) throws FileNotFoundException {
		if (drawdeck.isEmpty()) {
			while(!discards.isEmpty()) {
				drawdeck.push(discards.pop());		
			}
			updatediscard();
			shuffle();
		}
		final Card current = drawdeck.pop();
		final Button add = current.getButton();
		final int buttonhand = hand;
		add.setOnAction(arg0 -> {
			discards.push(current);
			hands.get(buttonhand).remove(hands.get(buttonhand).indexOf(current));
			handbox.getChildren().clear();
			for (Card i: hands.get(buttonhand)) {
				handbox.getChildren().add(i.getButton());
			}
			if (layout.getChildren().contains(discards.peek().getButton())) {
			layout.getChildren().remove(layout.getChildren().indexOf(discards.peek().getButton()));
			}
			discards.peek().setButton(new Button(discards.peek().getButton().getText()));
			try {
				updatediscard();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		hands.get(hand).add(current);
		handbox.getChildren().clear();
		for (Card i: hands.get(hand)) {
			handbox.getChildren().add(i.getButton());
		}
	}
	public static void draw(int count, int hand) throws FileNotFoundException {
		for (int i = 0; i < 10; i++) {
			draw(hand);
		}

	}
	public static void updatediscard() throws FileNotFoundException {
		layout.getChildren().remove(disbut);
		if (!discards.isEmpty()) {
		layout.add(discards.peek().getButton(), 2, 1);
		disbut = discards.peek().getButton();
		} else {
			input = new FileInputStream("src/Discard.png");
	        Image image = new Image(input);
	        ImageView imageView = new ImageView(image);
	        disbut = new Button("",imageView);
			disbut.setPadding(Insets.EMPTY);
			layout.add(disbut, 1, 1);
		}
	}
	public static void pregame(Stage stage) {
		layout = new GridPane();
		javafx.scene.control.TextField user = new javafx.scene.control.TextField("username");
		ChoiceBox<Integer> choiceBox = new ChoiceBox<Integer>();
		choiceBox.getItems().add(2);
		choiceBox.getItems().add(3);
		choiceBox.getItems().add(4);
		Label label = new Label("Number of players:");
		layout.add(label, 0, 0);
		layout.add(choiceBox, 1, 0);
		Button create = new Button("Create Game");
		Button join = new Button("Join Game");
		layout.add(create, 0, 1);
		layout.add(user, 1, 1);
		create.setOnAction(arg0 -> {
			try {
				gamestart(stage, choiceBox.getValue(),user.getText());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Scene scene = new Scene(layout, 400, 400);
		stage.setScene(scene);
		stage.show();
	}

	public static void gamestart(Stage stage, int players, String user) throws FileNotFoundException {
		layout = new GridPane();
		// ChatClient client = new ChatClient(user);
		hands = new ArrayList<ArrayList<Card>>();
		for (int i = 0; i < players; i++) {
			switch (i + 1) {
			case 4:
				ArrayList<Card> hand4 = new ArrayList<Card>();
				hands.add(hand4);
				break;
			case 3:
				ArrayList<Card> hand3 = new ArrayList<Card>();
				hands.add(hand3);
				break;
			case 2:
				ArrayList<Card> hand2 = new ArrayList<Card>();
				hands.add(hand2);
				break;
			case 1:
				ArrayList<Card> hand1 = new ArrayList<Card>();
				hands.add(hand1);
				handbox = new HBox();
				layout.add(handbox, 0, 2,10,1);
				break;
			}
			draw(10,i);
		}
		input = new FileInputStream("src/cardback.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
		Button draw = new Button("",imageView);
		draw.setPadding(Insets.EMPTY);
		layout.add(draw, 0, 1);
		draw.setOnAction(value -> {
			try {
				draw(0);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		handbox.getChildren().clear();
		for (Card i: hands.get(0)) {
			handbox.getChildren().add(i.getButton());
		}
		updatediscard();
		Scene scene = new Scene(layout, 400, 400);
		stage.setScene(scene);
		stage.show();
	}
}
