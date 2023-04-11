import java.io.*;
import java.util.*;
import java.text.*;

public class Padding {

	private static final String pad = "                                                  ";

	// Padding done in the middle 
	public static void centerPadding(int data, int textLength, FileWriter out) throws java.io.IOException {
		
		String textData = Integer.toString(data);
		
		if (textLength < textData.length()){
			textLengthError(textLength, out);
			return;
		}
		
		int totalLengthOfPad = textLength - textData.length();
		int endLengthOfPad = totalLengthOfPad / 2;
		int startLengthOfPad = totalLengthOfPad - endLengthOfPad;

		String startPadding = pad.substring(0, startLengthOfPad);
		String endPadding = pad.substring(0, endLengthOfPad);
		out.write(startPadding + textData + endPadding);
		
		return;
	}

	// Padding done on the left
	public static void leftPadding(int data, int textLength, FileWriter out) throws java.io.IOException {
		
		String textData = Integer.toString(data);
		
		if (textLength < textData.length()){
			textLengthError(textLength, out);
			return;
		}
		
		String padding = pad.substring(0, textLength-textData.length());
		out.write(textData + padding);
		
		return;
	}

	// Padding done on the right
	public static void rightPadding(int data, int textLength, FileWriter out) throws java.io.IOException {
		
		String textData = Integer.toString(data);
		
		if (textLength < textData.length()){
			textLengthError(textLength, out);
			return;
		}
		
		String padding = pad.substring(0, textLength-textData.length());
		out.write(padding + textData);
		
		return;
	}

	// Padding done in the middle 
	public static void centerPadding(double data, int textLength, FileWriter out) throws java.io.IOException {
		
		String textData = Double.toString(data);
		
		if (textLength < textData.length()){
			textLengthError(textLength, out);
			return;
		}
		
		int totalLengthOfPad = textLength - textData.length();
		int endLengthOfPad = totalLengthOfPad / 2;
		int startLengthOfPad = totalLengthOfPad - endLengthOfPad;

		String startPadding = pad.substring(0, startLengthOfPad);
		String endPadding = pad.substring(0, endLengthOfPad);
		out.write(startPadding + textData + endPadding);
		
		return;
	}

	// Padding done on the left
	public static void leftPadding(double data, int textLength, FileWriter out) throws java.io.IOException {
		
		String textData = Double.toString(data);
		
		if (textLength < textData.length()){
			textLengthError(textLength, out);
			return;
		}
		
		String padding = pad.substring(0, textLength-textData.length());
		out.write(textData + padding);
		
		return;
	}

	// Padding done on the right
	public static void rightPadding(double data, int textLength, FileWriter out) throws java.io.IOException {
		
		String textData = Double.toString(data);
		
		if (textLength < textData.length()){
			textLengthError(textLength, out);
			return;
		}
		String padding = pad.substring(0, textLength-textData.length());
		out.write(padding + textData);
		return;
	}

	// Padding done in the middle 
	public static void centerPadding(double data, int textLength, int positions, FileWriter out) throws java.io.IOException {
		
		String textData = fixDecimals(data, positions);
		
		if (textLength < textData.length()){
			textLengthError(textLength, out);
			return;
		}
		
		int totalLengthOfPad = textLength - textData.length();
		int endLengthOfPad = totalLengthOfPad / 2;
		int startLengthOfPad = totalLengthOfPad - endLengthOfPad;

		String startPadding = pad.substring(0, startLengthOfPad);
		String endPadding = pad.substring(0, endLengthOfPad);
		out.write(startPadding + textData + endPadding);
		
		return;
	}

	// Padding done on the left
	public static void leftPadding(double data, int textLength, int positions, FileWriter out) throws java.io.IOException {
		
		String textData = fixDecimals(data, positions);
		
		if (textLength < textData.length()){
			textLengthError(textLength, out);
			return;
		}
		
		String padding = pad.substring(0, textLength-textData.length());
		out.write(textData + padding);
		
		return;
	}

	// Padding done on the right
	public static void rightPadding(double data, int textLength, int positions, FileWriter out) throws java.io.IOException {
		
		String textData = fixDecimals(data, positions);
		
		if (textLength < textData.length()){
			textLengthError(textLength, out);
			return;
		}
		
		String padding = pad.substring(0, textLength-textData.length());
		out.write(padding + textData);
		
		return;
	}

	// Padding done in the middle 
	public static void centerPadding(String textData, int textLength, FileWriter out) throws java.io.IOException {
		
		if (textLength < textData.length()){
			textLengthError(textLength, out);
			return;
		}
		int totalLengthOfPad = textLength - textData.length();
		int endLengthOfPad = totalLengthOfPad / 2;
		int startLengthOfPad = totalLengthOfPad - endLengthOfPad;

		String startPadding = pad.substring(0, startLengthOfPad);
		String endPadding = pad.substring(0, endLengthOfPad);
		out.write(startPadding + textData + endPadding);
		return;
	}

	// Padding done on the left
	public static void leftPadding(String textData, int textLength, FileWriter out) throws java.io.IOException {
		
		if (textLength < textData.length()){
			textLengthError(textLength, out);
			return;
		}
		String padding = pad.substring(0, textLength-textData.length());
		out.write(textData + padding);
		return;
	}

	// Padding done on the right
	public static void rightPadding(String textData, int textLength, FileWriter out) throws java.io.IOException {
		
		if (textLength < textData.length()){
			textLengthError(textLength, out);
			return;
		}
		String padding = pad.substring(0, textLength-textData.length());
		out.write(padding + textData);
		return;
	}

	private static void textLengthError(int textLength, FileWriter out) throws java.io.IOException {
		
		for(int i=1; i<=textLength; ++i){
			out.write("#");
		}
		return;
	}

	private static String fixDecimals(double data, int positions){
		
		data=Math.round(data * Math.pow(10,positions)) / Math.pow(10,positions);
		String textData = Double.toString(data);
		return(textData);
	}

}