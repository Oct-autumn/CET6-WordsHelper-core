package cn.octautumn.CET6WordsHelper_core;

public class menu
{
    public static void openingMenu()
    {
        System.out.println("+------------------------+");
        System.out.println("|         Welcome        |");
        System.out.println("|  1.En -> Ch            |");
        System.out.println("|  2.Ch -> En            |");
        System.out.println("|  3.Count               |");
        System.out.println("|                        |");
        System.out.println("|  0.Exit                |");
        System.out.println("+------------------------+");
        System.out.println("Please select a func(0~3): ");

        String sel = main.consoleIn.next();
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
                System.out.println("错误的功能选择");
                break;
        }
    }
}
