import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;

public class image {
		int fileCount = 0;
	
		public void imageCompare() throws Exception {
			int name;
			System.out.println("Compared with [imgCom/0.jpg]: ");
			for(name=1; name <= fileCount; name++) {
				float percent = compare(
					getData("imgCom/0.jpg"),	//compareBase
					getData("img/" + String.valueOf(name) + ".jpg")	//compared
				);
				System.out.println("  Similarity: " + percent + "%");
			}
		}

		public static int[] getData(String name) {
			try{		
			BufferedImage img = ImageIO.read(new File(name));
			BufferedImage slt = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
			slt.getGraphics().drawImage(img, 0, 0, 100, 100, null);
			// ImageIO.write(slt,"jpeg",new File("slt.jpg"));
			int[] data = new int[256];
			for (int x = 0; x < slt.getWidth(); x++) {
				for (int y = 0; y < slt.getHeight(); y++) {
					int rgb = slt.getRGB(x, y);
					Color myColor = new Color(rgb);
					int r = myColor.getRed();
					int g = myColor.getGreen();
					int b = myColor.getBlue();
					data[(r + g + b) / 3]++;
				}
			}
			// data 就是所謂圖形學當中的直方圖的概念
			return data;
			}catch(Exception exception){
				System.out.println("File not found. ");
				return null;
			}
		}

		public static float compare(int[] s, int[] t) {
			try{
			float result = 0F;
			
			for (int i = 0; i < 256; i++) {
				int abs = Math.abs(s[i] - t[i]);
				int max = Math.max(s[i], t[i]);
				result += (1 - ((float) abs / (max == 0 ? 1 : max)));
			}
			
			return (result / 256) * 100;
			
			}catch(Exception exception){
				return 0;
			}
		}

	}


