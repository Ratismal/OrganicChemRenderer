package start;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrganicChem {
	private static JTextField textField;
	static BufferedImage img = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
	static JFrame frame;
	static ImagePanel panel_1;

	public static void main(String[] args) {
		Graphics2D g2d = img.createGraphics();
		g2d.setPaint(Color.WHITE);
		g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
		OrganicChem organic = new OrganicChem();
		organic.init();
	}

	public void init() {
		frame = new JFrame();
		frame.getContentPane().setLayout(new MigLayout("", "[55px][86px]", "[14px][20px][6px][25px]"));
		//JFrame imgFrame = new JFrame();

		JLabel lblRenderACompound = new JLabel("Render A Compound");
		frame.getContentPane().add(lblRenderACompound, "cell 0 0 2 1,growx,aligny top");

		JLabel lbl2 = new JLabel("Please separate each part of the compound with '@'");
		frame.getContentPane().add(lbl2, "cell 0 1 2 1,growx,aligny top");


		textField = new JTextField();
		frame.getContentPane().add(textField, "cell 1 2,alignx left,aligny top");
		textField.setColumns(10);

		JLabel lblCompound = new JLabel("Compound:");
		frame.getContentPane().add(lblCompound, "cell 0 2,alignx left,growy");

		panel_1 = new ImagePanel(img);
		frame.getContentPane().add(panel_1, "cell 0 4 2 1,grow");

		JButton btnRender = new JButton("Render");
		frame.getContentPane().add(btnRender, "cell 0 2 2 1,grow");
		btnRender.setActionCommand("render");
		btnRender.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("Changing\n");
				changeImage(1, 1, textField.getText());
				frame.remove(panel_1);
				frame.getContentPane().add(panel_1, "cell 0 3 2 1,grow");
				frame.revalidate();
				frame.repaint();

			}

		});
		//imgFrame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		//imgFrame.pack();
		//imgFrame.setVisible(true);
		frame.pack();
		frame.setVisible(true);
	}

	public static void changeImage(int x, int y, String string) {

		HashMap<String, Object> hash = InterpretCompound(string);
		int length = (int) hash.get("chainlength");
		x = (x*32) - 1;
		y = (y*32) - 1;


		Graphics2D g2d = img.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
		g2d.setPaint(Color.BLACK);
		Font font = Font.decode(Font.SANS_SERIF);
		int style = Font.BOLD;
		font = font.deriveFont(style, 24);
		g2d.setFont(font);
		g2d.drawString(string, 24, 24);
		//g2d.drawString(length, 80, 80);
		int type = (int) hash.get("type");
		int x1 = 32;
		int y1 = 64;
		if ((type == 0)) {
			for(int i = 1; i <= length; i++) {
				g2d.drawString("C", x1 * (i), y1);
				g2d.drawLine((x1 * i) - 13, y1 - 9, (x1 * i) - 2, y1 - 9);
				g2d.drawLine((x1 * i) - 13, y1 - 8, (x1 * i) - 2, y1 - 8);
				g2d.drawLine((x1 * i) + 9, y1 - 20, (x1 * i) + 9, y1 - 31);
				g2d.drawLine((x1 * i) + 8, y1 - 20, (x1 * i) + 8, y1 - 31);
				g2d.drawLine((x1 * i) + 9, y1 + 2, (x1 * i) + 9, y1 + 13);
				g2d.drawLine((x1 * i) + 8, y1 + 2, (x1 * i) + 8, y1 + 13);
			}
			g2d.drawLine((x1 * length) + 17, y1 - 9, (x1 * length) + 30, y1 - 9);
			g2d.drawLine((x1 * length) + 17, y1 - 8, (x1 * length) + 30, y1 - 8);
		}
		else if (type == 1) {
			List<Integer> list = new ArrayList<Integer>();
			if ((1 < length) && (length < 4)){
				list.add(1);
			}
			else {
				list = (List<Integer>) hash.get("ene");
			}
			for(int i = 1; i <= length; i++) {
				System.out.print(i + "\n");
				if ((list.contains(i)) || (list.contains(i+1))) {
					for (int ii = 0; ii < list.size(); ii++) {
						System.out.print(list.get(ii) + "\n");
						if (list.contains(i) && (list.contains(i+1))) {
							System.out.print("Four\n");
							g2d.drawString("C", x1 * (i), y1);
							g2d.setColor(Color.WHITE);
							g2d.drawLine((x1 * i) - 13, y1 - 9, (x1 * i) - 2, y1 - 9);
							g2d.drawLine((x1 * i) - 13, y1 - 8, (x1 * i) - 2, y1 - 8);
							g2d.drawLine((x1 * i) - 13, y1 - 11, (x1 * i) - 2, y1 - 11);
							g2d.drawLine((x1 * i) - 13, y1 - 10, (x1 * i) - 2, y1 - 10);
							g2d.drawLine((x1 * i) - 13, y1 - 7, (x1 * i) - 2, y1 - 7);
							g2d.drawLine((x1 * i) - 13, y1 - 6, (x1 * i) - 2, y1 - 6);
							g2d.setColor(Color.BLACK);
							g2d.drawLine((x1 * i) - 13, y1 - 11, (x1 * i) - 2, y1 - 11);
							g2d.drawLine((x1 * i) - 13, y1 - 10, (x1 * i) - 2, y1 - 10);
							g2d.drawLine((x1 * i) - 13, y1 - 7, (x1 * i) - 2, y1 - 7);
							g2d.drawLine((x1 * i) - 13, y1 - 6, (x1 * i) - 2, y1 - 6);
							//g2d.drawLine((x1 * i) + 9, y1 - 20, (x1 * i) + 9, y1 - 31);
							//g2d.drawLine((x1 * i) + 8, y1 - 20, (x1 * i) + 8, y1 - 31);
							//g2d.drawLine((x1 * i) + 9, y1 + 2, (x1 * i) + 9, y1 + 13);
							//g2d.drawLine((x1 * i) + 8, y1 + 2, (x1 * i) + 8, y1 + 13);
						}
						else if (i == (list.get(ii) + 1)) {
							System.out.print("One\n");
							g2d.drawString("C", x1 * (i), y1);
							g2d.setColor(Color.WHITE);
							g2d.drawLine((x1 * i) - 13, y1 - 9, (x1 * i) - 2, y1 - 9);
							g2d.drawLine((x1 * i) - 13, y1 - 8, (x1 * i) - 2, y1 - 8);
							g2d.drawLine((x1 * i) - 13, y1 - 11, (x1 * i) - 2, y1 - 11);
							g2d.drawLine((x1 * i) - 13, y1 - 10, (x1 * i) - 2, y1 - 10);
							g2d.drawLine((x1 * i) - 13, y1 - 7, (x1 * i) - 2, y1 - 7);
							g2d.drawLine((x1 * i) - 13, y1 - 6, (x1 * i) - 2, y1 - 6);
							g2d.setColor(Color.BLACK);
							g2d.drawLine((x1 * i) - 13, y1 - 11, (x1 * i) - 2, y1 - 11);
							g2d.drawLine((x1 * i) - 13, y1 - 10, (x1 * i) - 2, y1 - 10);
							g2d.drawLine((x1 * i) - 13, y1 - 7, (x1 * i) - 2, y1 - 7);
							g2d.drawLine((x1 * i) - 13, y1 - 6, (x1 * i) - 2, y1 - 6);
							//g2d.drawLine((x1 * i) + 9, y1 - 20, (x1 * i) + 9, y1 - 31);
							//g2d.drawLine((x1 * i) + 8, y1 - 20, (x1 * i) + 8, y1 - 31);
							g2d.drawLine((x1 * i) + 9, y1 + 2, (x1 * i) + 9, y1 + 13);
							g2d.drawLine((x1 * i) + 8, y1 + 2, (x1 * i) + 8, y1 + 13);
						}
						else if (i == (list.get(ii))) {
							System.out.print("Two\n");
							g2d.drawString("C", x1 * (i), y1);
							//g2d.drawLine((x1 * i) - 13, y1 - 11, (x1 * i) - 2, y1 - 11);
							//g2d.drawLine((x1 * i) - 13, y1 - 10, (x1 * i) - 2, y1 - 10);
							g2d.drawLine((x1 * i) - 13, y1 - 9, (x1 * i) - 2, y1 - 9);
							g2d.drawLine((x1 * i) - 13, y1 - 8, (x1 * i) - 2, y1 - 8);
							//g2d.drawLine((x1 * i) + 9, y1 - 20, (x1 * i) + 9, y1 - 31);
							//g2d.drawLine((x1 * i) + 8, y1 - 20, (x1 * i) + 8, y1 - 31);
							g2d.drawLine((x1 * i) + 9, y1 + 2, (x1 * i) + 9, y1 + 13);
							g2d.drawLine((x1 * i) + 8, y1 + 2, (x1 * i) + 8, y1 + 13);
						}


					}
				}
				else {
					System.out.print("Three\n");
					g2d.drawString("C", x1 * (i), y1);
					g2d.drawLine((x1 * i) - 13, y1 - 9, (x1 * i) - 2, y1 - 9);
					g2d.drawLine((x1 * i) - 13, y1 - 8, (x1 * i) - 2, y1 - 8);
					g2d.drawLine((x1 * i) + 9, y1 - 20, (x1 * i) + 9, y1 - 31);
					g2d.drawLine((x1 * i) + 8, y1 - 20, (x1 * i) + 8, y1 - 31);
					g2d.drawLine((x1 * i) + 9, y1 + 2, (x1 * i) + 9, y1 + 13);
					g2d.drawLine((x1 * i) + 8, y1 + 2, (x1 * i) + 8, y1 + 13);
				}
			}
			g2d.drawLine((x1 * length) + 17, y1 - 9, (x1 * length) + 30, y1 - 9);
			g2d.drawLine((x1 * length) + 17, y1 - 8, (x1 * length) + 30, y1 - 8);
		}
		//g2d.drawString("C", x1 * (length), y1);
		File f = new File("img.png");
		try {
			ImageIO.write(img, "png", f);
		} catch (IOException e) {
			System.out.print("AN ERROR HAS OCCURED\n");
			e.printStackTrace();
		}
	}

	public static HashMap<String, Object> InterpretCompound(String comp) {
		comp = comp.toLowerCase();
		comp = comp.replaceAll("-di", "-");
		comp = comp.replaceAll("-tri", "-");
		comp = comp.replaceAll("-tetra", "-");
		comp = comp.replaceAll("-penta", "-");
		comp = comp.replaceAll("-hexa", "-");
		comp = comp.replaceAll("-hepta", "-");
		comp = comp.replaceAll("-octa", "-");
		comp = comp.replaceAll("-nona", "-");
		comp = comp.replaceAll("-deca", "-");

		boolean cont;
		String prefix;
		List<String> list = new ArrayList<String>();
		for (int p = 0; p < 20; p++) {
			if ((comp.startsWith("0")) || (comp.startsWith("1")) || (comp.startsWith("2")) || (comp.startsWith("3")) ||
					(comp.startsWith("4")) || (comp.startsWith("5")) || (comp.startsWith("6")) || 
					(comp.startsWith("7")) || (comp.startsWith("8")) || (comp.startsWith("9"))) {
				cont = true;
				int pos = 0;
				for(int i = 0; cont == true; i++) {
					if ((comp.charAt(i) == '-')) {
						pos = i;
						cont = false;
					}
				}
				prefix = comp.substring(0, pos);
				comp = comp.substring(pos + 1, comp.length());
				System.out.print(prefix + "\n");
				System.out.print(comp + "\n");
				if (comp.startsWith("methyl")) {
					prefix = prefix.concat("-methyl");
					comp = comp.replaceFirst("methyl", " ");
					if (comp.startsWith(" -")) {
						comp = comp.replaceFirst(" -", " ");
					}
					list.add(prefix);
				}
				else if (comp.startsWith("ethyl")) {
					prefix = prefix.concat("-ethyl");
					comp = comp.replaceFirst("ethyl", " ");
					if (comp.startsWith(" -")) {
						comp = comp.replaceFirst(" -", " ");
					}
					list.add(prefix);
				}
				else if (comp.startsWith("propyl")) {
					prefix = prefix.concat("-propyl");
					comp = comp.replaceFirst("propyl", " ");
					if (comp.startsWith(" -")) {
						comp = comp.replaceFirst(" -", " ");
					}
					list.add(prefix);
				}
				else if (comp.startsWith("butyl")) {
					prefix = prefix.concat("-butyl");
					comp = comp.replaceFirst("butyl", " ");
					if (comp.startsWith(" -")) {
						comp = comp.replaceFirst(" -", " ");
					}
					list.add(prefix);
				}
				else if (comp.startsWith("pentyl")) {
					prefix = prefix.concat("-pentyl");
					comp = comp.replaceFirst("ethyl", " ");
					if (comp.startsWith(" -")) {
						comp = comp.replaceFirst(" -", " ");
					}
					list.add(prefix);
				}
				else if (comp.startsWith("chloro")) {
					prefix = prefix.concat("-chloro");
					comp = comp.replaceFirst("chloro", " ");
					if (comp.startsWith(" -")) {
						comp = comp.replaceFirst(" -", " ");
					}
					list.add(prefix);
				}
				else if (comp.startsWith("iodo")) {
					prefix = prefix.concat("-iodo");
					comp = comp.replaceFirst("iodo", " ");
					if (comp.startsWith(" -")) {
						comp = comp.replaceFirst(" -", " ");
					}
					list.add(prefix);
				}
				else if (comp.startsWith("bromo")) {
					prefix = prefix.concat("-bromo");
					comp = comp.replaceFirst("bromo", " ");
					if (comp.startsWith(" -")) {
						comp = comp.replaceFirst(" -", " ");
					}
					list.add(prefix);
				}
				comp = comp.trim();

			}
		}
		List<Integer> list2 = new ArrayList<Integer>();
		HashMap<String, Object> hash2 = new HashMap<String, Object>();
		for (int i = 0; i <= list.size() - 1; i++) {
			System.out.print((i+1) + ": ");
			System.out.print(list.get(i) + "\n");
			String comp3 = list.get(i);
			int index = comp3.indexOf("-");
			//System.out.print(comp3 + "\n");
			String comp2 = comp3.substring(index+1, comp3.length());
			String comp4 = comp3.substring(0, index);
			//System.out.print(comp2 + "\n");
			for (int ii = 0; ii <= (comp4.length()-1); ii++) {
				if (comp3.charAt(ii) != ','){
					//System.out.print(comp3.charAt(ii) + "p\n");
					list2.add(0, Integer.parseInt(comp3.charAt(ii) + ""));
					//System.out.print(list2.get(0) + "o\n");
				}
			}

			hash2.put(comp2, list2);
			list2.clear();
		}
		System.out.print("(" + comp + ")\n");

		String suffix = comp.substring(comp.length() - 3, comp.length());
		HashMap<String, Integer> hash1 = PopulateHash();

		int length = 0;

		System.out.print(suffix + "\n");
		if (suffix.equals("ane")) {
			comp = comp.replaceAll("ane", " ");
			comp = comp.trim();
			System.out.print(comp + "\n");
			length = hash1.get(comp);
			System.out.print(length + "\n");
			hash2.put("chainlength", length);
			hash2.put("type", 0);
		}
		else if (suffix.equals("ene")) {
			comp = comp.replaceAll("ene", " ");
			comp = comp.trim();
			System.out.print(comp + "\n");
			if (comp.contains("-")) {
				int index = comp.indexOf("-");
				String suffix2 = comp.substring(index, comp.length());
				comp = comp.replaceAll(suffix2, " ");
				comp = comp.trim();
				suffix2 = suffix2.replaceAll("-", " ");
				suffix2 = suffix2.trim();
				System.out.print(comp + "\n");
				System.out.print(suffix2 + "\n");
				List<Integer> list3 = new ArrayList<Integer>();
				for (int ii = 0; ii <= (suffix2.length()-1); ii++) {
					if (suffix2.charAt(ii) != ','){
						//System.out.print(suffix2.charAt(ii) + "p\n");
						list3.add(0, Integer.parseInt(suffix2.charAt(ii) + ""));
						//System.out.print(list3.get(0) + "o\n");
					}
				}
				hash2.put("ene", list3);
				length = hash1.get(comp);
				System.out.print(length + "\n");
				System.out.print(length + "\n");
				hash2.put("chainlength", length);
				hash2.put("type", 1);
			}
			else {
				length = hash1.get(comp);
				if (length >= 4) {
					System.out.print("ERROR WHAT IS GOING ON\n");
				}
				hash2.put("type", 1);
				hash2.put("chainlength", length);

			}
		}
		else if (suffix.equals("yne")) {
			comp = comp.replaceAll("yne", " ");
			comp = comp.trim();
		}
		else {
			System.out.print("ERROR WHAT IS GOING ON\n");
		}
		return hash2;

	}


	public static HashMap<String, Integer> PopulateHash() {
		HashMap<String, Integer> hash1 = new HashMap<String, Integer>();
		hash1.put("meth", 1);
		hash1.put("eth", 2);
		hash1.put("prop", 3);
		hash1.put("but", 4);
		hash1.put("pent", 5);
		hash1.put("hex", 6);
		hash1.put("hept", 7);
		hash1.put("oct", 8);
		hash1.put("non", 9);
		hash1.put("dec", 10);
		return hash1;
	}
}

class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image img;

	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}

