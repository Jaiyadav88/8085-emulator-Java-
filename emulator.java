import RunMode.run;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * emulator
 */
public class emulator {
    // register pair
    static HashMap<String, Integer> register = new HashMap<>();
    // flag regiters
    static HashMap<String, Integer> flag = new HashMap<>();
    // memory mapping
    static HashMap<Integer, Integer> memory = new HashMap<>();

    public static void main(String[] args) throws IOException {
        // initialize register pairs
        register.put("A", 0);
        register.put("B", 0);
        register.put("C", 0);
        register.put("D", 0);
        register.put("E", 0);
        register.put("H", 0);
        register.put("L", 0);

        // initialize flag register
        flag.put("S", 0);
        flag.put("Z", 0);
        flag.put("AC", 0);
        flag.put("P", 0);
        flag.put("CY", 0);
        try {
            ArrayList<String> instructions = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader("./test.txt"));
            String line = "";
            while ((line = br.readLine()) != null) {
                instructions.add(line);
            }
            System.out.println("+------------------------------------------------------------+");
            System.out.println("|                                                            |");
            System.out.println("-------------------Welcome to 8085 Emulator-------------------");
            System.out.println("|                                                            |");
            System.out.println("+------------------------------------------------------------+");
            char ch;
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the mode you want to select\nR-Run Mode\nD-Debugger Mode");
            ch = sc.next().charAt(0);
            switch (ch) {
                case 'R':
                    run.execute(instructions, flag, memory, register);
                    break;
            }
            br.close();
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file!");
        }

    }

}