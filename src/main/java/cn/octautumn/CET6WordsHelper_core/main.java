package cn.octautumn.CET6WordsHelper_core;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;

import java.io.IOException;
import java.util.Scanner;

public class main
{
    public static Scanner consoleIn = new Scanner(System.in);
    public static Terminal terminal;
    public static Screen screen;
    public static MultiWindowTextGUI MultiWindowGUI;

    public static void main(String[] args) throws IOException
    {
        terminal = new DefaultTerminalFactory().createTerminal();

        //新建屏幕并启动它
        screen = new TerminalScreen(terminal);
        screen.startScreen();
        // 创建GUI模块并启动它
        MultiWindowGUI = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));

        OnLoad.onLoad(MultiWindowGUI);

        MultiWindowGUI.waitForWindowToClose(MultiWindowGUI.getActiveWindow());
    }
}
