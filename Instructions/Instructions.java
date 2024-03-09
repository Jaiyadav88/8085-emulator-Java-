package Instructions;

import java.util.HashMap;
import java.util.Vector;

public class Instructions {
    public static int MOV(Vector<String> word, HashMap<String, Integer> reg, HashMap<Integer, Integer> memory) {
        // indirect memory
        if (word.get(2).equals("M")) {
            int low = reg.get("H");
            int high = reg.get("L");
            int add = low * 100 + high;
            reg.put(word.get(1), memory.get(add));
            return 1;
        }
        if (word.get(0).length() != 3 || reg.containsKey(word.get(1)) == false ||
                reg.containsKey(word.get(2)) == false) {
            System.err.println("Invalid instruction during execution of MOV");
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

    public static int MVI(Vector<String> word, HashMap<String, Integer> reg) {
        if (word.size() != 3 || word.get(0).compareTo("MVI") != 0) {
            System.out.println("Error while using MVI");
            return -1;
        }
        if (reg.containsKey(word.get(1)) == false || word.get(2).length() > 2 || Hex(word.get(2)) == false) {
            System.err.println("Invalid instruction during execution of MVI");
            System.out.println("Sorry!");
            return -1;
        }
        String r = word.get(1);
        int val = Integer.valueOf(word.get(2));
        reg.put(r, val);
        return 2;
    }

    public static boolean Hex(String dec) {
        for (char ch : dec.toCharArray()) {
            if (!ishex(ch))
                return false;
        }
        return true;
    }

    public static boolean ishex(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }

    public static int ADD(Vector<String> word, HashMap<String, Integer> flag, HashMap<String, Integer> reg,
            HashMap<Integer, Integer> memory) {
        if (word.size() != 2 || word.get(1).length() != 1) {
            System.err.println("Invalid instruction during execution of ADD");
            return -1;
        }

        if (reg.containsKey(word.get(1)) == false && !word.get(1).equals("M")) {
            System.err.println("Invalid instruction during execution of ADD");
            System.out.println("Sorry!");
            return -1;
        }
        int val = 0;
        if (word.get(1).equals("M")) {
            int low = reg.get("H");
            int high = reg.get("L");
            int add = low * 100 + high;
            val = memory.get(add) + reg.get("A");
        } else
            val = reg.get("A") + reg.get(word.get(1));
        String s = Integer.toBinaryString(val);
        int ones = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '1')
                ones++;
        }
        if (ones % 2 == 0)
            flag.put("P", 1);
        reg.put("A", val & 0xFF);
        flag.put("AC", ((val & 0x0F) + 1) > 0x0F ? 1 : 0);
        flag.put("CY", (val > 0xFF) ? 1 : 0);
        flag.put("Z", (reg.get("A") == 0) ? 1 : 0);
        flag.put("S", ((val & 0x80) == 1) ? 1 : 0);
        return 1;
    }

    public static int LXI(Vector<String> word, HashMap<String, Integer> reg, HashMap<Integer, Integer> memory) {
        if (word.size() != 3 || word.get(2).length() != 4) {
            System.err.println("Invalid instruction during execution of LXsI");
            return -1;
        }
        String add = word.get(2);
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
            System.err.println("Invalid instruction during execution of LXI");
            return -1;
        }
        return 3;
    }

    public static int LDA(Vector<String> word, HashMap<String, Integer> reg, HashMap<Integer, Integer> memory) {
        if (word.size() != 2 || word.get(1).length() != 4) {
            System.err.println("Invalid instruction during execution of LDA");
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

    public static int STA(Vector<String> word, HashMap<String, Integer> reg, HashMap<Integer, Integer> memory) {
        if (word.size() != 2 || word.get(1).length() != 4) {
            System.err.println("Invalid instruction during execution of STA");
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
            System.err.println("Invalid instruction during execution of SUB");
            System.out.println("Sorry!");
            return -1;
        }
        int val = reg.get("A") - reg.get(word.get(1));
        String s = Integer.toBinaryString(val);
        int ones = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '1')
                ones++;
        }
        if (ones % 2 == 0)
            flag.put("P", 1);
        reg.put("A", Math.abs(val & 0xFF));
        flag.put("AC", ((val & 0x0F) - 1) > (reg.get("A") & 0x0F) ? 0 : 1);
        flag.put("CY", (val > 0xff) ? 1 : 0);
        flag.put("Z", (reg.get("A") == 0) ? 1 : 0);
        flag.put("S", (val < 0) ? 1 : 0);
        return 1;
    }

    public static int LHLD(Vector<String> word, HashMap<String, Integer> flag, HashMap<String, Integer> reg,
            HashMap<Integer, Integer> memory) {
        if (word.size() != 2) {
            System.err.println("Invalid instruction during execution of LHLD");
            return -1;
        }
        String add = word.get(1);
        int address = Integer.parseInt(add);
        int num1 = memory.get(address);
        if (address <= 65535) {
            reg.put("L", num1);
            address += 1;
            reg.put("H", memory.get(address));
        } else {
            System.out.println("Address is exceeding FFFF");
        }
        return 3;
    }

    public static int SHLD(Vector<String> word, HashMap<String, Integer> reg, HashMap<Integer, Integer> memory) {
        if (word.size() != 2 || !Hex(word.get(1))) {
            System.err.println("Invalid instruction during execution of SHLD");
            return -1;
        }
        int val1 = reg.get("H");
        int val2 = reg.get("L");
        int add = Integer.parseInt(word.get(1));
        memory.put(add, val1);
        memory.put(add + 1, val2);
        return 3;
    }

    public static int STAX(Vector<String> word, HashMap<String, Integer> reg,
            HashMap<Integer, Integer> memory) {
        if (word.size() != 2) {
            System.err.println("Invalid instruction during execution of STAX");
            return -1;
        }
        int val = reg.get("A");
        if (word.get(1) == "B") {
            String add = Integer.toString(reg.get("B")) + Integer.toString(reg.get("C"));
            int mem = Integer.parseInt(add);
            int value = memory.get(mem);
            reg.put("A", value);
        } else if (word.get(1) == "D") {
            String add = Integer.toString(reg.get("D")) + Integer.toString(reg.get("E"));
            int mem = Integer.parseInt(add);
            int value = memory.get(mem);
            reg.put("A", value);
        } else if (word.get(1) == "H") {
            String add = Integer.toString(reg.get("H")) + Integer.toString(reg.get("L"));
            int mem = Integer.parseInt(add);
            int value = memory.get(mem);
            reg.put("A", value);
        }
        return 1;
    }

    public static int XCHG(Vector<String> word, HashMap<String, Integer> reg) {
        if (word.size() != 1) {
            System.err.println("Invalid instruction during execution of XCHG");
            return -1;
        }
        int h_val = reg.get("H");
        int l_val = reg.get("L");
        int d_val = reg.get("D");
        int e_val = reg.get("E");
        reg.put("H", d_val);
        reg.put("L", e_val);
        reg.put("D", h_val);
        reg.put("E", l_val);
        return 1;
    }

    public static int ADI(Vector<String> word, HashMap<String, Integer> reg, HashMap<String, Integer> flag) {
        if (word.size() != 2 || word.get(1).length() != 2) {
            System.err.println("Invalid instruction during execution of ADI");
            return -1;
        }
        int val = reg.get("A") + Integer.parseInt(word.get(1));
        String s = Integer.toBinaryString(val);
        int ones = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '1')
                ones++;
        }
        if (ones % 2 == 0)
            flag.put("P", 1);
        reg.put("A", val & 0xff);
        flag.put("CY", (val > 0xff) ? 1 : 0);
        flag.put("Z", (reg.get("A") == 0) ? 1 : 0);
        flag.put("S", ((val & 0x80) == 1) ? 1 : 0);
        return 2;
    }

    public static int INR(Vector<String> word, HashMap<String, Integer> reg, HashMap<String, Integer> flag) {
        if (word.size() != 2 && reg.containsKey(word.get(1)) == false) {
            System.err.println("Invalid instruction during execution of INR");
            return -1;
        }
        int val = reg.get(word.get(1));
        val = val + 1;
        String s = Integer.toBinaryString(val);
        int ones = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '1')
                ones++;
        }
        if (ones % 2 == 0)
            flag.put("P", 1);
        flag.put("AC", (val & 0x0F) > 9 ? 1 : 0);
        flag.put("CY", (val > 0xff) ? 1 : 0);
        flag.put("S", (val & 0x80) == 1 ? 1 : 0);
        flag.put("Z", (val & 0xFF) == 0 ? 1 : 0);
        reg.put(word.get(1), val);
        return 1;
    }

    public static int DCR(Vector<String> word, HashMap<String, Integer> reg, HashMap<String, Integer> flag) {
        if (word.size() != 2 || reg.containsKey(word.get(1)) == false) {
            System.err.println("Invalid instruction during execution of DCR");
            return -1;
        }
        int val = reg.get(word.get(1)) - 1;
        String s = Integer.toBinaryString(val);
        int ones = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '1')
                ones++;
        }
        if (ones % 2 == 0)
            flag.put("P", 1);
        reg.put(word.get(1), val & 0xff);
        flag.put("AC", (val & 0x0F) > 9 ? 1 : 0);
        flag.put("S", (val & 0x80) == 1 ? 1 : 0);
        flag.put("Z", (val & 0xFF) == 0 ? 1 : 0);
        return 1;
    }

    public static int INX(Vector<String> word, HashMap<String, Integer> reg, HashMap<String, Integer> flag) {
        if (word.size() != 2 || (!word.get(1).equals("B") && !word.get(1).equals("D")
                && !word.get(1).equals("H"))) {
            System.err.println("Invalid instruction during execution of INX");
            return -1;
        }
        int val1 = 0;
        int regValue = 0;
        if (word.get(1).equals("B")) {
            regValue = reg.get("C");
            val1 = regValue + 1;
            reg.put("C", val1 & 0xff);
        } else if (word.get(1).equals("D")) {
            regValue = reg.get("E");
            val1 = regValue + 1;
            reg.put("E", val1 & 0xff);
        } else if (word.get(1).equals("H")) {
            regValue = reg.get("L");
            val1 = regValue + 1;
            reg.put("L", val1 & 0xff);
        }
        String s = Integer.toBinaryString(val1);
        int ones = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '1')
                ones++;
        }
        if (ones % 2 == 0)
            flag.put("P", 1);
        flag.put("AC", ((regValue & 0x0F) + 1) > 0x0F ? 1 : 0);
        flag.put("Z", (val1 == 0) ? 1 : 0);
        flag.put("S", (val1 & 0x80) == 0x80 ? 1 : 0);
        return 1;
    }

    public static int CMA(Vector<String> word, HashMap<String, Integer> reg, HashMap<String, Integer> flag) {
        if (word.size() != 1) {
            System.err.println("Invalid instruction during execution of CMA");
            return -1;
        }
        String bin = Integer.toBinaryString(reg.get("A"));
        String s = "";
        for (char ch : bin.toCharArray()) {
            if (ch == '0')
                s = s + "1";
            else
                s = s + "0";
        }
        int num = Integer.parseInt(s, 2);
        reg.put("A", num);
        return 1;
    }

    public static int CMP(Vector<String> word, HashMap<String, Integer> reg, HashMap<String, Integer> flag) {
        if (word.size() != 2 || reg.containsKey(word.get(1)) == false) {
            System.err.println("Invalid instruction during execution of CMP");
            return -1;
        }
        int diff = reg.get("A") - reg.get(word.get(1));
        String s = Integer.toBinaryString(diff);
        int ones = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '1')
                ones++;
        }
        if (ones % 2 == 0)
            flag.put("P", 1);
        flag.put("AC", (diff & 0x0F) > 9 ? 1 : 0);
        flag.put("S", diff < 0 ? 1 : 0);
        flag.put("Z", diff == 0 ? 1 : 0);
        flag.put("CY", diff < 0 ? 1 : 0);
        return 1;
    }

    public static int SUI(Vector<String> word, HashMap<String, Integer> flag, HashMap<String, Integer> reg) {
        if (word.size() != 2) {
            System.err.println("Invalid instruction during execution of SUI");
            return -1;
        }
        if (word.get(1).length() != 2) {
            System.err.println("Invalid instruction during execution of SUI");
            System.out.println("Sorry!");
            return -1;
        }
        int val = reg.get("A") - Integer.parseInt(word.get(1));
        String s = Integer.toBinaryString(val);
        int ones = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '1')
                ones++;
        }
        if (ones % 2 == 0)
            flag.put("P", 1);
        reg.put("A", Math.abs(val & 0xFF));
        flag.put("AC", ((val & 0x0F) - 1) > (reg.get("A") & 0x0F) ? 0 : 1);
        flag.put("CY", (val > 0xff) ? 1 : 0);
        flag.put("Z", (reg.get("A") == 0) ? 1 : 0);
        flag.put("S", (val < 0) ? 1 : 0);
        return 1;
    }

    public static int SET(Vector<String> word, HashMap<Integer, Integer> memory) {
        if (word.size() != 3 || word.get(1).length() != 4 || word.get(2).length() != 2) {
            System.err.println("Invalid instruction during execution of SET");
            return -1;
        }
        int add = Integer.parseInt(word.get(1));
        int val = Integer.parseInt(word.get(2));
        memory.put(add, val);
        return 3;
    }

    public static int LDAX(Vector<String> word, HashMap<String, Integer> reg, HashMap<Integer, Integer> memory) {
        if (word.size() != 2 || reg.containsKey(word.get(1)) == false) {
            System.err.println("Invalid instruction during execution of LDAX");
            return -1;
        }
        int lower_byte = 0;
        int higher_byte = 0;
        int add = 0;
        if (word.get(1).equals("B")) {
            higher_byte = reg.get("B");
            lower_byte = reg.get("C");
            add = (higher_byte * 100) + lower_byte;
            reg.put("A", memory.get(add));
        } else if (word.get(1).equals("D")) {
            higher_byte = reg.get("D");
            lower_byte = reg.get("E");
            add = (higher_byte * 100) + lower_byte;
            reg.put("A", memory.get(add));
        } else if (word.get(1).equals("H")) {
            higher_byte = reg.get("H");
            lower_byte = reg.get("L");
            add = (higher_byte * 100) + lower_byte;
            reg.put("A", memory.get(add));
        }
        return 3;
    }

    public static int ANI(Vector<String> word, HashMap<String, Integer> reg, HashMap<String, Integer> flag) {
        if (word.size() != 2 || word.get(1).length() != 2) {
            System.err.println("Invalid instruction during execution of ANI");
            return -1;
        }
        int val = Integer.parseInt(word.get(1), 16);
        int acc = reg.get("A");
        int and = acc & val;
        String s = Integer.toBinaryString(and);
        int ones = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '1')
                ones++;
        }
        if (ones % 2 == 0)
            flag.put("P", 1);
        reg.put("A", and);
        flag.put("AC", ((and & 0x0F) + 1) > 0x0F ? 1 : 0);
        flag.put("CY", (and > 0xFF) ? 1 : 0);
        flag.put("Z", (reg.get("A") == 0) ? 1 : 0);
        flag.put("S", (reg.get("A") == 0) ? 1 : 0);
        return 2;
    }

}