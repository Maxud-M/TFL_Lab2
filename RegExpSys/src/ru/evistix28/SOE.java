package ru.evistix28;

import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SOE {//System Of Equations

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
        if(str.equals("")) {
            return res;
        }
        if(str.charAt(0) != '(' && str.length() != 1) {
            res += "(" + str + ")*";
        } else {
            res += str + "*";
        }
        return res;
    }

    ArrayList<Equation> equations;


    public static String brakes(String str) {
        if(str.charAt(0) == '(' || str.split("\\+").length < 2) {
            return str;
        }
        return "(" + str + ")";
    }

    private void substition(int i, int j) {
        Equation X = equations.get(i);
        Equation Y = equations.get(j);
        if(Y.equation.containsKey(X.nameOfUnknown)) {
            String coeffXinY = Y.equation.get(X.nameOfUnknown);
            String coeff = coeffXinY + IterationKleene(X.coeffOfUnknown);
            if(X.equation.entrySet().isEmpty()) {
                if(Y.equation.containsKey('\1')) {
                    Y.equation.put('\1', Y.equation.get('\1') +  "+" + coeff);
                } else {
                    Y.equation.put('\1', coeff);
                }
            }
            for(Map.Entry<Character, String> entryX: X.equation.entrySet()) {
                if(entryX.getKey() == Y.nameOfUnknown) {
                    if(Y.coeffOfUnknown != "") {
                        Y.coeffOfUnknown += "+" + coeff + brakes(entryX.getValue());
                    } else {
                        Y.coeffOfUnknown = coeff + brakes(entryX.getValue());
                    }
                    continue;
                }
                if(Y.equation.containsKey(entryX.getKey())) {
                    String newCoeff = Y.equation.get(entryX.getKey());
                    Y.equation.put(entryX.getKey(), newCoeff + "+" + coeff + brakes(entryX.getValue()));
                } else {
                    Y.equation.put(entryX.getKey(), coeff + brakes(entryX.getValue()));
                }
            }
            Y.equation.remove(X.nameOfUnknown);
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
    public static boolean checkWrongCoeff(String coeff) {
        if(numOfBrackes(coeff) > 1) {
            return true;
        }
        if(coeff.contains("|") && !coeff.contains("(")) {
            return true;
        }
        return false;
    }

    private static int numOfBrackes(String coeff) {
        int count = 0;
        for(int i = 0; i < coeff.length(); ++i) {
            if(coeff.charAt(i) == '(') {
                count++;
            }
        }
        return count;

    }


    public class Equation { //X = aX + b
        char nameOfUnknown; //X
        HashMap<Character, String> equation;
        String coeffOfUnknown;




        public Equation(String eq) {
            coeffOfUnknown = "";
            equation = new HashMap<>();
            eq = eq.replaceAll(" ", "");
            eq = eq.replaceAll("\n", "");
            int itr = 0;
            while(!contains(variables, eq.charAt(itr))) {
                itr++;
            }
            nameOfUnknown = eq.charAt(itr);
            itr += 2;
            int startOfCoeff = itr;
            int endOfCoeff = 0;
            while(itr < eq.length()) {
                startOfCoeff = itr;
                while(itr != eq.length() && !contains(variables, eq.charAt(itr))) {
                    itr++;
                }
                endOfCoeff = itr;
                if(startOfCoeff == endOfCoeff) {
                    System.out.println("Variable without coefficient");
                    System.exit(0);
                }
                String coeff = eq.substring(startOfCoeff, endOfCoeff);
                if(checkWrongCoeff(coeff)) {
                    System.out.println("Wrong coefficient");
                    System.exit(0);
                }
                if(itr == eq.length()) {
                    equation.put('\1', eq.substring(startOfCoeff, endOfCoeff));
                    continue;
                }
                if(eq.charAt(itr) == nameOfUnknown) {
                    coeffOfUnknown = eq.substring(startOfCoeff, endOfCoeff);
                    itr += 2;
                    continue;
                }
                equation.put(eq.charAt(itr), eq.substring(startOfCoeff, endOfCoeff));
                itr += 2;
            }

        }



        private String getEquation() {
            String res = "";

            for(Map.Entry<Character, String> entry: equation.entrySet()) {
                if(entry.getKey() == '\1') {
                    res += entry.getValue();
                } else {
                    res += entry.getValue() + entry.getKey();
                }
                res += "+";
            }
            return (res.equals(""))? res: res.substring(0, res.length() - 1);
        }


        public String getSolution() {
            String solution = IterationKleene(coeffOfUnknown);
            String eqString = getEquation();
            if(!eqString.equals("")) {
                solution += brakes(eqString);
            }
            return solution;
        }

    }

    public SOE(ArrayList<String> equations) {
        this.equations = new ArrayList<>(0);
        for(int i = 0; i < equations.size(); ++i) {
            this.equations.add(new Equation((equations.get(i))));
        }
    }
}
