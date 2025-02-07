package util.converters;

import java.util.ArrayList;

public class ByteConverters {
	
	/**
	 * Get the initial bytes, up to the given included number.
	 * @param allBytes The bytes to extract the initial ones from
	 * @param numBytesToGet How many bytes to extract from the beginning of the list
	 */
	public static byte [] getInitBytes(byte [] allBytes, int numBytesToGet) {
		byte [] initBytes = new byte[numBytesToGet];
		for (int i = 0; i < numBytesToGet; i++) {
			initBytes[i] = allBytes[i];
		}
		return initBytes;
	}
	
	/**
	 * Takes a series of bytes (Assuming in-order digits) and converts them to a normal integer for other simple uses.
	 * Currently works with 2 bytes specifically, but more can be added to the method later.
	 * @param bytesToConvertToNum The bytes to convert to a number
	 */
	public static int convertToNum(byte [] bytesToConvertToNum) {
		int number = Byte.toUnsignedInt(bytesToConvertToNum[0]);
		number <<= 8;
		number += Byte.toUnsignedInt(bytesToConvertToNum[1]);
		return number;
	}
	
	/**
	 * Takes a number that was originally derived from bytes (Or an edited version), and turns it back into (In-order) bytes again.
	 * @param numToConvertToBytes The number to be converted to bytes
	 * @param numBytesToMake How many bytes to convert the number into; currently 2 is supported, but more can be added later
	 */
	public static byte [] convertToIndividualBytes(int numToConvertToBytes, int numBytesToMake) {
		byte [] bytes = new byte[numBytesToMake];
		if (numBytesToMake == 2) {
			int num1 = (numToConvertToBytes >> 8);
			int num2 = (numToConvertToBytes & 0x000000FF);
			bytes[0] = (byte) num1;
			bytes[1] = (byte) num2;
		}
		return bytes;
	}
	
	/**
	 * Concatenate two groups of bytes together.
	 * @param initBytes The bytes to put at the beginning
	 * @param endBytes The bytes to put at the end
	 */
	public static byte [] concatBytes(byte [] initBytes, byte [] endBytes) {
		int numInitBytes = initBytes.length;
		byte [] allBytes = new byte[endBytes.length + numInitBytes];
		for (int i = 0; i < numInitBytes; i++) {
			allBytes[i] = initBytes[i];
		}
		for (int i = numInitBytes; i < allBytes.length; i++) {
			allBytes[i] = endBytes[i - numInitBytes];
		}
		return allBytes;
	}
	/**
	 * Concatenate two groups of bytes together.
	 * @param initBytes The bytes to put at the beginning
	 * @param endBytes The bytes to put at the end
	 */
	public static byte [] concatBytes(byte [] initBytes, ArrayList<Byte> endBytes) {
		int numInitBytes = initBytes.length;
		byte [] allBytes = new byte[endBytes.size() + numInitBytes];
		for (int i = 0; i < numInitBytes; i++) {
			allBytes[i] = initBytes[i];
		}
		for (int i = numInitBytes; i < allBytes.length; i++) {
			allBytes[i] = endBytes.get(i - numInitBytes);
		}
		return allBytes;
	}
	
	
	//For Testing
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int testNumToConvert = 0x415F;
		byte [] testConversion = convertToIndividualBytes(testNumToConvert, 2);
		System.out.println("Byte1: " + testConversion[0] + "\nByte2: " + testConversion[1]);

	}

}
