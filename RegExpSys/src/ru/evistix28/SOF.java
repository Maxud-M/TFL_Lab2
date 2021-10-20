package ru.evistix28;

import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;

public class SOF {//System Of Equations

    public class Equation { //X = aX + b
        char nameOfVar; //X
        ArrayList<Pair<String, Character>> b;
        String a;

        public String getB() {
            String res = "";
            for(int i = 0; i < b.size(); ++i) {
                res += b.get(i).fst + b.get(i).snd;
                if(i != b.size() - 1) {
                    res += "+";
                }
            }
            return res;
        }





        public String getSolution() {
            String solution = "";
            if(a.charAt(0) != '(' && a.length() != 1) {
                solution += "(" + a + ")*";
            } else {
                solution += a + "*";
            }
            solution += "(" + getB() + ")";
            return solution;
        }

    }

    public SOF(ArrayList<String> equations) {

    }

}
