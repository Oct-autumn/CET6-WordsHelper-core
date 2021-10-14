package cn.octautumn.CET6WordsHelper_core;

import cn.octautumn.CET6WordsHelper_core.WordListClass.WordList;
import com.googlecode.lanterna.gui2.*;
import com.fasterxml.jackson.databind.*;

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

        //加载词库
        try
        {
            final String JsonFilePath = WorkingDir + fileSeparator + "resources" + fileSeparator + "CET6-Words.json";
            wordListJsonFile = new File(JsonFilePath);
            if (!wordListJsonFile.exists())
            {
                do
                {
                    new WarningDialog_WordListDamage("错误",
                            "Err-000 未能找到词库文件(CET6-Words.json)\n" +
                                    "是否手动导入已有词库？（取消将自动加载包内词库）"
                    ).showDialog(gui);
                }while(wordListJsonFile == null || !wordListJsonFile.exists());

                ObjectMapper mapper = new ObjectMapper();

                while (true)
                {
                    JsonNode WordListJson = mapper.readTree(wordListJsonFile);
                    if (WordListJson.has("Verify"))
                        if (WordListJson.get("Verify").asText().equals("T2N0QXV0dW1u"))
                            break;
                    new WarningDialog_WordListDamage("错误",
                            "Err-001 词库文件校验失败\n" +
                                    "是否手动导入已有词库？（取消将自动加载包内词库）"
                    ).showDialog(gui);
                    System.out.println("1");
                }
            }
        } catch (RuntimeException exception)
        {
            System.out.println(exception);
        }
    }
}
