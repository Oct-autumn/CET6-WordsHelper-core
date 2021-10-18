package cn.octautumn.CET6WordsHelper_core;

import cn.octautumn.CET6WordsHelper_core.WordListClass.WordList;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;

import java.io.IOException;
import java.util.Scanner;

public class Main
{
    //public static Scanner consoleIn = new Scanner(System.in);
    public static Terminal terminal;
    public static Screen screen;
    public static MultiWindowTextGUI MultiWindowGUI;
    public static final String fileSeparator = System.getProperty("file.separator");
    public static final String WorkingDir = System.getProperty("user.dir");
    public static WordList wordList = new WordList();

    public static void main(String[] args) throws IOException
    {
        terminal = new DefaultTerminalFactory().createTerminal();

        //新建屏幕并启动
        screen = new TerminalScreen(terminal);
        screen.startScreen();
        // 创建GUI模块并启动
        MultiWindowGUI = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE_BRIGHT));

        OnLoad.onLoad(MultiWindowGUI);
        OnRunning.ShowMenu(MultiWindowGUI);


    }
}
