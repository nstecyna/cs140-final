package project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class FullAssembler implements Assembler {



	@Override
	public int assemble(String inputFileName, String outputFileName, StringBuilder error) {
		if (error == null)
			throw new IllegalArgumentException("Coding error: the error buffer is null");

		int lineNum = 0;
		int retVal = 0;
		boolean isData = false;

		String[] parts;
		int argValue;
		int argAddress;

		int blankLine = 0;
		boolean hasBlankLine = false;

		try (Scanner scan = new Scanner(Paths.get(inputFileName))) {
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				lineNum++;

				// if a non-blank line is after a blank line
				if (hasBlankLine) {
					if (line.trim().length() > 0) {
						error.append("\nIllegal blank line in the source file on line " + blankLine);
						hasBlankLine = false;
						retVal = blankLine;
					}
				}

				// if blank line, find out if the next one is
				if (line.trim().length() == 0) {
					if (!hasBlankLine)
						blankLine = lineNum;
					hasBlankLine = true;
					continue;
				}
				// if there is whitespace on the first char
				else if (Character.isWhitespace(line.charAt(0))){
					error.append("\nLine " + lineNum + " starts with illegal white space");
					retVal = lineNum;
				}

				// if the line says "DATA"
				if (line.trim().equalsIgnoreCase("DATA")) {
					if (!line.trim().equals("DATA")) {
						error.append("\nLine does not have DATA in upper case");
						retVal = lineNum;
					}
					isData = true;
					continue;
				}
				
				// if reading after "DATA"
				if (isData) {
					parts = line.trim().split("\\s+");
					if (parts.length < 2) {
						error.append("\nError on line " + lineNum + ": this data is missing an argument");
						retVal = lineNum;
					}
					else {
						try {
							argValue = Integer.parseInt(parts[1], 16);
						} catch (NumberFormatException e) {
							error.append("\nError on line " + lineNum +
									":  data has non-numeric value");
							retVal = lineNum;
						}
						try {
							argAddress = Integer.parseInt(parts[0], 16);
						} catch (NumberFormatException e) {
							error.append("\nError on line " + lineNum +
									":  data has non-numeric memory address");
							retVal = lineNum;
						}
					}
				}

				// if reading before "DATA
				if (!isData) {
					parts = line.trim().split("\\s+");
					if (!InstrMap.toCode.keySet().contains(parts[0])) {
						error.append("\nError on line " + (lineNum) + ": illegal mnemonic");
						retVal = lineNum;
					} else {
						if (noArgument.contains(parts[0])) {
							if (parts.length > 1) {
								error.append("\nError on line " + lineNum + ": this mnemonic cannot take arguments");
								retVal = lineNum;
							}

						}
						else {
							if (parts.length > 2) {
								error.append("\nError on line " + lineNum + ": this mnemonic has too many arguments");
								retVal = lineNum;
							} else if (parts.length < 2) {
								error.append("\nError on line " + lineNum + ": this mnemonic is missing an argument");
								retVal = lineNum;
							} else {
								try {
									argValue = Integer.parseInt(parts[1], 16);
								} catch (NumberFormatException e) {
									error.append("\nError on line " + lineNum +
											":  non-numeric value");
									retVal = lineNum;
								}
							}

						}
						if (!parts[0].toUpperCase().equals(parts[0])) {
							error.append("\nError on line " + lineNum + ": mnemonic must be upper case");
							retVal = lineNum;
						}
					}
				}

			}


		} catch (FileNotFoundException e) {
			error.append("\nError: Unable to write the assembled program to the output file");
			retVal = -1;
		} catch (IOException e) {
			error.append("\nUnexplained IO Exception");
			retVal = -1;
		}
		if (retVal == 0) {
			SimpleAssembler assembler = new SimpleAssembler();
			assembler.assemble(inputFileName,outputFileName,error);
		}
		return retVal;
	}

	public static void main(String[] args) {
		StringBuilder error = new StringBuilder();
		System.out.println("Enter the name of the file without extension: ");
		try (Scanner keyboard = new Scanner(System.in)) {
			String filename = keyboard.nextLine();
			int i = new FullAssembler().assemble(filename + ".pasm",
					filename + ".pexe", error);
			System.out.println("result = " + i);
		}
	}
}