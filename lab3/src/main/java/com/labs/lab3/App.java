package com.labs.lab3;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

          Parser p = new Parser();
        System.out.println(p.Parse("A = [[2], [-2], [1]] ^T * [[1], [1], [1]]").toString());
        System.out.println(p.Parse("B = [[6, -1, -1]] * ([[6], [9], [-3]] + [[4], [-4], [0]])").toString());
        System.out.println(p.Parse("A + B").toString());
        System.out.println(p.Parse("A * B").toString());
        System.out.println(p.Parse("C = [[6]] * 10").toString());
        System.out.println(p.Parse("A * B + C").toString());

        System.out.println(p.Parse("C = [[6], [1]] * 10").toString());
    }
}
