package com.indix.tautologyVerifier;

import java.util.*;

/**
 * Created by mani on 11/16/16.
 */
public class TautologyVerifier {

    private char[] chars;
    private List<Character> variables;
    private HashMap<Character,Boolean> values = new HashMap<>();
    private Stack<Boolean> results = new Stack<>();
    private Stack<Character> operators = new Stack<>();

    public boolean isTautology(int index) {

        if(index==variables.size()) {
            return evaluate();
        }

        if(!isTautology(index+1))
            return false;

        values.put(variables.get(index), true);

        if(!isTautology(index+1))
            return false;

        values.put(variables.get(index), false);
        return true;
    }

    public boolean isTautology(String expression) {

        chars = expression.toCharArray();
        values.clear();

        for (char var : chars) {
            if ((var > 64 && var < 91) || (var > 96 && var < 123)) {
                values.put(var, false);
            }
        }

        variables = new ArrayList<>(values.keySet());
        return isTautology(0);
    }

    public boolean evaluate() {

        results.clear();
        operators.clear();

        for (char var : chars) {
            if ((var == '(') || (var == '&') || (var == '|') || (var == '!')) {
                operators.push(var);
            }
            else if (var == ')') {
                char operator = operators.pop();
                boolean result = results.pop();

                while (operator != '(') {
                    aggregate(operator, result);
                    operator = operators.pop();
                    result = results.pop();
                }
                results.push(result);
            }
            else if ((var > 64 && var < 91) || (var > 96 && var < 123)) {
                if (results.isEmpty()) {
                    results.push(values.get(var));
                }
                else {
                    char operator = operators.pop();
                    if (operator == '(') {
                        results.push(values.get(var));
                        operators.push(operator);
                    }
                    else {
                        aggregate(operator,values.get(var));
                    }
                }
            }
        }

        while(!operators.isEmpty()) {
            char operator = operators.pop();
            boolean result = results.pop();
            aggregate(operator,result);
        }
        return results.pop();
    }

    public void aggregate(char operator, boolean result) {

        if(operator == '!') {
            results.push(!result);
        }
        else if(operator=='&') {
            results.push(results.pop()&&result);
        }
        else if(operator=='|') {
            results.push(results.pop()||result);
        }
    }
}