import RunMode.run;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
                        System.out.println("Enter the 8085 Assembley Program");
                        BufferedWriter rd = new BufferedWriter(new FileWriter("./test.txt"));
                        Scanner sc = new Scanner(System.in);
                        String s = "";
                        while (true) {
                                s = sc.nextLine();
                                if (s.equals("HLT")) {
                                        rd.write(s);
                                        break;
                                }
                                rd.write(s);
                                rd.newLine();
                        }
                        rd.close();
                        BufferedReader br = new BufferedReader(new FileReader("./test.txt"));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                                instructions.add(line);
                        }
                        System.out.println(
                                        "            _                                                                ___   ___   ___ ____  ");
                        System.out.println(
                                        "  _ __ ___ (_) ___ _ __ ___  _ __  _ __ ___   ___ ___  ___ ___  ___  _ __   ( _ ) / _ \\ ( _ ) ___| ");
                        System.out.println(
                                        " | '_ ` _ \\| |/ __| '__/ _ \\| '_ \\| '__/ _ \\ / __/ _ \\/ __/ __|/ _ \\| '__|  / _ \\| | | |/ _ \\___ \\ ");
                        System.out.println(
                                        " | | | | | | | (__| | | (_) | |_) | | | (_) | (_|  __/\\__ \\__ \\ (_) | |    | (_) | |_| | (_) |__) |");
                        System.out.println(
                                        " |_| |_| |_|_|\\___|_|  \\___/| .__/|_|  \\___/ \\___\\___||___/___/\\___/|_|     \\___/ \\___/ \\___/____/ ");
                        System.out.println(
                                        "                            |_|                                                                    ");
                        char ch;
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