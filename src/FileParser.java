import java.io.*;
import java.util.*;
/**
 * this class contains all help funtion to read and writ to files
 */
public class FileParser {
/**
 * @param filePath
 * -read each line and add each it to a list 
 * it can read currentUserAccounts, login files, available tickets
 * @return a queue
 */
  public static Queue<String> readFile(String filePath) {
    try {
      
      FileReader fileReader = new FileReader(filePath);
      BufferedReader bufferReader = new BufferedReader(fileReader);
      String readLine;
      Queue<String> commandQueue = new LinkedList();

      // while file is not empty
      while ((readLine = bufferReader.readLine()) != null) {
        // read next command and add to a queue
        commandQueue.add(readLine);
      }

      bufferReader.close();
      fileReader.close();

      return commandQueue;

    } catch (IOException e) {
      System.out.println("file cannot be read");
    }
    return null;
  }

  /**
   * @param fileName, fileContent 
   * - write given filename the fileContent
   * return nothing 
   */
  public static void writeFile(String fileName, String fileContent,boolean append) {
    try {
      FileWriter fileWriter = new FileWriter(("./" + fileName), append);
      BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
      bufferWriter.write(fileContent + "\n");
      bufferWriter.close();
      fileWriter.close();
    } catch (IOException e) {
      System.out.println("cannot write file sorry");
    }
  }

}