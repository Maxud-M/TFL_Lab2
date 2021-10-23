package ru.evistix28;

import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SOF {//System Of Equations

    public static final char[] variables = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static final char[] letters =  "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public static boolean contains(char[] array, char symbol) {
        for(int i = 0; i < array.length; ++i) {
            if(array[i] == symbol) {
                return true;
            }
        }
        return false;
    }


    public static String IterationKleene(String str) {
        String res = "";
        if(str.charAt(0) != '(' && str.length() != 1) {
            res += "(" + str + ")*";
        } else {
            res += str + "*";
        }
        return res;
    }

    ArrayList<Equation> equations;


    private void substition(int i, int j) {
        Equation X = equations.get(i);
        Equation Y = equations.get(j);
        if(Y.equation.containsKey(X.nameOfUnknown)) {
            String coeffXinY = Y.equation.get(X.nameOfUnknown);
            String coeff = coeffXinY + IterationKleene(X.coeffOfUnknown);
            for(Map.Entry<Character, String> entryX: X.equation.entrySet()) {
                if(entryX.getKey() == Y.nameOfUnknown) {
                    Y.coeffOfUnknown +=  "+" + coeff + entryX.getValue();
                    continue;
                }
                if(Y.equation.containsKey(entryX.getKey())) {
                    String newCoeff = Y.equation.get(entryX.getKey());
                    Y.equation.put(entryX.getKey(), newCoeff + "+" + coeff + entryX.getValue());
                } else {
                    Y.equation.put(entryX.getKey(), coeff + entryX.getValue());
                }
            }
            equations.set(j, Y);
        }
        return;
    }

    public void solution() {
        for(int i = 0; i < equations.size(); ++i) {
            for(int j = i + 1; j < equations.size(); ++j) {
                substition(i, j);
            }
        }
        for(int i = equations.size() - 1; i >= 0; --i) {
            for(int j = i - 1; j >= 0; --j) {
                substition(i, j);
            }
        }
        for(int i = 0; i < equations.size(); ++i) {
            System.out.println(equations.get(i).getSolution());
        }
    }



    public class Equation { //X = aX + b
        char nameOfUnknown; //X
        HashMap<Character, String> equation;
        String coeffOfUnknown;



        public Equation(String eq) {
            equation = new HashMap<>();
            eq.replaceAll(" ", "");
            eq.replaceAll("\n", "");
            int itr = 0;
            while(!contains(variables, eq.charAt(itr))) {
                itr++;
            }
            nameOfUnknown = eq.charAt(itr);
            itr += 2;
            int startOfCoeff = itr;
            int endOfCoeff = 0;
            while(itr < eq.length()) {
                while(!contains(variables, eq.charAt(itr)) && itr != eq.length()) {
                    itr++;
                }
                endOfCoeff = itr;
                if(eq.charAt(itr) == nameOfUnknown) {
                    coeffOfUnknown = eq.substring(startOfCoeff, endOfCoeff);
                    itr += 2;
                    continue;
                }
                if(itr == eq.length()) {
                    equation.put('\0', eq.substring(startOfCoeff, endOfCoeff));
                } else {
                    equation.put(eq.charAt(itr), eq.substring(startOfCoeff, endOfCoeff));
                }
                itr += 2;
            }

        }



        private String getEquation() {
            String res = "";

            for(Map.Entry<Character, String> entry: equation.entrySet()) {
                res += entry.getValue() + entry.getKey();
                res += "+";
            }
            res = res.substring(0, equation.size() - 1);
            return res;
        }


        public String getSolution() {
            String solution = IterationKleene(coeffOfUnknown);
            solution += "(" + getEquation() + ")";
            return solution;
        }

    }

    public SOF(ArrayList<String> equations) {
        this.equations = new ArrayList<>(0);
        for(int i = 0; i < equations.size(); ++i) {
            this.equations.add(new Equation((equations.get(i))));
        }
    }
}
