import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        BitStream stream = new BitStream(16000);

        System.out.println("Please select an option:");
        System.out.println("1- Encode");
        System.out.println("2- Encode Text File");
        System.out.println("3- Decode");
        System.out.print("> ");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.equals("1")) {
            System.out.print("String to encode: ");
            String stringToEncode = scanner.nextLine();
            System.out.print("Output file name: ");
            String outputFilename = scanner.nextLine();

            HuffmanEncoder.encode(stringToEncode, stream);

            try {
                stream.writeToFile(outputFilename);
            } catch (IOException ex) {
            }

        } else if (input.equals("2")) {
            System.out.print("Text File to Encode: ");
            String inputFilename = scanner.nextLine();

            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(inputFilename));
    
                String line = br.readLine();
                while (line != null) {
                    sb.append(line).append("\n");
                    line = br.readLine();
                }
                br.close();
            } catch (IOException ex) {
            }

            String stringToEncode = sb.toString();
            System.out.print("Output file name: ");
            String outputFilename = scanner.nextLine();

            HuffmanEncoder.encode(stringToEncode, stream);

            try {
                stream.writeToFile(outputFilename);
            } catch (IOException ex) {
            }

        } else if (input.equals("3")) {
            System.out.print("name of the file to decode: ");
            String inputFilename = scanner.nextLine();


            try {
                stream.readFromFile(inputFilename);
            } catch (IOException ex) {
            }

            String decodedString = HuffmanDecoder.decode(stream);
            System.out.print("decoded string: " + decodedString);
        }
        scanner.close();
    }

}
