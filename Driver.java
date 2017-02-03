// Driver for typing analysis
// JHC
// February 2017

import java.text.DecimalFormat;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;

public class Driver {
    public static void main(String[]args) {
	String prescribedText = "";
	String transcribedText = "";
	String inputStream = "";
	int backSpaces = 0;
	double timeTaken = 60.0;
	
	// read data from file
	try {
	    File file = new File(args[0]);
	    InputStream in = new FileInputStream(file);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
	    prescribedText = br.readLine();
	    transcribedText = br.readLine();
	    inputStream = br.readLine();
	    br.close();
	    in.close();

	} catch(IOException e) {
	    System.out.println("Error reading file.");
	}

	for (int i=0 ; i<inputStream.length() ; i++) {
	    if (inputStream.charAt(i) == '~') {
		backSpaces += 1;
	    }
	}
	
	// create accuracy and speed analyzers based on user input and the prompt text
	AccuracyAnalyzer accAnalyzer = new AccuracyAnalyzer(prescribedText, transcribedText, inputStream, backSpaces);
	accAnalyzer.Analyze();
	SpeedAnalyzer sA = new SpeedAnalyzer(transcribedText, timeTaken, (int)accAnalyzer.INF);
	sA.Analyze();
	
	// print statistics
	DecimalFormat df = new DecimalFormat();
	df.setMaximumFractionDigits(2);
	System.out.println();
	System.out.println("STATISTICS");
	System.out.println("----------");
	System.out.println("Correct Keystrokes: " + (int)accAnalyzer.C);
	System.out.println("Corrected Errors:   " + (int)accAnalyzer.IF);
	System.out.println("Uncorrected Errors: " + (int)accAnalyzer.INF);
	System.out.println();
	System.out.println("Overall Accuracy:   " + df.format(accAnalyzer.accuracy) + " %");
	System.out.println();
	System.out.println("Gross WPM:         " + df.format(sA.grossTypingSpeed) + " WPM");
	System.out.println("Net WPM:           " + df.format(sA.netTypingSpeed) + " WPM");
    }
}
