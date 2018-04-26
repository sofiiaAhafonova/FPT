package com.labs.lab3;
import java.util.regex.*;
import java.util.HashMap;
public class Parser {

    enum State{INCORRECT_INPUT, NO_VARIABLES, VARIABLE_SAVED, VARIABLE_EXISTS}

    private static String MATRIX_REGEX = "^\\[(\\[-?([1-9]\\d*|\\d)(\\.\\d\\d?)?(,\\ -?([1-9]\\d*|\\d)(\\.\\d\\d?)?)*\\])(,\\ \\[-?([1-9]\\d*|\\d)*(\\.\\d\\d?)?(,\\ -?([1-9]\\d*|\\d)(\\.\\d\\d?)?)*\\])*\\]";
    private HashMap<Character, Matrix> variables;

    public Parser()
    {
        variables = new HashMap<Character, Matrix>();
    }

    public Matrix Parse(String s)
    {
        Result result;
        Character ch =  setVariable(s);
        if (ch != null)
            s = s.substring(4);
        if ((result = Plus(s)) == null || result.current == null || !result.rest.isEmpty()) {
            System.err.println("Error: can't full parse");
            return null;
        }
        if (ch != null){
            if (variables.containsKey(ch)) {
                variables.replace(ch, result.current);
                return variables.get(ch);
            }
            else
                variables.put(ch, result.current);
        }
        return result.current;
    }

    private Character setVariable(String s){
        Pattern regexp = Pattern.compile("[ABC] = ");
        Matcher matcher = regexp.matcher(s);
        if (!matcher.lookingAt())
            return null;
        return s.charAt(0);
    }

    private Matrix getVariable(Character variableName)
    {
        if (!variables.containsKey(variableName)) {
            System.err.println( "Error: Try get unexists variable '"+variableName+"'" );
            return null;
        }
        return variables.get(variableName);
    }

    private Result Plus(String s)
    {
        Result buffer = Multiplication(s);
        if (buffer == null && (buffer = getMatrix(s)) == null)
            return null;
        Matrix cur = buffer.current;
        while (buffer.rest.length() > 0) {
            if (!(buffer.rest.charAt(0) == ' ' && buffer.rest.charAt(1) == '+' && buffer.rest.charAt(2) == ' '))
                break;
            String next = buffer.rest.substring(3);
            buffer = Multiplication(next);
            if (buffer == null && (buffer = getMatrix(s)) == null)
                return null;
            buffer.current = cur.sum(buffer.current);
        }
        return new Result(buffer.current, buffer.rest);
    }

    private Result Multiplication(String s)
    {
            Result current = Bracket(s);
            if (current == null && (current = getMatrix(s)) == null)
                return null;
            Matrix acc = current.current;
            if (acc == null) return null;
            while (true) {
            if (current.rest.length() == 0) {
                    return current;
                }
                current = Transposition(current);
                if (current == null) return null;
                acc = current.current;
                if (current.rest.equals(""))
                    return current;
                current = ScalarMultiplication(current);
                if (current == null) return null;
                acc = current.current;
                if (current.rest.equals(""))
                    return current;
                if (!(current.rest.charAt(0) == ' ' && current.rest.charAt(1) == '*'
                        && current.rest.charAt(2) == ' '))
                    break;
                String next = current.rest.substring(3);
                current = Bracket(next);
                if (current == null) return null;
                current.current = acc.mult(current.current);
            }
            return current;
    }

    private Result Transposition(Result cur){
        if (cur == null) return null;
        if(cur.rest.length() == 0) return cur;
        if(cur.rest.charAt(0) !=' ' || cur.rest.charAt(1) !='^' || cur.rest.charAt(2) !='T')
            return cur;
        cur.rest = cur.rest.substring(3);
        cur.current = cur.current.transpose();
        return cur;
    }

    private Result ScalarMultiplication(Result cur){
        if (cur == null) return null;
        if(cur.rest.length() == 0) return cur;
        if(cur.rest.charAt(0) !=' ' || cur.rest.charAt(1) !='*' || cur.rest.charAt(2) !=' ')
            return cur;
        Pattern regexp = Pattern.compile("-?([1-9]\\d*|\\d)(\\.\\d\\d?)?");
        String buf = cur.rest.substring(3);
        Matcher matcher = regexp.matcher(buf);
        if (!matcher.lookingAt())
            return cur;
        String buffer = matcher.group();
        Double k = Double.parseDouble(buffer);
        cur.current = cur.current.mult(k);
        cur.rest = buf.substring(buffer.length());
        return cur;
    }

    private Result Bracket(String s)
    {
        char zeroChar = s.charAt(0);
        Result r;
        if (zeroChar == '(') {
            if ((r = Plus(s.substring(1))) == null)
                return null;
            if (!r.rest.isEmpty() && r.rest.charAt(0) == ')')
                r.rest = r.rest.substring(1);
            else
                System.err.println("Error: no close bracket");
            return r;
        }
        return Variable(s);
    }

    private Result Variable(String s) {
        if (Character.isLetter(s.charAt(0)) && s.length() == 1)
            return new Result(getVariable(s.charAt(0)), "");
        if (Character.isLetter(s.charAt(0)) && (s.charAt(1) == ' ') || s.charAt(1) == ' ') // если что-нибудь нашли
            return new Result(getVariable(s.charAt(0)), s.substring(1));
        return getMatrix(s);
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
        Matrix m;
        try {
            m = new Matrix(matrix);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
        return new Result(m, restPart);
    }
}