package imsmartshutup;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Main {

	public static ArrayList<Position> pos;
	public static ArrayList<String> usedStrings;
	public static File dirOut;
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		System.out.println("Doge File: ");
		Scanner in = new Scanner(System.in);
		String file = in.nextLine();
		System.out.println("Output File: ");
		File output = new File(in.nextLine());
		System.out.println("Can I download a font? Y/N");
		String response = in.nextLine();
		if(!response.equalsIgnoreCase("y"))  {
			System.out.println("Have a good day!");
			System.exit(0);
			return;
		}
		File f = new File(file);
		if(!f.exists())  {
			System.out.println("File not found!");
			System.exit(0);
			return;
		}
		
		dirOut = new File(output.getParent() + "\\");
		
		BufferedImage img = ImageIO.read(f);
		pos = new ArrayList<>();
		usedStrings = new ArrayList<>();
		
		Graphics g = img.getGraphics();
		g.setFont(getFont());
		Random r = new Random();
		String[] dogeQuotes = IOUtils.toString(new URL("http://cilantro33.000webhostapp.com/fun_stuff/dogeQuotes.txt")).split("\n");
		for(int i = 0; i < (r.nextInt(5) + 5); i++)  {
			int x = r.nextInt(img.getWidth() - 1) + 1;
			int y = r.nextInt(img.getHeight() - g.getFontMetrics().getHeight()) + g.getFontMetrics().getHeight();
			String s = dogeQuotes[r.nextInt(dogeQuotes.length - 1) + 1];
			if((g.getFontMetrics().stringWidth(s) + x) > img.getWidth()) x -= g.getFontMetrics().stringWidth(s);
			g.setColor(new Color(0, 0, 0));
			g.drawString(s, x, y + 1);
			g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
			g.drawString(s, x, y);
			Position p = new Position(x, y, s);
			while(isOverlapping(g, p) && usedStrings.contains(s)) {
				x = r.nextInt(img.getWidth() - 1) + 1;
				y = r.nextInt(img.getHeight() - 1) + 1;
				s = dogeQuotes[r.nextInt(dogeQuotes.length - 1) + 1];
				if((g.getFontMetrics().stringWidth(s) + x) > img.getWidth()) x -= g.getFontMetrics().stringWidth(s);
				g.setColor(new Color(0, 0, 0));
				g.drawString(s, x - 1, y + 1);
				g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
				g.drawString(s, x, y);
				p = new Position(x, y, s);
			}
			pos.add(p);
			usedStrings.add(s);
		}
		g.dispose();
		
		ImageIO.write(img, "png", output);
		System.out.println("Done! Enjoy!");
		File ttf = new File(dirOut, "LDFComicSans.ttf");
		ttf.delete();
	}
	
	public static Font getFont() throws FontFormatException, IOException, URISyntaxException  {
		File out = new File(dirOut, "LDFComicSans.ttf");
		FileUtils.copyURLToFile(new URL("http://cilantro33.000webhostapp.com/fun_stuff/LDFComicSans.ttf"), out);
		Font f = Font.createFont(Font.TRUETYPE_FONT, new File(dirOut.getPath(), "LDFComicSans.ttf"));
		return f.deriveFont(30f);
	}
	
	public static boolean isOverlapping(Graphics g, Position sPos)  {
		boolean b = false;
		
		for(Position p : pos)  {
			int x1 = p.getX();
			int y1 = p.getY();
			int x2 = p.getX() + g.getFontMetrics().stringWidth(p.getS());
			int y2 = p.getY() + g.getFontMetrics().getHeight();
			b = x1 >= sPos.getX() && y1 >= sPos.getY() && x2 <= sPos.getX() + g.getFontMetrics().stringWidth(sPos.getS()) && y2 <= sPos.getY() + g.getFontMetrics().getHeight();
			if(b)  break;
		}
		
		return b;
	}
	
}
