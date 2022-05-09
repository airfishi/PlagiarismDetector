package plagdetect;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PlagiarismDetector implements IPlagiarismDetector {
	
	String currDocs = "tinyDocs";
	//on Desktop
	//File currPath = new File("B://CS 220 HOMEWORK/CS220 Plagiarism Detector/cs220-plagiarism-detector/");
	//on Laptop
	File currPath = new File("D://CS 220 HOMEWORK/PlagiarismDetector/");
	//on my Desktop
	//File currPath = new File("B://CS 220 HOMEWORK/CS220 Plagiarism Detector/cs220-plagiarism-detector/");
	//on my Laptop
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
		for(String f1 : docToNgrams.keySet()) {
			for(String f2: docToNgrams.keySet()) {
				if(!f1.equals(f2)) {
					
				}
			}
		}
		
		return null;
	}

	public void readFileNotInUse(File filename) throws IOException {
		//TODO
		Set<String> ngrams = new HashSet<>();
		try (Scanner scan = new Scanner(filename)) {
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] words = line.split(" ");
				
				for(int i = 0; i + n -1 < words.length; i++) {
					String ngram = "";
					for(int j = 0; j < n; j++) {
						ngram += words[i + j] + " ";
					}
					
					ngrams.add(ngram.substring(0,ngram.length()-1));

				}
			}
		}catch(IOException e){
			
		}
		String name = filename.toString().substring(filename.toString().indexOf("\\") + 1);
		name = name.substring(name.indexOf("\\") + 1);
		docToNgrams.put(name, ngrams);
	}
	

	@Override
	public void readFile(File filename) throws IOException {
		
		Set<String> ngrams = new HashSet<>();

		Path file = Path.of(currPath.toString() + "\\"+ filename.toString());

			String doc = Files.readString(file);
			
			int numSpaces = 0;
			String tempDoc = doc;
			while(tempDoc.indexOf(" ") > 0) {
				numSpaces++;
				tempDoc = tempDoc.substring(tempDoc.indexOf(" ") + 1);
			}
			
			/*
			while the number of spaces is greater than or equal to the 
			number of spaces in an ngram (n-1), get the first n words, aka
			find the number of strings with n-1 spaces in, and add them to
			ngrams then do the same process starting at the next word in the
			doc until the number of words left is less than the size of
			an ngram (until there are n-1 spaces left
			*/

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
				ngrams.add(toAdd);
				if(i + n -1< numSpaces) {
					doc = doc.substring(doc.indexOf(" ") + 1);
			
				}else if(doc.charAt(0) == ' '){
					doc = doc.substring(1,doc.length());
				} else
					doc = doc.substring(0,doc.length());
			}
			
		String name = filename.toString().substring(filename.toString().indexOf("\\") + 1);
		name = name.substring(name.indexOf("\\") + 1);
		docToNgrams.put(name, ngrams);
		
	}

	@Override
	public int getNumNGramsInCommon(String file1, String file2) {
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
		Collection<String> pairs = new HashSet<>();
		for(String file1 : docToNgrams.keySet()) {
			for(String file2 : docToNgrams.keySet()) {
				if(file1.compareTo(file2) < 0) {
					if(getNumNGramsInCommon(file1,file2) == minNgrams || getNumNGramsInCommon(file1,file2) > minNgrams) {
						pairs.add(file1 + " " + file2 + " " + getNumNGramsInCommon(file1,file2));
					}
				}
			}
		}
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
