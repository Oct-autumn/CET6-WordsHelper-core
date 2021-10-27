package cn.octautumn.CET6WordsHelper_core.OnRunning;

import cn.octautumn.CET6WordsHelper_core.Dialogs.WarningDialog_License;
import cn.octautumn.CET6WordsHelper_core.Main;
import cn.octautumn.CET6WordsHelper_core.OnRunning.ChallengeMode.*;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

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
                    menuWindow.setVisible(false);
                    try
                    {
                        ShowMode2(gui, menuWindow);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                })
                .addItem("单词记忆情况", () -> {
                    menuWindow.setVisible(false);
                    try
                    {
                        ShowHistory(gui, menuWindow);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                })
                .addItem("词库操作", () -> {
                })
                .addItem("", () -> {
                })
                .addItem("GPL证书", () -> new WarningDialog_License().showDialog(gui))
                .addItem("退出", menuWindow::close);
        panel.addComponent(menu);

        menuWindow.setComponent(panel);

        gui.addWindowAndWait(menuWindow);
        if (gui.getWindows().contains(menuWindow))
            gui.setActiveWindow(menuWindow);
        gui.updateScreen();
    }

    //英译中挑战
    public static void ShowMode1(MultiWindowTextGUI gui, BasicWindow menuWindow) throws IOException, RuntimeException
    {
        BasicWindow thisWindow = new BasicWindow("英译中挑战");
        thisWindow.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        panel.addComponent(0, new Label("████████████████████  300s"));  //countdownLabel
        panel.addComponent(1, new Label(""));   //wordLabel
        panel.addComponent(2, new Label("在下列选项中选出与该单词对应的译义"));  //tipLabel
        panel.addComponent(3, new ActionListBox(new TerminalSize(30, 4)));  //transSelections
        panel.addComponent(4, new Label("<-------------------------------------->"));
        panel.addComponent(5, new Button("EXIT", () -> {
            thisWindow.close();
            menuWindow.setVisible(true);
            Main.MultiWindowGUI.setActiveWindow(menuWindow);
            Main.MultiWindowGUI.waitForWindowToClose(menuWindow);
        }));
        thisWindow.setComponent(panel);

        RunMode1 Mode1func = new RunMode1(thisWindow, menuWindow, panel);
        Thread func = new Thread(Mode1func);
        Thread counter = new Thread(new CountDown(panel, Mode1func, 300));
        func.start();
        counter.start();

        gui.addWindowAndWait(thisWindow);
        if (gui.getWindows().contains(thisWindow))
            gui.setActiveWindow(thisWindow);
        gui.updateScreen();
    }

    //中译英挑战
    public static void ShowMode2(MultiWindowTextGUI gui, BasicWindow menuWindow) throws IOException, RuntimeException
    {
        BasicWindow thisWindow = new BasicWindow("中译英挑战");
        thisWindow.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        panel.addComponent(0, new Label("████████████████████  600s"));  //countdownLabel
        panel.addComponent(1, new Label(""));   //wordLabel
        panel.addComponent(2, new Label(""));   //wordTipLabel
        panel.addComponent(3, new Label("根据提示在下面拼写该单词："));  //tipLabel
        panel.addComponent(4, new TextBox(new TerminalSize(30, 1), TextBox.Style.MULTI_LINE)
                .setText(""));  //answer
        panel.addComponent(5, new Label("<-------------------------------------->"));
        panel.addComponent(6, new Button("EXIT", () -> {
            thisWindow.close();
            menuWindow.setVisible(true);
            Main.MultiWindowGUI.setActiveWindow(menuWindow);
            Main.MultiWindowGUI.waitForWindowToClose(menuWindow);
        }));

        thisWindow.setComponent(panel);

        RunMode2 Mode2func = new RunMode2(thisWindow, menuWindow, panel);
        Thread func = new Thread(Mode2func);
        Thread counter = new Thread(new CountDown(panel, Mode2func, 600));
        func.start();
        counter.start();

        gui.addWindowAndWait(thisWindow);
        if (gui.getWindows().contains(thisWindow))
            gui.setActiveWindow(thisWindow);
        gui.updateScreen();
    }

    public static void ShowHistory(MultiWindowTextGUI gui, BasicWindow menuWindow) throws IOException, RuntimeException
    {

    }
}
