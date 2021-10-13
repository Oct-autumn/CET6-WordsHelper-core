package cn.octautumn.CET6WordsHelper_core;

import com.googlecode.lanterna.gui2.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static cn.octautumn.CET6WordsHelper_core.Main.*;

public class OnLoad
{
    public static File wordListJsonFile;
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
            final String JsonFilePath = WorkingDir + fileSeparator + "resources" + fileSeparator + "CET6-Words.json";
            wordListJsonFile = new File(JsonFilePath);
            if (!wordListJsonFile.exists())
            {
                WarningDialog_WordListDamage warningDlg = new WarningDialog_WordListDamage("错误", "Err-000 未能找到词库文件(CET6-Words.json)\n是否手动导入？");
                warningDlg.showDialog(gui);
                try
                {
                    while(!wordListJsonFile.exists())
                    {//等待输入
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

            }
        }
        catch (RuntimeException exception)
        {
            System.out.println(exception);
        }
    }
}
