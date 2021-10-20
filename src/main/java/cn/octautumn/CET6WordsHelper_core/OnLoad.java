package cn.octautumn.CET6WordsHelper_core;

import cn.octautumn.CET6WordsHelper_core.DictionaryClass.ChTrans;
import cn.octautumn.CET6WordsHelper_core.DictionaryClass.DictEntry;
import com.googlecode.lanterna.gui2.*;
import com.fasterxml.jackson.databind.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static cn.octautumn.CET6WordsHelper_core.Main.*;

public class OnLoad
{
    public static File wordListJsonFile;

    public static void onLoad(MultiWindowTextGUI gui) throws IOException, RuntimeException
    {
        // 创建一个窗口来装载面板
        BasicWindow loadingWindow = new BasicWindow("加载中");
        loadingWindow.setHints(List.of(Window.Hint.CENTERED));
        // 创建一个面板来装载组件
        Panel loadingPanel = new Panel();
        loadingPanel.setLayoutManager(new GridLayout(2));

        loadingPanel.addComponent(new Label("正在加载词汇..."));
        loadingPanel.addComponent(new EmptySpace());

        Label processLabel = new Label("━━━━━━━━━━");
        loadingPanel.addComponent(processLabel);
        Label wordCountLabel = new Label("");
        wordCountLabel.setText("0000/0000");
        loadingPanel.addComponent(wordCountLabel);

        loadingWindow.setComponent(loadingPanel);
        gui.addWindow(loadingWindow);
        gui.setActiveWindow(loadingWindow);

        //加载词库
        final String JsonFilePath = WorkingDir + fileSeparator + "CET6-Words.json";
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
                if (WordListJson.has("verify"))
                    if (WordListJson.get("verify").asText().equals("T2N0QXV0dW1u"))
                        break;
                new WarningDialog_WordListDamage("错误",
                        "Err-001 词库文件校验失败\n" +
                                "是否手动导入已有词库？（取消将自动加载包内词库）"
                ).showDialog(gui);
            }
        }

        //构造词库数据结构
        ObjectMapper mapper = new ObjectMapper();
        JsonNode WordListJson = mapper.readTree(wordListJsonFile);
        int wordSum = WordListJson.get("count").asInt();
        dictionary.setWordCount(wordSum);
        int wordKey = 0;

        for (Iterator<JsonNode> it = WordListJson.get("data").iterator(); it.hasNext(); wordKey++)
        {
            JsonNode NowEntry = it.next();
            DictEntry newEntry = new DictEntry();

            newEntry.setId(NowEntry.get("id").asInt()).
                    setEnS(NowEntry.get("enS").asText());

            for (JsonNode NowChTrans : NowEntry.get("chS"))
            {
                ChTrans newChTrans = new ChTrans();

                newChTrans.setId(NowChTrans.get("id").asInt())
                        .setPos(NowChTrans.get("pos").asText());

                for (JsonNode NowMean : NowChTrans.get("mean"))
                {
                    newChTrans.addMean(NowMean.asText());
                }

                newEntry.addChS(newChTrans);
            }

            dictionary.addEntry(wordKey, newEntry);
            wordCountLabel.setText(String.format("%04d/%04d",wordKey + 1,wordSum));

            int tmp_cal = (wordKey+1)*100 / wordSum;
            processLabel.setText("█".repeat(Math.max(0, tmp_cal / 10)) +
                    "━".repeat(Math.max(0, 10 - (tmp_cal / 10))));

            gui.updateScreen();
        }

        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        loadingWindow.close();
        gui.updateScreen();
    }
}
