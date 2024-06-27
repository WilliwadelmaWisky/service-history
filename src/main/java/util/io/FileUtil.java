package util.io;

import java.io.*;

/**
 * Very generic static helper functions for many different use cases (File operations).
 * This class is not meant to be constructed.
 *
 * @version 18.6.2024
 */
public final class FileUtil {

    /**
     * Read all contents of the file
     * @param file A file to read
     * @return Contents of the file (Null if file doesn't exist)
     */
    public static String read(File file) {
        if (file == null || !file.exists())
            return null;

        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return stringBuilder.toString();
    }

    /**
     * Read all contents of the file
     * @param path A path of the file to read
     * @return Contents of the file (Null if file doesn't exist)
     */
    public static String read(String path) {
        File file = new File(path);
        return read(file);
    }


    /**
     * Write a string to a file (Overrides any previous contents)
     * @param file A file to write to
     * @param data A string to write
     * @return True or False depending on the success of the operation
     */
    public static boolean write(File file, String data) {
        if (file == null || data == null)
            return false;

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(data);
        } catch(IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return true;
    }

    /**
     * Write a string to a file (Overrides any previous contents)
     * @param path A path of the file to write to
     * @param data A string to write
     * @return True or False depending on the success of the operation
     */
    public static boolean write(String path, String data) {
        File file = new File(path);
        return write(file, data);
    }


    /**
     * Get name of the file without extension (name.txt -> name)
     * @param file A file to get the name of
     * @return The name of the file
     */
    public static String getNameWithoutExtension(File file) {
        String fileName = file.getName();
        return getNameWithoutExtension(fileName);
    }

    /**
     * Get name of the file without extension (name.txt -> name)
     * @param fileName A filename (not a path)
     * @return The name of the file
     */
    public static String getNameWithoutExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }

    /**
     * Get an extension of a file (name.text -> txt)
     * @param file A file to get the extension of
     * @return The extension of the file
     */
    public static String getExtension(File file) {
        String fileName = file.getName();
        return getExtension(fileName);
    }

    /**
     * Get an extension of a file (name.text -> txt)
     * @param fileName A filename (not a path)
     * @return The extension of the file
     */
    public static String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? null : fileName.substring(dotIndex + 1);
    }
}
