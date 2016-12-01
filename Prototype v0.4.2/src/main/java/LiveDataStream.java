import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class LiveDataStream {

	//public static void main(String[] args) throws ParserConfigurationException, SAXException, TransformerFactoryConfigurationError, TransformerException {
		//LiveDataStream x = new LiveDataStream();
		//x.getDataStream("");
	//}
	
	public void getDataStream(String website) throws ParserConfigurationException, SAXException, TransformerFactoryConfigurationError, TransformerException
    {
       // System.out.println("RWT getting called");
        try {
            String uri = "http://www.w3schools.com/xml/cd_catalog.xml";
                    //"http://apps.tsa.dhs.gov/MyTSAWebService/GetTSOWaitTimes.ashx?ap=DCA"; //you can put any API here

            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // connection.setRequestProperty("Accept", "application/xml");
            connection.setRequestProperty("Content-Type", "application/xml");

            try {

                StringBuilder builder = new StringBuilder();

                //buffered reader to parse the result line by line
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                //string to hold a single line
                String line;

                //adding to the string builder until there is nothing else to add
                while ((line = br.readLine()) != null) {
                    builder.append(line).append("\n");
                }//end while
                br.close();

                int status = connection.getResponseCode();

                if (status != 200) {
                    //throw new Exception("Bad Response");
                }

               // System.out.println("Status: " + status);
               // System.out.println(builder.toString());
                try(  PrintWriter out = new PrintWriter("src/main/resources/data.xml" )  ){
                    out.println(builder.toString() );
                }
             
                File stylesheet = new File("src/main/resources/style.xsl");   
    	        File xmlSource = new File("src/main/resources/data.xml");
    	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   	     
    	        DocumentBuilder builder2 = factory.newDocumentBuilder();
    	        Document document = builder2.parse(xmlSource);
    	        StreamSource stylesource = new StreamSource(stylesheet);
    	        Transformer transformer = TransformerFactory.newInstance()
    	                .newTransformer(stylesource);
    	        Source source = new DOMSource(document);
    	        Result outputTarget = new StreamResult(new File("src/main/resources/x.csv"));
    	        transformer.transform(source, outputTarget);
    	     //   System.out.println(transformer.toString());
            }
            finally
            {
                connection.disconnect();
            }
            


        }
        catch(IOException ex)
        {
            System.out.println("Error");
            System.out.println(ex.getMessage());
//            helloWorldButton.setText("Hey");
        }
    }
	
	public void Xml2Csv(String filename) throws ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException {
	        File stylesheet = new File("src/main/resources/style.xsl");
	        File xmlSource = new File("src/main/resources/data.xml");

	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document document = builder.parse(xmlSource);

	        StreamSource stylesource = new StreamSource(stylesheet);
	        Transformer transformer = TransformerFactory.newInstance()
	                .newTransformer(stylesource);
	        Source source = new DOMSource(document);
	        Result outputTarget = new StreamResult(new File("src/main/resources/x.csv"));
	        transformer.transform(source, outputTarget);
	}

}
