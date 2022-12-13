
public class mainProgram {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		imgCrawler dl = new imgCrawler();
		image imgcom = new image();
		
		dl.URLinput();
		
		System.out.println("");

        imgcom.fileCount = dl.fileCount;	//sync fileCount
        //System.out.println(imgcom.fileCount);
        
        if (imgcom.fileCount != 0) {
        	imgcom.imageCompare();
        }
        else {
        	System.out.println("No file to compare! ");
        }
	}

}
