package implementations;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import utilities.MyStack;
import utilities.MyQueue;
import exceptions.EmptyStackException;

public class XMLParser {
    private MyStack<String> stack;
    private MyQueue<String> errors;

    public XMLParser() {
        stack = new MyStack<>();
        errors = new MyQueue<>();
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java XMLParser <filename.xml>");
            return;
        }

        XMLParser parser = new XMLParser();
        parser.parse(args[0]);
    }

    public void parse(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            int lineNum = 0;

            while (scanner.hasNextLine()) {
                lineNum++;
                String line = scanner.nextLine().trim();

                int pos = 0;
                while ((pos = line.indexOf("<", pos)) != -1) {
                    int endPos = line.indexOf(">", pos);
                    if (endPos == -1) break; 

                    String fullTag = line.substring(pos + 1, endPos);// Skip XML declarations
                    if (!fullTag.startsWith("?") && !fullTag.startsWith("!")) {
                        processTag(fullTag, lineNum);
                    }

                    pos = endPos + 1;
                }
            }

            // check for unclosed tags
            while (!stack.isEmpty()) {
                errors.enqueue("Error: Unclosed tag <" + stack.pop() + ">");
            }

            printErrors();

        } catch (FileNotFoundException e) {
            System.err.println("Could not find file: " + filePath);
        } catch (EmptyStackException e) {
            System.err.println("Stack error occurred during parsing.");
        }
    }

    private void processTag(String tagContent, int lineNum) throws EmptyStackException {
  
        String tagName = tagContent.split(" ")[0];

        if (tagName.startsWith("/")) {
          
            String actualName = tagName.substring(1);
            if (stack.isEmpty()) {
                errors.enqueue("Error at line " + lineNum + ": Closing tag </" + actualName + "> has no opening tag.");
            } else {
                String openTag = stack.pop();
                if (!openTag.equals(actualName)) {
                    errors.enqueue("Error at line " + lineNum + ": Mismatched tags. Expected </" + openTag + ">, but found </" + actualName + ">");
                }
            }
        } else if (tagName.endsWith("/")) {
 
        } else {
 
            stack.push(tagName);
        }
    }

    private void printErrors() {
        if (errors.isEmpty()) {
            System.out.println("XML Parsing complete: No errors found.");
        } else {
            System.out.println("XML Errors Found:");
            while (!errors.isEmpty()) { 
                System.out.println(errors.dequeue());
            }
        }
    }
}