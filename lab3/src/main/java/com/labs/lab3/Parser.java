package com.labs.lab3;
import java.util.regex.*;
import java.util.HashMap;
public class Parser {

    enum State{INCORRECT_INPUT, NO_VARIABLES, VARIABLE_SAVED, VARIABLE_EXISTS}

    private static String MATRIX_REGEX = "^\\[(\\[-?([1-9]\\d*|\\d)(\\.\\d\\d?)?(,\\ -?([1-9]\\d*|\\d)(\\.\\d\\d?)?)*\\])(,\\ \\[-?([1-9]\\d*|\\d)*(\\.\\d\\d?)?(,\\ -?([1-9]\\d*|\\d)(\\.\\d\\d?)?)*\\])*\\]";
    private HashMap<Character, Matrix> variables;
    private State state;

    public Parser()
    {
        variables = new HashMap<Character, Matrix>();
    }

    public Matrix Parse(String s)
    {

        Character ch =  setVariable(s);
        if (ch != null)
            s = s.substring(4);
        Result result = Plus(s);
        if (result.current == null || !result.rest.isEmpty()) {
            System.err.println("Error: can't full parse");
            System.err.println("rest: " + result.rest);
            return null;
        }
        if (ch != null){
            if (variables.containsKey(ch))return
                variables.replace(ch, result.current);
            else
                variables.put(ch, result.current);
        }
        return result.current;
    }

    private Character setVariable(String s){
        Pattern regexp = Pattern.compile("[ABC] = ");
        Matcher matcher = regexp.matcher(s);
        if (!matcher.lookingAt()) {
            state = State.NO_VARIABLES;
            return null;
        }
        return s.charAt(0);
    }
    public Matrix getVariable(Character variableName)
    {
        if (!variables.containsKey(variableName)) {
            System.err.println( "Error: Try get unexists variable '"+variableName+"'" );
            return null;
        }
        return variables.get(variableName);
    }


    private Result Plus(String s)
    {
        Result buffer = Mult(s);
        Matrix cur = buffer.current;

        while (buffer.rest.length() > 0) {
            if (!(buffer.rest.charAt(0) == ' ' && buffer.rest.charAt(1) == '+' && buffer.rest.charAt(2) == ' '))
                break;
            String next = buffer.rest.substring(3);
            buffer = getMatrix(next);
            cur = cur.sum(buffer.current);
            buffer.current = cur;
        }
        return new Result(buffer.current, buffer.rest);
    }

    private static double [][] getDoubleArray(String input){
        String [] vector = input.split("[\\[||\\]]");
        int counter = 0;
        for(String str: vector)
            if (str.length() != 0 && !str.equals(", "))
                counter++;
        double [][]matrix = new double[counter][];
        int i = 0;
        int len = 0;
        String []buf;
        for(String str: vector)
            if (str.length() != 0 && !str.equals(", ")) {
                buf = str.split(", ");
                if (i == 0)
                    len = buf.length;
                else if (len != buf.length)
                    return null;
                matrix[i] = new double[buf.length];
                for(int j = 0; j < buf.length; j++)
                    matrix[i][j] = Double.parseDouble(buf[j]);
                i++;
            }
        return matrix;
    }

    private Result getMatrix(String input)
    {
        Pattern regexp = Pattern.compile(MATRIX_REGEX);
        Matcher matcher = regexp.matcher(input);
        if (!matcher.lookingAt())
            return null;
        String buffer = matcher.group();
        String restPart = input.substring(buffer.length());
        double [][]matrix = getDoubleArray(buffer);
        if (matrix == null)
            return null;
        return new Result(new Matrix(matrix), restPart);
    }

    private Result Bracket(String s)
    {
        char zeroChar = s.charAt(0);
        if (zeroChar == '(') {
            Result r = Plus(s.substring(1));
            if (!r.rest.isEmpty() && r.rest.charAt(0) == ')') {
                r.rest = r.rest.substring(1);
            } else {
                System.err.println("Error: not close bracket");
            }
            return r;
        }
        return Variable(s);
    }

    private Result Variable(String s) {
        if (Character.isLetter(s.charAt(0)) && s.charAt(1) == ' ')  { // если что-нибудь нашли
                return new Result(getVariable(s.charAt(0)), s.substring(1));

        }
        return getMatrix(s);
    }


    private Result Mult(String s)
    {
            Result current = Bracket(s);
            if (current == null)
                current = getMatrix(s);
            Matrix acc = current.current;
            while (true) {
                if (current.rest.length() == 0) {
                    return current;
                }
                current = Transposion(current);
                if (current.rest.equals(""))
                    return current;
                current = ScalarMult(current);
                if (current.rest.equals(""))
                    return current;
                if (!(current.rest.charAt(0) == ' ' && current.rest.charAt(1) == '*' && current.rest.charAt(2) == ' '))
                    break;
                String next = current.rest.substring(3);
                current = Bracket(next);
                acc.mult(current.current);
            }
            return current;
    }

    private Result Transposion(Result cur){
        if(cur.rest.length() == 0) return cur;
        if(cur.rest.charAt(0) !=' ' || cur.rest.charAt(1) !='^' || cur.rest.charAt(2) !='T')
            return cur;
        cur.rest = cur.rest.substring(3);
        cur.current = cur.current.transpose();
        return cur;
    }

    private Result ScalarMult(Result cur){
        if(cur.rest.length() == 0) return cur;
        if(cur.rest.charAt(0) !=' ' || cur.rest.charAt(1) !='*' || cur.rest.charAt(2) !=' ')
            return cur;
        Pattern regexp = Pattern.compile("-?([1-9]\\d*|\\d)(\\.\\d\\d?)?");
        cur.rest = cur.rest.substring(3);
        Matcher matcher = regexp.matcher(cur.rest);
        if (!matcher.lookingAt())
            return cur;
        String buffer = matcher.group();
        Double k = Double.parseDouble(buffer);
        cur.current = cur.current.mult(k);
        cur.rest = cur.rest.substring(buffer.length());
        return cur;
    }
}