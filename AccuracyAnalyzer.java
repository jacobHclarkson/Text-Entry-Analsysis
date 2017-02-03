// methods to analyze the accuracy of user input
// JHC
// February 2017

import java.util.ArrayList;

public class AccuracyAnalyzer {
	String prescribedText = "";
	String transcribedText = "";
	String inputStream ="";
	int [][] sMatrix;
	int corrections = 0;
	double C, INF, IF, accuracy;

	// ctor
	public AccuracyAnalyzer(String _prescribedText, String _transcribedText, String _inputStream, int _corrections) {
		prescribedText = _prescribedText;

		transcribedText = _transcribedText;
		inputStream = _inputStream;
		corrections = _corrections;

		// create string comparison matrix
		sMatrix = new int[Math.max(prescribedText.length(), transcribedText.length())][Math.max(prescribedText.length(), transcribedText.length())];
	}

	// main analysis method
	public void Analyze() {
		C = (double)(Correct());
		INF = (double)(IncorrectNotFixed());
		IF = (double)(IncorrectFixed());
		double totalErrorRate = ((INF + IF)/(C + INF + IF)) * 100;
		accuracy = 100 - totalErrorRate;
	}

	// return number of correct keystrokes
	// C = max(|presented|, |transcribed|) - MSD(presented, transcribed)
	private int Correct() {
		return (Math.max(prescribedText.length(), transcribedText.length()) - MSD(sMatrix));
	}

	// return number of incorrect characters that were not fixed
	// INF = MSD(presented, transcibed)
	private int IncorrectNotFixed() {
		return MSD(sMatrix);
	}

	// return number of backspaces pressed
	private int Fixes() {
		return corrections;	
	}

	// return number of keystrokes used for fixes
	// chars in inStream that are not in transcribed
	private int IncorrectFixed() {
		int count = 0;
		// iterate through and check char for char, increment to keep aligned
		int j = 0;
		for(int i=0 ; i<transcribedText.length()-1 ; i++) {
			if(inputStream.charAt(j) == transcribedText.charAt(i) && transcribedText.charAt(i) != '~') {} else {
				count += 1;
				i = i-1;
				j += 1;
			}
			j += 1;
		}
		return count;
	}

	// returns the Minimum String Distance between two strings
	private int MSD(int[][] sMatrix) {
		for(int i=0 ; i<prescribedText.length() ; i++) {
			sMatrix[i][0] = i;
		} 
		for(int j=0 ; j<transcribedText.length() ; j++) {
			sMatrix[0][j] = j;
		}
		for(int i=1 ; i<prescribedText.length() ; i++) {
			for(int j=1 ; j<transcribedText.length() ; j++) {
				int x = sMatrix[i-1][j] + 1;
				int y = sMatrix[i][j-1] + 1;
				int z = sMatrix[i-1][j-1] + MSDhelper(prescribedText.charAt(i), transcribedText.charAt(j));
				sMatrix[i][j] = Math.min(Math.min(x, y), Math.min(y, z));
			}
		}
		return sMatrix[prescribedText.length()-1][transcribedText.length()-1];
	}
	private int MSDhelper(char a, char b) {
		if(a == b)
			return 0;
		else
			return 1;
	}
}
