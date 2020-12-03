package de.smartformer.articlesapi.cucumber.commonsit;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Pattern;

public class FileParser {

    public static String readFromPointer(String fileName, long pointer) {
        try (RandomAccessFile file = new RandomAccessFile(fileName, "r")) {
            // Move the cursor in the file
            file.seek(pointer);
            byte[] arr = new byte[(int) file.length() - (int) pointer];
            file.readFully(arr);
            return new String(arr);

        } catch (IOException ex) {
            ex.printStackTrace();
            return ex.getMessage().toString();
        }
    }

    public static String readFromPointer(String fileName) {
        return FileParser.readFromPointer(fileName, 0);
    }

    public static boolean isStringMatch(String allText, String targetText) {

        allText = allText.replaceAll("\\[|\\]", "");
        targetText = targetText.replaceAll("\\[|\\]", "");
        Pattern p = Pattern.compile(targetText, Pattern.MULTILINE);

        return p.matcher(allText).find();
    }
}
