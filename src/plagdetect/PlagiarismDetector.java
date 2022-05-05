package plagdetect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	//on Desktop
	//File currPath = new File("B://CS 220 HOMEWORK/CS220 Plagiarism Detector/cs220-plagiarism-detector/docs/" + currDocs);
	//on Laptop
	File currPath = new File("D://CS 220 HOMEWORK/PlagiarismDetector/docs/" + currDocs);
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
		
		//find first space
		//add string before to toAdd
		//repeat n-1 times
		
		
		try {
			Scanner scan = new Scanner(new FileInputStream(filename));
			Path file = Path.of(currPath.toString() + "/"+ filename);
			String doc = Files.readString(file);
			
			//count number of spaces in doc
			int numSpaces = 0;
			while(doc.indexOf(" ") > 0) {
				numSpaces++;
			}
			
			//while the number of spaces is greater than or equal to the 
			//number of spaces in an ngram (n-1), get the first n words, aka
			//find the number of strings with n-1 spaces in, and add them to
			//ngrams then do the same process starting at the next word in the
			//doc until the number of words left is less than the size of
			//an ngram (until there are n-1 spaces left
			
			
			String tempDoc = doc;
			for(int i = 0; i < numSpaces - n + 1; i++) {
				int j = 1;
				String toAdd = "";
				
				while(j < n) {
					int cut = tempDoc.indexOf(" ");
					toAdd += tempDoc.substring(0,cut);
					tempDoc = tempDoc.substring(cut + 1);
					j++;
				}
				
				ngrams.add(toAdd);
				tempDoc = doc.substring(doc.indexOf(" "));
			}
			
		
		} catch(IOException e) {
			throw new RuntimeException(e);
			
		}
		System.out.println(ngrams);
		
		return ngrams;
	}

	@Override
	public int getNumNgramsInFile(String filename) {
		//TODO
		return getNgramsInFile(filename).size();
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
