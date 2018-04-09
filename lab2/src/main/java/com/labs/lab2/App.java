package com.labs.lab2;


public class App 
{
    public static void main( String[] args )
    {
        System.out.println("My regular expression is: \\++([\\D]?\\d*)");
        String str = "++++++++a32432434";
        SwitchFSM switchFSM = new SwitchFSM();
        TransTableFSM transTableFSM = new TransTableFSM();
        StateFSM stateFSM = new StateFSM();
        boolean res1 = switchFSM.scanString(str);
        String expr1 = res1 ? "" : " not";
        System.out.printf("Current string is:\"%s\"\nThis string is%s suitable for current regex\n", str, expr1);
        boolean res2 = transTableFSM.scanString(str);
//        String expr2 = res2 ? "" : " not";
//        System.out.printf("Current string is:\"%s\"\nThis string is%s suitable for current regex\n", str, expr2);
        boolean res3 = stateFSM.scanString(str);
//        String expr3 = res3 ? "" : " not";
//        System.out.printf("Current string is:\"%s\"\nThis string is%s suitable for current regex\n", str, expr3);
        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }
}
