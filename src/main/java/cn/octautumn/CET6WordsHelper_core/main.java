package cn.octautumn.CET6WordsHelper_core;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import com.fasterxml.jackson.*;

import java.io.IOException;
import java.util.Scanner;

public class main
{
    public static Scanner consoleIn = new Scanner(System.in);
    public static Terminal terminal;
    public static Screen screen;

    public static void main(String[] args) throws IOException
    {
        terminal = new DefaultTerminalFactory().createTerminal();

        //新建屏幕并启动它
        screen = new TerminalScreen(terminal);
        screen.startScreen();

        // 创建GUI模块并启动它
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));

        onLoad(gui);

        gui.waitForWindowToClose(gui.getActiveWindow());
    }

    private static void onLoad(MultiWindowTextGUI gui) throws IOException
    {
        // 创建一个窗口来装载面板
        BasicWindow loadingWindow = new BasicWindow();
        // 创建一个面板来装载组件
        Panel loadingPanel = new Panel();
        loadingPanel.setLayoutManager(new GridLayout(2));

        loadingPanel.addComponent(new Label("正在加载词汇..."));
        Label wordCountLabel = new Label("");
        wordCountLabel.setText("0/0");
        loadingPanel.addComponent(wordCountLabel);

        loadingWindow.setComponent(loadingPanel);
        gui.addWindow(loadingWindow);


    }
}
