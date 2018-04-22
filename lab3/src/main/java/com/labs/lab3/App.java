package com.labs.lab3;
import java.util.regex.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String myString = "(2*((3-2)+(5-3)))+(5*(3-7))";
        String ar[] = myString.split("[\\(||\\)]");
        int i = -1;
        while (++i < ar.length)
            System.out.println(ar[i]);
    }
}
