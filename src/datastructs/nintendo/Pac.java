package datastructs.nintendo;

import java.util.ArrayList;

import util.converters.ByteConverters;
import util.files.BinaryFileCommonIO;

//ARC File / .pac File
/**
 * This is the data structure for both .arc and .pac files, as these files are synonymous with each other.
 * Compression, such as .pcs files or individual compression inside a file, is not currently supported, at this time.
 * @author Windhunter
 */
public class Pac {
	
	/**
	 * All of the Brres files within the Arc/Pac.
	 */
	private ArrayList<Brres> brresFiles;
	
	/**
	 * The initial bytes that define the behavior of the Arc/Pac.
	 */
	private byte [] initBytes;
	
	/**
	 * This is how many bytes are the initBytes defined in the Arc/Pac.
	 */
	private final int numInitBytes = 0x40;
	
	/**
	 * Creates a Pac/Arc data structure, given all the Pac/Arc bytes.
	 * @param allPacBytes The bytes from the original file
	 */
	public Pac(byte [] allPacBytes) {
		//Get Initial Bytes
		this.initBytes = ByteConverters.getInitBytes(allPacBytes, this.numInitBytes);
		
		//Get Brres's
		brresFiles = new ArrayList<Brres>();
		byte [] allBrresBytes = BinaryFileCommonIO.getBytesAtAddress(allPacBytes, (this.numInitBytes));
		int brresIdentifierCounter = 0;
		int curDataType = 0;
		int curIndexNum = 0;
		int curBrresSize = 0;
		byte [] curBrresBytes = null;
		int curByteIndexOffset = 0;
		for (int i = 0; i < allBrresBytes.length; i++) {
			//Take Note of Brres Information
			if (brresIdentifierCounter == 1) {
				//Misc Data, Model Data, Or Texture Data
				curDataType = Byte.toUnsignedInt(allBrresBytes[i]);
				
				//Index Number; Offset of 1/2 for brresIdentifierCounter of 2/3
				byte [] curIndexNumsAsBytes = {allBrresBytes[i+1], allBrresBytes[i+2]};
				curIndexNum = ByteConverters.convertToNum(curIndexNumsAsBytes);
				
				//Number of Bytes; Offset of 5/6 for brresIdentifierCounter of 6/7
				byte [] curBrresSizeAsBytes = {allBrresBytes[i+5], allBrresBytes[i+6]};
				curBrresSize = ByteConverters.convertToNum(curBrresSizeAsBytes);
			}
			
			//Start Recording Brres Itself, & Stop at End of Brres
			if (brresIdentifierCounter == 31 + curBrresSize) {
				this.brresFiles.add(new Brres(curBrresBytes, curDataType, curIndexNum));
				brresIdentifierCounter = 0;
				curByteIndexOffset = 0;
				continue;
			}
			if (brresIdentifierCounter > 31) {	//The Brres Itself
				if (brresIdentifierCounter == 32)
					curBrresBytes = new byte[curBrresSize];
				curBrresBytes[curByteIndexOffset] = allBrresBytes[i];
				curByteIndexOffset++;
			}
			brresIdentifierCounter++;
		}
	}
	
	/**
	 * Gets the byte representation of an Arc/Pac file, for use in things such as saving or exporting.
	 */
	public byte [] getPacFile() {
		//Compile All the Brres Bytes Together
		ArrayList<Byte> tempBytes = new ArrayList<Byte>();
		for (int i = 0; i < this.brresFiles.size(); i++) {	//Brres List
			Brres curBrres = this.brresFiles.get(i);
			byte [] curBytes = curBrres.getBytes();
			
			//Add in Bytes for Brres Signifiers:
			//00 XX YY YY 00 00 ZZ ZZ 00 00 FF FF 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
			byte byteX = (byte) curBrres.getBrresType();
			byte [] bytesY = ByteConverters.convertToIndividualBytes(curBrres.getIndexNum(), 2);
			byte [] bytesZ = ByteConverters.convertToIndividualBytes(curBytes.length, 2);
			byte [] brresSignifiers = {0, byteX, bytesY[0], bytesY[1], 0, 0, bytesZ[0], bytesZ[1],
				0, 0, (byte) 0xFF, (byte) 0xFF, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			for (int j = 0; j < brresSignifiers.length; j++) {
				tempBytes.add(brresSignifiers[j]);
			}
			for (int j = 0; j < curBytes.length; j++) {	//Each Brres
				byte curByte = curBytes[j];
				tempBytes.add(curByte);	//The Brres Bytes Themselves
			}
		}
		return ByteConverters.concatBytes(this.initBytes, tempBytes);
	}

	/**
	 * All of the Brres files within the Arc/Pac.
	 */
	public ArrayList<Brres> getBrresFiles() {
		return brresFiles;
	}

	//For Testing
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String testFileString1 = "Z:\\Windhunter's Programs\\BrawlMax\\File for PAC Experiments.pac";
		String testFileString2 = "Z:\\Windhunter's Programs\\BrawlMax\\File for PAC Experiments_TEST_EXPORT.pac";
		Pac testPac = new Pac(BinaryFileCommonIO.getAllBytesFromFile(testFileString1));
		byte [] testBytes = testPac.getPacFile();
		BinaryFileCommonIO.writeAllBytesToFile(testFileString2, testBytes);
		
//		byte [] test1 = BinaryFileCommonIO.getAllBytesFromFile(testFileString1);
//		byte [] test2 = BinaryFileCommonIO.getAllBytesFromFile(testFileString2);
//		for (int i = 0; i < test1.length; i++) {
//			if (test1[i] != test2[i])
//				System.out.println("ERROR AT " + i);
//		}
		
		
	}

}
