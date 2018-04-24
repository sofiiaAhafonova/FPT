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

        Parser p = new Parser();
        System.out.println(p.Parse("A = [[5, 211, 5.36], [88.1, 7.4, 91.11], [4.2, -9.05, 58]]").toString());
        System.out.println(p.Parse("A ^T").toString());

    }
}
