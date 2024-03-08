package Programcounter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;
import java.util.Vector;

public class Programcounter {

    public static int MOV() {
        return 1;
    }

    public static int MVI() {
        return 2;
    }

    public static int ADD() {
        return 1;
    }

    public static int LXI() {
        return 3;
    }

    public static int LDA() {
        return 3;
    }

    public static int SUB() {
        return 1;
    }

    public static int LHLD() {
        return 3;
    }

    public static int SHLD() {
        return 3;
    }

    public static int STAX() {
        return 1;
    }

    public static int XCHG() {
        return 1;
    }

    public static int ADI() {
        return 2;
    }

    public static int INR() {
        return 1;
    }

    public static int DCR() {

        return 1;
    }

    public static int CMA() {
        return 1;
    }

    public static int CMP() {
        return 1;
    }

    public static int JMP() {
        return 3;
    }

    public static int STA() {
        return 3;
    }

    public static int INX() {
        return 1;
    }

    public static int HLT() {
        return 1;
    }

    public static int SUI() {
        return 1;
    }

    public static int JNZ() {
        return 2;
    }

    public static void memorymap(ArrayList<String> inst, LinkedHashMap<String, Integer> pc, int start,
            LinkedHashMap<Integer, String> mp) {
        for (int i = 0; i < inst.size(); i++) {
            int next_address = 0;
            Vector<String> wd = new Vector<>();
            StringTokenizer st = new StringTokenizer(inst.get(i), " ,");
            while (st.hasMoreTokens()) {
                String s = st.nextToken();
                wd.add(s);
            }
            if (wd.get(0).compareTo("MOV") == 0) {
                next_address = MOV();
            } else if (wd.get(0).compareTo("MVI") == 0) {
                next_address = MVI();
            } else if (wd.get(0).compareTo("ADD") == 0) {
                next_address = ADD();
            } else if (wd.get(0).compareTo("LXI") == 0) {
                next_address = LXI();
            } else if (wd.get(0).compareTo("LDA") == 0) {
                next_address = LDA();
            } else if (wd.get(0).compareTo("STA") == 0) {
                next_address = STA();
            } else if (wd.get(0).compareTo("SUB") == 0) {
                next_address = SUB();
            } else if (wd.get(0).equals("LHLD")) {
                next_address = LHLD();
            } else if (wd.get(0).equals("STAX")) {
                next_address = STAX();
            } else if (wd.get(0).equals("XCHG")) {
                next_address = XCHG();
            } else if (wd.get(0).equals("ADI")) {
                next_address = ADI();
            } else if (wd.get(0).equals("INR")) {
                next_address = INR();
            } else if (wd.get(0).equals("SHLD")) {
                next_address = SHLD();
            } else if (wd.get(0).equals("DCR")) {
                next_address = DCR();
            } else if (wd.get(0).equals("INX")) {
                next_address = INX();
            } else if (wd.get(0).equals("CMA")) {
                next_address = CMA();
            } else if (wd.get(0).equals("CMP")) {
                next_address = CMP();
            } else if (wd.get(0).equals("JMP")) {
                next_address = JMP();
            } else if (wd.get(0).equals("HLT")) {
                next_address = HLT();
            } else if (wd.get(0).equals("JNZ")) {
                next_address = JNZ();
            } else if (wd.get(0).equals("SUI")) {
                next_address = SUI();
            }
            pc.put(Integer.toString(start), i);
            mp.put(start, inst.get(i));
            start += next_address;
        }
    }

}
