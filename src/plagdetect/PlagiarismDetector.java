package plagdetect;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PlagiarismDetector implements IPlagiarismDetector {
	
	String currDocs = "tinyDocs";
	File currPath = new File("B://CS 220 HOMEWORK/CS220 Plagiarism Detector/cs220-plagiarism-detector/docs/" + currDocs);

	int n; 
	
	public PlagiarismDetector(int n) {
		this.n = n;
	}
	
	@Override
	public int getN() {
		return n;
	}

	@Override
	public Collection<String> getFilenames() {
		Collection<String> toRet = new LinkedList<>();
		for(File file : currPath.listFiles()) {
			toRet.add(file.getName());
		}
		System.out.println(toRet);
		return toRet;
	}

	@Override
	public Collection<String> getNgramsInFile(String filename) {
		Set<String> ngrams = new HashSet<>();
		
		Path file = Path.of(currPath.toString() + "/" + filename); 
		String doc = "";
		System.out.println(file.toString());
		try {
			doc = Files.readString(file);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println(doc);
		
		//add substring from 0 to nth space
		//string gets substring from first space to end
		
		
		
		//create scanner for file
		/*try {
			Scanner scan = new Scanner(new FileInputStream(filename));
			int x = 0;
			while(x <3) {
				scan.nex()
			}
		
		} catch(IOException e) {
			throw new RuntimeException(e);
			
		}
		*/
		return null;
	}

	@Override
	public int getNumNgramsInFile(String filename) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, Map<String, Integer>> getResults() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readFile(File file) throws IOException {
		// TODO Auto-generated method stub
		// most of your work can happen in this method
		
	}

	@Override
	public int getNumNGramsInCommon(String file1, String file2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<String> getSuspiciousPairs(int minNgrams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readFilesInDirectory(File dir) throws IOException {
		// delegation!
		// just go through each file in the directory, and delegate
		// to the method for reading a file
		for (File f : dir.listFiles()) {
			readFile(f);
		}
	}
}
