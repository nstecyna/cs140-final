package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Loader {

	public static String load(MachineModel model, File file, int codeOffset, int memoryOffset) {
		int codeSize = 0;
		if (model == null || file == null)
			return null;
		try (Scanner input = new Scanner(file)){
			boolean incode = true;
			while (input.hasNextLine()) {
				String line1 = input.nextLine();
				String line2 = input.nextLine();
				Scanner parser = new Scanner(line1 + " " + line2);
				int firstInt = parser.nextInt();
				int arg;
				try {
					if (incode) {
						if (firstInt == -1)
							incode = false; //(that ends this iteration of the loop)
						else {
							arg = parser.nextInt();
							model.setCode(codeOffset+codeSize, firstInt, arg);
							codeSize++;
						}
					} else {
						try {
							int value = parser.nextInt();
							model.setData(firstInt+memoryOffset, value);
							parser.close();
						} catch(NoSuchElementException e){
							return "From Scanner: NoSuchElementException";
						}
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					return "Array Index " + e.getMessage();
				} catch (NoSuchElementException e) {
					return "From Scanner: NoSuchElementException";
				}
			}

			return "" + codeSize;
		} catch (FileNotFoundException e) {
			return "File " + file.getName() + " Not Found";
		}
	}

	public static void main(String[] args) {
		MachineModel model = new MachineModel();
		String s = Loader.load(model, new File("factorial8.pexe"),100,200);
		for(int i = 100; i < 100+Integer.parseInt(s); i++) {
			System.out.println(model.getOp(i));			
			System.out.println(model.getArg(i));			
		}
		for (int i = 200; i < 203; i++)
			System.out.println(i + " " + model.getData(i));
	}

}
