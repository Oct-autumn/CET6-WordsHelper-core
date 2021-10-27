package cn.octautumn.CET6WordsHelper_core;

import cn.octautumn.CET6WordsHelper_core.DictionaryClass.Dictionary;
import cn.octautumn.CET6WordsHelper_core.OnRunning.OnRunning;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Main
{
    //public static Scanner consoleIn = new Scanner(System.in);
    public static Terminal terminal;
    public static Screen screen;
    public static MultiWindowTextGUI MultiWindowGUI;
    public static final String fileSeparator = System.getProperty("file.separator");
    public static final String WorkingDir = System.getProperty("user.dir");
    public static final Dictionary mainDict = new Dictionary();
    public static final Dictionary HistoryDict = new Dictionary();

    public static void main(String[] args) throws IOException
    {
        terminal = new DefaultTerminalFactory()
                .setTerminalEmulatorTitle("CET6 单词助手")
                .createTerminal();

        //新建屏幕并启动
        screen = new TerminalScreen(terminal);
        screen.startScreen();
        // 创建GUI模块并启动
        MultiWindowGUI = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE_BRIGHT));
        screen.refresh();

        OnLoad.onLoad(MultiWindowGUI);
        OnRunning.ShowMenu(MultiWindowGUI);

        OnQuit.onQuit(MultiWindowGUI);

        MultiWindowGUI.updateScreen();
        screen.close();
        terminal.close();
    }
}
