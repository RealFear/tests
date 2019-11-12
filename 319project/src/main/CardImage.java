package main;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class CardImage {
	WritableImage[] greens = new WritableImage[12];
	WritableImage[] reds = new WritableImage[12];
	WritableImage[] purples = new WritableImage[12];
	WritableImage[] oranges = new WritableImage[12];
	WritableImage[] wilds = new WritableImage[4];
	int cardwidth = 73;
	int cardheight = 97;
	
	public static void main(String[] args) throws IOException {
		CardImage cards = new CardImage();
	}
	public CardImage() throws FileNotFoundException {
	FileInputStream input = new FileInputStream("src/Cards.png");
	Image image = new Image(input);
	PixelReader reader = image.getPixelReader();
	int x = 6;
	for (int i = 0; i < greens.length; i++) {
		greens[i] = new WritableImage(reader, x, 5, cardwidth, cardheight);
		reds[i] = new WritableImage(reader, x, 112, cardwidth, cardheight);
		purples[i] = new WritableImage(reader, x, 220, cardwidth, cardheight);
		oranges[i] = new WritableImage(reader, x, 328, cardwidth, cardheight);
		x += 77;
	}
	wilds[0] = new WritableImage(reader, x, 5, cardwidth, cardheight);
	wilds[1] = new WritableImage(reader, x, 112, cardwidth, cardheight);
	wilds[2] = new WritableImage(reader, x, 220, cardwidth, cardheight);
	wilds[3] = new WritableImage(reader, x, 328, cardwidth, cardheight);
	}
	public WritableImage[] getGreens() {
		return greens;
	}
	public WritableImage[] getReds() {
		return reds;
	}
	public WritableImage[] getPurples() {
		return purples;
	}
	public WritableImage[] getOranges() {
		return oranges;
	}
	public WritableImage[] getWilds() {
		return wilds;
	}

	
}
