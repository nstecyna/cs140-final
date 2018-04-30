package project;

public class Memory {
	public static int DATA_SIZE = 2048;
	private int[] data = new int[DATA_SIZE];
	
	int[] getData() {
		return this.data;
	}
	
	public int getData(int index) {
		return this.data[index];
	}
	
	public void setData(int index, int value) {
		this.data[index] = value;
	}
	
}
