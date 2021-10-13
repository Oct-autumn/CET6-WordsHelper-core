package cn.octautumn.CET6WordsHelper_core;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class OnLoad
{
    public static void onLoad(MultiWindowTextGUI gui) throws IOException
    {
        // 创建一个窗口来装载面板
        BasicWindow loadingWindow = new BasicWindow();
        loadingWindow.setHints(List.of(Window.Hint.CENTERED));
        // 创建一个面板来装载组件
        Panel loadingPanel = new Panel();
        loadingPanel.setLayoutManager(new GridLayout(2));

        loadingPanel.addComponent(new Label("正在加载词汇..."));
        Label wordCountLabel = new Label("");
        wordCountLabel.setText("0/0");
        loadingPanel.addComponent(wordCountLabel);

        loadingWindow.setComponent(loadingPanel);
        gui.addWindow(loadingWindow);

        try
        {
            final String JsonFilePath = System.getProperty("user.dir") + "resources\\CET6-Words.json";
            File wordListJsonFile = new File(JsonFilePath);
            if (!wordListJsonFile.exists())
            {
                warningDialog warningDlg = new warningDialog("错误");
                warningDlg.showDialog(gui);
            }
        }
        catch (RuntimeException exception)
        {
            System.out.println(exception);
        }
    }
}
