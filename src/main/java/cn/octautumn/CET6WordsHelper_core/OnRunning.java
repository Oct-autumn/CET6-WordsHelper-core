package cn.octautumn.CET6WordsHelper_core;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.menu.Menu;
import com.googlecode.lanterna.gui2.menu.MenuItem;

import java.io.IOException;
import java.util.List;

public class OnRunning
{
    public static void ShowMenu(MultiWindowTextGUI gui) throws IOException, RuntimeException
    {
        BasicWindow menuWindow = new BasicWindow("<>");
        menuWindow.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        Menu menu = new Menu("菜单")
                .add(new MenuItem("英译中挑战", () -> {

                }))
                .add(new MenuItem("中译英挑战", () -> {

                }))
                .add(new MenuItem("单词记忆情况", () -> {

                }))
                .add(new MenuItem("退出", () -> {

                }));
        panel.addComponent(menu);

        menuWindow.setComponent(panel);

        gui.addWindow(menuWindow);
        gui.setActiveWindow(menuWindow);
        gui.updateScreen();
    }
}
