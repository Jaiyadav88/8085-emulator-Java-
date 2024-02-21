package Instructions;

import java.util.HashMap;
import java.util.Vector;

public class Instructions {
    public int MOV(Vector<String> word, HashMap<String, Integer> reg) {
        if (word.get(0).length() != 3 || reg.containsKey(word.get(1)) == false ||
                reg.containsKey(word.get(2)) == false) {
            System.out.println("Invalid Instruction!");
            System.out.println("Sorry!");
            return -1;
        }
        int data2 = reg.get(word.get(2));
        reg.put(word.get(1), data2);
        // System.out.println("The value of Register" + word.get(1) + " is " +
        // reg.get(word.get(1)));
        // System.out.println("The value of Register" + word.get(2) + " is " +
        // reg.get(word.get(2)));
        return 1;
    }

    public int MVI(Vector<String> word, HashMap<String, Integer> reg) {
        if (word.size() != 3 || word.get(0).compareTo("MVI") != 0) {
            System.out.println("Error while using MVI");
            return -1;
        }
        if (reg.containsKey(word.get(1)) == false || word.get(2).length() > 2 || Hex(word.get(2)) == false) {
            System.out.println("Invalid Instruction!");
            System.out.println("Sorry!");
            return -1;
        }
        String r = word.get(1);
        int val = Integer.valueOf(word.get(2));
        reg.put(r, val);
        return 2;
    }

    public boolean Hex(String dec) {
        for (char ch : dec.toCharArray()) {
            if (!ishex(ch))
                return false;
        }
        return true;
    }

    public boolean ishex(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }

    public static int ADD(Vector<String> word, HashMap<String, Integer> flag, HashMap<String, Integer> reg) {
        if (word.size() != 2) {
            System.out.println("Invalid Input!");
            return -1;
        }
        if (reg.containsKey(word.get(1)) == false || word.get(1).length() != 1) {
            System.out.println("Invalid Instruction!");
            System.out.println("Sorry!");
            return -1;
        }
        int val = reg.get("A") + reg.get(word.get(1));
        reg.put("A", val & 0xFF);
        flag.put("CY", (val > 0xff) ? 1 : 0);
        flag.put("Z", (reg.get("A") == 0) ? 1 : 0);
        flag.put("S", ((val & 0x80) == 1) ? 1 : 0);
        return 1;
    }

    public int LXI(Vector<String> word, HashMap<String, Integer> reg, HashMap<Integer, Integer> memory) {
        String add = word.get(2);
        if (word.size() != 3 || word.get(2).length() != 4) {
            System.out.println("Invalid Input!");
            return -1;
        }
        while (add.length() < 4)
            add = "0" + add;
        if (word.get(1).equals("B")) {
            int val = Integer.parseInt(add.substring(0, 2));
            reg.put("B", val);
            val = Integer.parseInt(add.substring(2));
            reg.put("C", val);
        } else if (word.get(1).equals("D")) {
            int val = Integer.parseInt(add.substring(0, 2));
            reg.put("D", val);
            val = Integer.parseInt(add.substring(2));
            reg.put("E", val);
        } else if (word.get(1).equals("H")) {
            int val = Integer.parseInt(add.substring(0, 2));
            reg.put("H", val);
            val = Integer.parseInt(add.substring(2));
            reg.put("L", val);
        } else {
            System.out.println("Invalid Input!");
            return -1;
        }
        return 3;
    }

    public int LDA(Vector<String> word, HashMap<String, Integer> reg, HashMap<Integer, Integer> memory) {
        if (word.size() != 2 || word.get(1).length() != 4) {
            System.out.println("Invalid Input!");
            return -1;
        }
        if (memory.containsKey(Integer.parseInt(word.get(1))) == false) {
            memory.put(Integer.parseInt(word.get(1)), 0);
            reg.put("A", 0);
        } else {
            reg.put("A", memory.get(Integer.parseInt(word.get(1))));
        }
        return 3;
    }

    public int STA(Vector<String> word, HashMap<String, Integer> reg, HashMap<Integer, Integer> memory) {
        if (word.size() != 2 || word.get(1).length() != 4) {
            System.out.println("Invalid Input!");
            return -1;
        }
        memory.put(Integer.parseInt(word.get(1)), reg.get("A"));
        return 3;
    }

    public static int SUB(Vector<String> word, HashMap<String, Integer> flag, HashMap<String, Integer> reg) {
        if (word.size() != 2) {
            System.out.println("Invalid Input!");
            return -1;
        }
        if (reg.containsKey(word.get(1)) == false || word.get(1).length() != 1) {
            System.out.println("Invalid Instruction!");
            System.out.println("Sorry!");
            return -1;
        }
        int val = reg.get("A") - reg.get(word.get(1));
        reg.put("A", val & 0xFF);
        flag.put("CY", (val > 0xff) ? 1 : 0);
        flag.put("Z", (reg.get("A") == 0) ? 1 : 0);
        flag.put("S", (val < 0) ? 1 : 0);
        return 1;
    }
}
