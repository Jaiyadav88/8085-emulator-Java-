package RunMode;

import Instructions.Instructions;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * run
 */
public class run {
    static int start = 0;

    public static void execute(ArrayList<String> instructions, HashMap<String, Integer> flag,
            HashMap<Integer, Integer> memory, HashMap<String, Integer> reg) {
        System.out.println("Enter the Starting Address:");
        Scanner sc = new Scanner(System.in);
        start = sc.nextInt();
        Instructions ob = new Instructions();
        for (int i = 0; i < instructions.size(); i++) {
            int next_address = 0;
            Vector<String> wd = new Vector<>();
            StringTokenizer st = new StringTokenizer(instructions.get(i), " ,");
            while (st.hasMoreTokens()) {
                String s = st.nextToken();
                wd.add(s);
            }
            if (wd.get(0).compareTo("MOV") == 0) {
                next_address = ob.MOV(wd, reg);
            } else if (wd.get(0).compareTo("MVI") == 0) {
                next_address = ob.MVI(wd, reg);
            } else if (wd.get(0).compareTo("ADD") == 0) {
                next_address = Instructions.ADD(wd, flag, reg);
            } else if (wd.get(0).compareTo("HLT") == 0) {
                next_address = 1;
                break;
            } else if (wd.get(0).compareTo("LXI") == 0) {
                next_address = ob.LXI(wd, reg, memory);
            } else if (wd.get(0).compareTo("LDA") == 0) {
                next_address = ob.LDA(wd, reg, memory);
            } else if (wd.get(0).compareTo("STA") == 0) {
                next_address += ob.STA(wd, reg, memory);
            } else if (wd.get(0).compareTo("SUB") == 0) {
                next_address += Instructions.SUB(wd, flag, reg);
            } else {
                System.err.println("Invalid Input program Terminated!");
                break;
            }
            System.out.println(start + ":" + instructions.get(i));
            start += next_address;
        }
        print_architecture(reg, flag, memory);
        sc.close();
    }

    public static void print_architecture(HashMap<String, Integer> reg, HashMap<String, Integer> flag,
            HashMap<Integer, Integer> memory) {
        // Print Register values
        System.out.println("+----------------------------------+");
        System.out.println("| A  | B  | C  | D  | E  | H  | L  |");
        System.out.println("+---------------------------------");
        System.out.printf("| %02d | %02d | %02d | %02d | %02d | %02d | %02d |\n",
                reg.get("A"), reg.get("B"), reg.get("C"), reg.get("D"), reg.get("E"), reg.get("H"), reg.get("L"));
        System.out.println("----------------------------------+");
        System.out.println();

        // Print Flag Register values
        System.out.println("Flag Register");
        System.out.println("------------------------");
        System.out.println("| S  | Z | AC | P | CY |");
        System.out.println("------------------------");
        System.out.printf("| %d  | %d | %d  | %d | %d  |\n",
                flag.get("S"), flag.get("Z"), flag.get("AC"), flag.get("P"), flag.get("CY"));
        System.out.println("------------------------");
        System.out.println();

        // Print Memory contents
        if (!memory.isEmpty()) {
            System.out.println("Memory Address : Value at Address");
            for (Map.Entry<Integer, Integer> e : memory.entrySet()) {
                System.out.printf("%dX : %d\n", e.getKey(), e.getValue());
            }
        }
    }

}
