package cn.octautumn.CET6WordsHelper_core;

import cn.octautumn.CET6WordsHelper_core.WordListClass.DictEntry;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.menu.Menu;
import com.googlecode.lanterna.gui2.menu.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OnRunning
{
    public static void ShowMenu(MultiWindowTextGUI gui) throws IOException, RuntimeException
    {
        BasicWindow menuWindow = new BasicWindow("菜单");
        menuWindow.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        ActionListBox menu = new ActionListBox(new TerminalSize(17, 6))
                .addItem("英译中挑战", () -> {
                    menuWindow.setVisible(false);
                    try
                    {
                        ShowMode1(gui, menuWindow);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                })
                .addItem("中译英挑战", () -> {

                })
                .addItem("单词记忆情况", () -> {

                })
                .addItem("",()->{})
                .addItem("退出", () -> {
                    try
                    {
                        Main.screen.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                });
        panel.addComponent(menu);

        menuWindow.setComponent(panel);

        gui.addWindowAndWait(menuWindow);
        gui.setActiveWindow(menuWindow);
        gui.updateScreen();
    }

    public static void ShowMode1(MultiWindowTextGUI gui, BasicWindow menuWindow) throws IOException, RuntimeException
    {
        BasicWindow thisWindow = new BasicWindow("英译中挑战");
        thisWindow.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        Label processLabel = new Label("████████████████████");
        panel.addComponent(processLabel);
        Label countdownLabel = new Label("300s");
        panel.addComponent(countdownLabel);
        Label wordLabel = new Label("");
        panel.addComponent(wordLabel);
        panel.addComponent(new EmptySpace());

        ActionListBox selections = new ActionListBox(new TerminalSize(30, 4));

        int errorCount = 0;
        for (int wordCount = 0; wordCount < 20; wordCount++)
        {
            ArrayList<DictEntry> selWord = new ArrayList<>();
            for (int i = 0; i < 4; i++)
            {
                Main.wordList.getData().get(Math.random()*(Main.wordList.getDat))
            }
        }

        gui.addWindow(thisWindow);
        gui.setActiveWindow(thisWindow);
        gui.updateScreen();
    }
}
