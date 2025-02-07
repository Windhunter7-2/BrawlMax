package util.files;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ImageFileIO {
	
	/**
	 * Calculates the number of bytes, kilobytes, and megabytes of the given bytes, and converts
	 * these into the most appropriate display String to say what the filesize is. It matches the
	 * format that Windows uses for standard filesize.
	 * @param bytes The bytes to get the filesize of
	 * @return A String saying what the total filesize would be
	 */
	public static String evaluateFilesize(byte [] bytes) {
		//Calculate bytes, kilobytes, & megabytes
		int numBytes = bytes.length;
		double numKb = (numBytes / 1024.0);
		double numMb = (numKb / 1024.0);
		
		//List in order of size
		String numBytesAsString = NumberFormat.getIntegerInstance().format(numBytes);
		if (numMb >= 1.0) {	//If enough Megabytes
			String numMbAsString = new DecimalFormat("#0.0").format(numMb);
			return (numMbAsString + " MB (" + numBytesAsString + " bytes)");
		}
		else if (numKb >= 1.0) {	//If enough Kilobytes
			String numKbAsString = new DecimalFormat("#0.0").format(numKb);
			return (numKbAsString + " KB (" + numBytesAsString + " bytes)");
		}
		return (numBytesAsString + " bytes (" + numBytesAsString + " bytes)");
	}
	
	/**
	 * Exports an image file with the given data and filename.
	 * @param bytes The bytes to export to the file
	 * @param outputFolder Where to output the file to
	 * @param outputFilename The filename, including the extension (e.g. "...png"), of the output file
	 */
	public static void exportImageFile(byte [] bytes, String outputFolder, String outputFilename) {
		String fullFilename = (outputFolder + File.separator + outputFilename);
		File file = new File(outputFolder);
		if (!file.exists()) {
		    file.mkdirs();
		}
		try (FileOutputStream fileOutputStream = new FileOutputStream(fullFilename)) {
			fileOutputStream.write(bytes);
		}
		catch (Exception e) {
			System.err.println("Error! Could not export image file " + outputFilename + " properly!");
		}
	}

}
