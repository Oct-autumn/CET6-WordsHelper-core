package cn.octautumn.CET6WordsHelper_core;

import java.util.Scanner;

public class main
{
    public static void main(String[] args)
    {
        Scanner consoleIn = new Scanner(System.in);

        System.out.println("+------------------------+");
        System.out.println("|         Welcome        |");
        System.out.println("|  1.En -> Ch            |");
        System.out.println("|  2.Ch -> En            |");
        System.out.println("|  3.Count               |");
        System.out.println("|                        |");
        System.out.println("|  0.Exit                |");
        System.out.println("+------------------------+");
        System.out.println("Please select a func(0~3): ");

        String sel = consoleIn.next();
        switch (sel)
        {
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
            case "0":
                break;
            default:

                break;
        }
    }
}
