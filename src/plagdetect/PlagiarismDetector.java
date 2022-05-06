package plagdetect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PlagiarismDetector implements IPlagiarismDetector {
	
	String currDocs = "tinyDocs";
	//on Desktop
	File currPath = new File("B://CS 220 HOMEWORK/CS220 Plagiarism Detector/cs220-plagiarism-detector/");
	//on Laptop
	//File currPath = new File("D://CS 220 HOMEWORK/PlagiarismDetector/");
	int n = 3; 
	
	Map<String,Set<String>> docToNgrams = new HashMap<>();
	
	public PlagiarismDetector(int n) {
		this.n = n;
	}
	
	@Override
	public int getN() {
		return n;
	}

	@Override
	public Collection<String> getFilenames() {
		/*
		Collection<String> toRet = new LinkedList<>();
		for(File file : currPath.listFiles()) {
			toRet.add(file.getName());
		}
		System.out.println(toRet);
		System.out.println(docToNgrams.keySet());
		*/
		return docToNgrams.keySet();
	}

	@Override
	public Collection<String> getNgramsInFile(String filename) {
		return docToNgrams.get(filename);
	}

	@Override
	public int getNumNgramsInFile(String filename) {

		return docToNgrams.get(filename).size();
	}

	@Override
	public Map<String, Map<String, Integer>> getResults() {
		// TODO Auto-generated method stub
		
		for(String f1 : docToNgrams.keySet()) {
			for(String f2: docToNgrams.keySet()) {
				if(!f1.equals(f2)) {
					
				}
			}
		}
		
		return null;
	}

	@Override
	public void readFile(File filename) throws IOException {
		
		Set<String> ngrams = new HashSet<>();
		//Scanner scan = new Scanner(new FileInputStream(filename));
		//System.out.println(filename.toString());
		Path file = Path.of(currPath.toString() + "\\"+ filename.toString());
		//System.out.println(file.toString());
			String doc = Files.readString(file);
			//System.out.println(doc);
			//count number of spaces in doc
			int numSpaces = 0;
			String tempDoc = doc;
			while(tempDoc.indexOf(" ") > 0) {
				numSpaces++;
				tempDoc = tempDoc.substring(tempDoc.indexOf(" ") + 1);
			}
			//System.out.println("Number of spaces : " +  numSpaces + " N is : " + n);
			
			//while the number of spaces is greater than or equal to the 
			//number of spaces in an ngram (n-1), get the first n words, aka
			//find the number of strings with n-1 spaces in, and add them to
			//ngrams then do the same process starting at the next word in the
			//doc until the number of words left is less than the size of
			//an ngram (until there are n-1 spaces left
			
			
			//tempDoc = doc;
			for(int i = 0; i < numSpaces -1; i++) {
				
				String toAdd = "";
				tempDoc = doc;
				
				for(int j = 0; j < n; j++) {
					if(i + n - 1 < numSpaces) {
						int cut = tempDoc.indexOf(" ") + 1;
						toAdd += tempDoc.substring(0,cut);
						tempDoc = tempDoc.substring(cut);
					} else if(doc.charAt(0) == ' ') {
						toAdd = doc.substring(1);
					} else {
						toAdd = doc;
					}

				}
				if(toAdd.charAt(toAdd.length()-1) == ' ')
					toAdd = toAdd.substring(0,toAdd.length()-1);
				//System.out.println(toAdd);
				ngrams.add(toAdd);
				if(i + n -1< numSpaces) {
					doc = doc.substring(doc.indexOf(" ") + 1);
			
				}else if(doc.charAt(0) == ' '){
					doc = doc.substring(1,doc.length());
				} else
					doc = doc.substring(0,doc.length());
				//System.out.println(doc);
			}
			
		//System.out.println(ngrams);
		String name = filename.toString().substring(filename.toString().indexOf("\\") + 1);
		name = name.substring(name.indexOf("\\") + 1);
		docToNgrams.put(name, ngrams);
		
	}

	@Override
	public int getNumNGramsInCommon(String file1, String file2) {
		// TODO Auto-generated method stub
		int toRet = 0;
		Collection<String> file1Ngrams = docToNgrams.get(file1);
		for(String ngram : file1Ngrams) {
			if(docToNgrams.get(file2).contains(ngram)) {
				toRet++;
			}
		}
		
		return toRet;
	}

	@Override
	public Collection<String> getSuspiciousPairs(int minNgrams) {
		// TODO fix bug
		
		//for key in docsToNgrams, compare to all files not yet compared to
		//if they contain
		
		//System.out.println(docToNgrams.keySet());
		Collection<String> pairs = new HashSet<>();
		for(String file1 : docToNgrams.keySet()) {
			for(String file2 : docToNgrams.keySet()) {
				if(!file1.equals(file2)) {
					if(getNumNGramsInCommon(file1,file2) == minNgrams || getNumNGramsInCommon(file1,file2) > minNgrams) {
						pairs.add(file1 + " " + file2 + " " + getNumNGramsInCommon(file1,file2));
					}
				}
			}
		}
		
		//TODO currently working on why these docs don't appear when this is run
		/*
		int j = 0;
		for(String x : docToNgrams.get("2994.txt")) {
			if(docToNgrams.get("2989.txt").contains(x)) {
				j++;
			}
		}
		System.out.println(j);
		*/
		
		return pairs;
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
