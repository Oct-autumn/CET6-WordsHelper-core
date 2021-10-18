package cn.octautumn.CET6WordsHelper_core.OnRunning;

import cn.octautumn.CET6WordsHelper_core.Main;
import cn.octautumn.CET6WordsHelper_core.OnRunning.CM1.RunMode1;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.io.IOException;
import java.util.List;

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
        panel.setLayoutManager(new GridLayout(1));

        Label countdownLabel = new Label("████████████████████  300s");
        panel.addComponent(countdownLabel);
        Label wordLabel = new Label("");
        panel.addComponent(wordLabel);
        panel.addComponent(new Label("在下列选项中选出与该单词对应的译义"));
        ActionListBox selections = new ActionListBox(new TerminalSize(30, 4));
        panel.addComponent(selections);
        thisWindow.setComponent(panel);

        RunMode1.func Mode1func = new RunMode1.func(thisWindow, wordLabel, selections, menuWindow);
        Thread func = new Thread(Mode1func);
        func.start();
        Thread counter = new Thread(new RunMode1.Countdown(countdownLabel, Mode1func));
        counter.start();

        gui.addWindowAndWait(thisWindow);
        if (gui.getWindows().contains(thisWindow))
            gui.setActiveWindow(thisWindow);
        gui.updateScreen();
    }
}
