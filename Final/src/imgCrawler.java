//org.jsoup
import org.jsoup.Connection;
import org.jsoup.Jsoup;

//org.jsoup.nodes
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

//org.jsoup.select
import org.jsoup.select.Elements;

//java.io
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

//java.net
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.MalformedURLException;
//import java.util.Arrays;

//java.util
import java.util.Scanner;

//javax.imageio
import javax.imageio.ImageIO;

//java.awt
import java.awt.Color;
import java.awt.image.BufferedImage;

public class imgCrawler {
	int fileCount;
	
	public static void downImages(String filePath, String imgUrl, String saveFileName, int fileNum) {
        //build filePath if not exist
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //get fileName
        String fileName = imgUrl.substring(imgUrl.lastIndexOf('/') + 1, imgUrl.length());
        //System.out.println(fileName);
        //get file extension
        String subFileName = imgUrl.substring(imgUrl.lastIndexOf('.'), imgUrl.length());
        //System.out.println(subFileName);

        try {
            //encode non-UTF8 texts
            String urlTail = URLEncoder.encode(fileName, "UTF-8");
            //encode '+' to '%20'
            imgUrl = imgUrl.substring(0, imgUrl.lastIndexOf('/') + 1) + urlTail.replaceAll("\\+", "\\%20");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //set filePath(download order + extension
        File file = new File(filePath + File.separator + saveFileName + subFileName);
        String convFile = filePath + File.separator + saveFileName + subFileName;
        String convDir = filePath + File.separator + saveFileName;
        
        //System.out.println("test: " + fileNum);
        //fileExtension[fileNum] = subFileName;

        try {
            //get url
            URL url = new URL(imgUrl);
            //connect to url
            URLConnection connection = url.openConnection();
            //set timeout = 10sec
            connection.setConnectTimeout(10 * 1000);

            InputStream in = connection.getInputStream();
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            //buffer
            byte[] buf = new byte[1024];
            int size;
            //write to file
            while (-1 != (size = in.read(buf))) {
                out.write(buf, 0, size);
            }
            out.close();
            in.close();
            
            if (	//supported file format
            		(subFileName.equals(".JPG")) || 
            		(subFileName.equals(".jpg")) || 
            		(subFileName.equals(".tiff")) ||
            		(subFileName.equals(".bmp")) ||
            		(subFileName.equals(".BMP")) ||
            		(subFileName.equals(".gif")) ||
            		(subFileName.equals(".GIF")) ||
            		(subFileName.equals(".WBMP")) ||
            		(subFileName.equals(".png")) ||
            		(subFileName.equals(".PNG")) ||
            		(subFileName.equals(".JPEG")) ||
            		(subFileName.equals(".tif")) ||
            		(subFileName.equals(".TIF")) ||
            		(subFileName.equals(".TIFF")) ||
            		(subFileName.equals(".wbmp")) ||
            		(subFileName.equals(".jpeg"))
            ) {
            	converter(convFile, convDir);
            }
            else {
            	System.out.println("  File Not Supported!");
            }
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
	
	public void URLinput() {
		// TODO Auto-generated method stub
		Scanner sc= new Scanner(System.in);  
    	System.out.print("Enter an URL with the prefix HTTP or HTTPS: ");  
    	String str= sc.next();  
    	//System.out.print("You have entered: "+str);
    	sc.close();

    	String filePath = "img";
    	System.out.println("Image will be stored at [img] folder\n");
        
    	//connect to url with jsoup
        Connection connect = Jsoup.connect(str);
        try {
            //get html document
            Document document = connect.get();
            System.out.println("Connected. ");
            //search for img tags
            //Elements imgs = document.getElementsByTag("img");
            Elements imgs = document.select("img");
            System.out.println("=======Download Started========");
            //get src from img
            int fileName = 1;
            for (Element element : imgs) {
                //get img absolute src
                //String imgSrc = element.attr("abs:src");
            	String imgSrc = element.absUrl("src");
                //print URL of img
                System.out.println("  " + imgSrc);
                //download, call function downImages
                imgCrawler.downImages(filePath, imgSrc, String.valueOf(fileName), fileName);
                fileName++;
                fileCount = fileName - 1;
            }
            System.out.println("=======Download Complete=======");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void converter(String file, String dir) {
		BufferedImage bufferedImage;
		
		//String[] readFormats = ImageIO.getReaderFormatNames(); // 可讀入的格式
		//String[] writerFormatNames = ImageIO.getWriterFormatNames(); // 可輸出的格式
		//System.out.println("可讀入的圖片格式：" + Arrays.asList(readFormats));
		//System.out.println("可輸出的圖片格式：" + Arrays.asList(writerFormatNames));
		
		try {
				
			//read image file
			//file = "img/2.png";
			//String fileNum = file.substring(file.lastIndexOf('.') - 1, file.lastIndexOf('.'));
			//System.out.println("test: " + fileNum);
			File convFile = new File(file);
			
			bufferedImage = ImageIO.read(convFile);
	 
			// create a blank, RGB, same width and height, and a white background
			BufferedImage newBufferedImage = new BufferedImage(
					bufferedImage.getWidth(),
					bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
	 
			// write to jpeg file
			ImageIO.write(newBufferedImage, "jpg", new File(dir + ".jpg"));
			
			//System.out.println("Done");		
		} catch (IOException e) {
				e.printStackTrace();
		}
	}
}


