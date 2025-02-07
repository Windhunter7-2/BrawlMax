package datastructs.nintendo;

import java.util.ArrayList;

public class Brres {
	
	byte [] allBrresBytes;
	
	int brresType;
	
	int indexNum;
	
	/**
	 * Creates a Brres data structure, given the Brres identifying info.
	 * @param allBrresBytes The bytes of the .brres file
	 * @param brresType 1 for Misc Data, 2 for Model Data, etc.
	 * @param indexNum e.g. the common ModelData[0] == 0, whereas something like ModelData[10] == A
	 */
	public Brres(byte [] allBrresBytes, int brresType, int indexNum) {
		this.allBrresBytes = allBrresBytes;
		this.brresType = brresType;
		this.indexNum = indexNum;
		
		TODO
		/*
brresType = 01 for Misc Data, 02 for Model Data, 03 for Texture Data, etc.
indexNum = Index Number (For Example, the Common ModelData[0] Would Be 0000, Whereas Something Like ModelData[10] Would Be 000A
		 * */
	}
	
	public int getBrresType() {
		return brresType;
	}

	public void setBrresType(int brresType) {
		this.brresType = brresType;
	}

	public int getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(int indexNum) {
		this.indexNum = indexNum;
	}

	public byte [] getBytes() {
		return this.allBrresBytes;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TODO

	}

}
