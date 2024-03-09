package Branch;

import java.util.HashMap;
import java.util.Vector;

public class branch {
    public static int JMP(Vector<String> word) {
        if (word.size() != 1 || word.get(1).length() != 4) {
            System.out.println("Invalid Address!");
            return -1;
        }
        return 3;
    }

    public static int JNZ(Vector<String> word, HashMap<String, Integer> flag) {
        if (word.size() != 2 || word.get(1).length() != 4) {
            System.out.println("Invalid Instruction");
            return -1;
        }
        if (flag.get("Z") == 1) {
            return 3;
        }
        return 2;
    }
}
