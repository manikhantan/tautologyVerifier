package com.indix.tautologyVerifier;

import java.util.*;

/**
 * Created by mani on 11/16/16.
 */
public class TautologyVerifier {

    private HashMap<Character,Boolean> values = new HashMap<>();
    private Stack<Boolean> results = new Stack<>();
    private Stack<Character> operators = new Stack<>();

    public boolean isTautology(char[] chars, List<Character> keys, int index) {

        if(index==keys.size()) {
            return evaluate(chars);
        }

        if(!isTautology(chars,keys,index+1))
            return false;

        values.put(keys.get(index), true);

        if(!isTautology(chars,keys,index+1))
            return false;

        values.put(keys.get(index), false);
        return true;
    }

    public boolean isTautology(String expression) {

        char[] chars = expression.toCharArray();
        values.clear();

        for (char var : chars) {
            if ((var > 64 && var < 91) || (var > 96 && var < 123)) {
                values.put(var, false);
            }
        }

        List<Character> variables = new ArrayList<>(values.keySet());
        return isTautology(chars, variables, 0);
    }

    public boolean evaluate(char[] chars) {

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