package util.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class BinaryFileCommonIO {
	
	/**
	 * Gets all bytes from a binary file.
	 * @param pathname The filename, as well as the path to the file
	 * @return All bytes within that binary file
	 */
	public static byte [] getAllBytesFromFile(String pathname) {
		File file = new File(pathname);
		byte [] bytes = new byte[(int) file.length()];
		try(FileInputStream fileInputStream = new FileInputStream(file)) {
			fileInputStream.read(bytes);
			fileInputStream.close();
		}
		catch(Exception e) {
			System.err.println("Error! Could not read from the file: " + pathname);
		}
		return bytes;
	}
	
	/**
	 * Writes all the given bytes to a binary file.
	 * @param pathname The filename, as well as the path to the file
	 */
	public static void writeAllBytesToFile(String pathname, byte [] allBytes) {
		File file = new File(pathname);
		try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			fileOutputStream.write(allBytes);
			fileOutputStream.flush();
			fileOutputStream.close();
		}
		catch(Exception e) {
			System.err.println("Error! Could not write to the file: " + pathname);
		}
	}
	
	/**
	 * Gets all bytes from the original bytes of a file (i.e. address 0x0 to the end of the file),
	 * but only from the given address to the end of the file.
	 * @param allBytes All of the original bytes from a file
	 * @param addressInDecimal Where to start the byte extraction from to obtain the subsection of bytes
	 * @return The bytes from the given address to the end of the file
	 */
	public static byte [] getBytesAtAddress(byte [] allBytes, int addressInDecimal) {
		byte [] bytes = new byte[allBytes.length - addressInDecimal];
		for (int i = addressInDecimal; i < allBytes.length; i++) {
			bytes[i - addressInDecimal] = allBytes[i];
		}
		return bytes;
	}
	
	/**
	 * Gets all bytes from the original bytes of a file (i.e. address 0x0 to the end of the file),
	 * but only from the given address to either the end of the file, or as many bytes as given
	 * per the "length" parameter.
	 * @param allBytes All of the original bytes from a file
	 * @param addressInDecimal Where to start the byte extraction from to obtain the subsection of bytes
	 * @param length How many bytes to extract in total
	 * @return The bytes from the given address to the last byte, given by the length
	 */
	public static byte [] getBytesAtAddressWithLen(byte [] allBytes, int addressInDecimal, int length) {
		byte [] bytes = new byte[length];
		for (int i = addressInDecimal; i < (addressInDecimal+length); i++) {
			bytes[i - addressInDecimal] = allBytes[i];
		}
		return bytes;
	}
	
}
