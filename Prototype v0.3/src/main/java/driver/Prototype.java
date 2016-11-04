package driver;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import parser.*;
import ruleContainer.RuleEditor;
import ruleContainer.RuleFactory;
import java.util.ArrayList;



public class Prototype {
	KieServices ks;
	KieContainer kContainer;
	KieSession kSession;
	KieFileSystem kfs;
	KieBase kBase;
	Boolean newRuleMade = false;
	ArrayList<DataObject> dataObj;
	ArrayList<Parser> parsers;
	DataObjectCollection dataObjCol;
	
	public Prototype()
	{
		dataObj = new ArrayList<DataObject>();
		parsers = new ArrayList<Parser>();
		ks = KieServices.Factory.get();
		kContainer = ks.getKieClasspathContainer();
		kfs = ks.newKieFileSystem();
		dataObjCol = new DataObjectCollection();
	}
	
	public void createNewRules(RuleFactory rf,RuleEditor ruleEditor)
	{
		try {
			String fileName;
			fileName = rf.createRule();
			
			ruleEditor.addRule(fileName);
			rebuildKFS();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public KieServices getKs() {
		return ks;
	}

	public void setKs(KieServices ks) {
		this.ks = ks;
	}

	public KieFileSystem getKfs() {
		return kfs;
	}

	public void setKfs(KieFileSystem kfs) {
		this.kfs = kfs;
	}

	public void importLogFile(Scanner scanner) throws IOException
	{
		Parser parser = new Parser();
		boolean fileFound = selectLogFile(scanner, parser,dataObj);
		if(fileFound)
		{
			parsers.add(parser);
		}
	}

	public boolean selectLogFile(Scanner scanner, Parser parser,ArrayList<DataObject> dataObj)
			throws IOException {
		System.out.println("Please input the csv filename");
		String csvFile = scanner.nextLine();
		while (!(new File(csvFile + ".csv").exists())&&!(csvFile.equals("EXIT"))) {
			System.out.println("No file found!");
			System.out.println("Please input the csv filename or enter 'EXIT' to leave");
			csvFile = scanner.nextLine();
			
		}
		if(csvFile.equals("EXIT"))
		{
			System.out.println("No file has been imported.");
			return false;
		}
		else
		{
			System.out.println("LogFile " + csvFile + ".csv has been imported.");
			dataObj.add(parser.parse(csvFile + ".csv"));
			return true;
		}
	}

	public void runOnce(DataObject dataObj, Parser parser, KieSession kSession, KieContainer kContainer)
			throws IOException {

	}

	/*
	 * http://stackoverflow.com/questions/23784652/drools-knowle dgebase-
	 * deprecated
	 */
	private void addRule(String newRulePath) throws FileNotFoundException {
		ArrayList<String> fileNames = listFilesForFolder(new File("src/main/resources/rules"));
		FileInputStream fis;
		
		for(String fileName : fileNames){
			fis = new FileInputStream("src/main/resources/rules/" + fileName);
			kfs.write("src/main/resources/" + fileName, ks.getResources().newInputStreamResource(fis));
		}
		
		KieBuilder kieBuilder = ks.newKieBuilder(kfs).buildAll();
		
		Results results = kieBuilder.getResults();
		if (results.hasMessages(Message.Level.ERROR)) {
			System.out.println(results.getMessages());
			throw new IllegalStateException("### errors ###");
		}
		
		KieContainer kContainer = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());

		KieBaseConfiguration config = ks.newKieBaseConfiguration();
	    config.setOption(EventProcessingOption.STREAM);

	    kBase = kContainer.newKieBase( config );
	    newRuleMade = true;
	    //kSession = kieBase.newKieSession();

	}
	
	public void updateAll() throws IOException
	{
		if (dataObj.size() > 0) {
			while (dataObj.size() > 0) {
				for (int i = 0; i < dataObj.size(); i++) {
					// create a new session and add rules to it
					
					if(newRuleMade){
						kSession = kBase.newKieSession();
					}
					else{
						kSession = kContainer.newKieSession("ksession-rules");
					}
					kSession.insert(dataObj.get(i));

					// Fire the rules engine
					kSession.fireAllRules();
					// Trash the rules
					kSession.dispose();
					// Update the specific parser
					boolean x = parsers.get(i).update(dataObj.get(i));
					// If logfile is out of lines
					if (x == true) {
						dataObj.remove(i);
						parsers.remove(i);
					}
				}
			}
		} else {
			System.out.println("No Logfile Selected.");
		}
	}
	
	
	public void updateOnce() throws IOException
	{
		if (dataObj.size() > 0) {
			for (int i = 0; i < dataObj.size(); i++) {
				// create a new session and add rules to it
				//kSession = kContainer.newKieSession("ksession-rules");
				
				if(newRuleMade){
					kSession = kBase.newKieSession();
				}
				else{
					kSession = kContainer.newKieSession("ksession-rules");
				}
				
				kSession.insert(dataObj.get(i));
				//kSession.insert(arg0)

				// Fire the rules engine
				kSession.fireAllRules();

				// Trash the rules
				kSession.dispose();
				// Update the specific parser
				boolean x = parsers.get(i).update(dataObj.get(i));
				// If logfile is out of lines
				if (x == true) {
					dataObj.remove(i);
					parsers.remove(i);
					System.out.println("Remove");
				}
			}
		} else
			System.out.println("No Logfile Selected.");
	}
	
	public ArrayList<String> listFilesForFolder(final File folder) {
		ArrayList<String> drls = new ArrayList<String>();
	    for (final File fileEntry : folder.listFiles()) {
	         drls.add(fileEntry.getName());
	         //System.out.println(fileEntry.getName());
	    }
	    return drls;
	}
	
	 public void rebuildKFS(){
		KieBuilder kieBuilder = ks.newKieBuilder(kfs).buildAll();
		
		Results results = kieBuilder.getResults();
		if (results.hasMessages(Message.Level.ERROR)) {
			System.out.println(results.getMessages());
			throw new IllegalStateException("### errors ###");
		}
		
		KieContainer kContainer = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());

		KieBaseConfiguration config = ks.newKieBaseConfiguration();
	    config.setOption(EventProcessingOption.STREAM);

	    kBase = kContainer.newKieBase( config );
	}
	 
	 public void updateKie()
	 {
		ks = KieServices.Factory.get();
		kContainer = ks.getKieClasspathContainer();
		kfs = ks.newKieFileSystem();
	 }
	 
	 public boolean checkForLogFile()
	 {
		 return (dataObj.size()>0);
	 }
	
	
}
