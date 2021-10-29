package ru.RG;

import java.util.ArrayList;
import java.util.HashMap;

public class RG {

    SOE system;

    private int contains(char nameOfUnknown) {
        for(int i = 0; i < system.equations.size(); ++i) {
            if(system.equations.get(i).nameOfUnknown == nameOfUnknown) {
                return i;
            }
        }
        return -1;
    }

    public RG(ArrayList<String> rules) {
        system = new SOE();
        for(int i = 0; i < rules.size(); ++i) {
            String rule = rules.get(i).replaceAll(" ", "").replaceAll("\n", "");
            String equation = rule.charAt(0) + "=" + rule.charAt(3);
            int indexOfEq = contains(rule.charAt(0));
            if(rule.length() > 4) {
                equation += rule.charAt(4);
                if(indexOfEq == -1) {
                    system.equations.add(new SOE.Equation(equation));
                } else {
                    SOE.Equation eq = system.equations.get(indexOfEq);
                    if(!eq.equation.containsKey(rule.charAt(4))) {
                        system.equations.get(indexOfEq).equation.put(rule.charAt(4), String.valueOf(rule.charAt(3)));
                    } else {
                        system.equations.get(indexOfEq).equation.put(rule.charAt(4), SOE.brakes(rule.charAt(3) + "+" +  eq.equation.get(rule.charAt(4))));
                    }
                }
            } else {
                if(indexOfEq == -1) {
                    system.equations.add(new SOE.Equation(equation));
                } else {
                    SOE.Equation eq = system.equations.get(indexOfEq);
                    if(!eq.equation.containsKey('\1')) {
                        system.equations.get(indexOfEq).equation.put('\1', String.valueOf(rule.charAt(3)));
                    } else {
                        system.equations.get(indexOfEq).equation.put('\1', SOE.brakes(rule.charAt(3) + "+" +  eq.equation.get('\1')));
                    }
                }
            }
        }
    }
}
