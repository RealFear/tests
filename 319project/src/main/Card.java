package main;
import java.io.FileNotFoundException;
import java.util.Stack;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class Card {
	private Button button;
	private int cardtype;
	private int colornum;
	private WritableImage[] color = new WritableImage[12];
	public Card(Button button, int cardtype, int color) {
		super();
		this.button = button;
		this.cardtype = cardtype;
		this.colornum = color;
	}
	public Card(int cardtype, int colornum) throws FileNotFoundException {
		super();
		this.cardtype = cardtype;
		this.colornum = colornum;
		CardImage cards = new CardImage();
		switch (colornum) {
		case 0:
			color = cards.getGreens();
			break;
		case 1:
			color = cards.getReds();
			break;
		case 2:
			color = cards.getPurples();
			break;
		case 3:
			color = cards.getOranges();
			break;
		default:
			color = cards.getWilds();
			break;
		}
		ImageView imageview = new ImageView(color[cardtype]);
		button = new Button("",imageview);
		button.setPadding(Insets.EMPTY);
	}
	public Button getButton() {
		return button;
	}
	public void setButton(Button button) {
		button.setGraphic(this.button.getGraphic());
		button.setPadding(this.button.getPadding());
		this.button = button;
	}
	
	public static Stack<Card> initdeck( int size) throws FileNotFoundException {
		Stack<Card> deck = new Stack<Card>();
		if (size > 52) size = 52;
		if (size > 12) {
			if (size > 24) {
				if (size > 36) {
					if (size > 48) {
						deck.push(new Card(3,5));
						size--;
					}
					deck.push(new Card(2,5));
					size--;
				}
				deck.push(new Card(1,5));
				size--;
			}
			deck.push(new Card(0,5));
			size--;
		}
		int j = -1;
		for (int i = 0; i < size ; i++) {
			int x = i % 4;
			if (x == 0) j++;
			deck.push(new Card(j,x));
		}
		return deck;
	}
	public WritableImage getImage() {
		return color[cardtype];
	}
	public int getCardtype() {
		return cardtype;
	}
	public int getColornum() {
		return colornum;
	}

}
