package cn.octautumn.CET6WordsHelper_core.DictionaryClass;

import cn.octautumn.CET6WordsHelper_core.Main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.lanterna.gui2.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import static cn.octautumn.CET6WordsHelper_core.Main.mainDict;

public class DictOperation
{
    public static void readDictionary(InputStream input) throws IOException
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
        Main.MultiWindowGUI.addWindow(loadingWindow);
        Main.MultiWindowGUI.setActiveWindow(loadingWindow);

        //构造词库数据结构
        ObjectMapper mapper = new ObjectMapper();

        JsonNode WordListJson = mapper.readTree(input);
        int wordSum = WordListJson.get("count").asInt();
        mainDict.setCount(wordSum);
        int wordKey = 0;

        for (Iterator<JsonNode> it = WordListJson.get("data").iterator(); it.hasNext(); wordKey++)
        {
            JsonNode NowEntry = it.next();
            DictEntry newEntry = new DictEntry();

            newEntry.setFamiliar(NowEntry.get("familiar").asInt())
                    .setEnS(NowEntry.get("enS").asText());

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

            mainDict.addEntry(wordKey, newEntry);
            wordCountLabel.setText(String.format("%04d/%04d", wordKey + 1, wordSum));

            int tmp_cal = (wordKey + 1) * 100 / wordSum;
            processLabel.setText("█".repeat(Math.max(0, tmp_cal / 10)) +
                    "━".repeat(Math.max(0, 10 - (tmp_cal / 10))));

            Main.MultiWindowGUI.updateScreen();
        }

        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        loadingWindow.close();
        Main.MultiWindowGUI.updateScreen();
    }

    public static boolean verifyDict(InputStream input) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode WordListJson = mapper.readTree(input);
        if (WordListJson.has("verify"))
            return WordListJson.get("verify").asText().equals("T2N0QXV0dW1u");
        return false;
    }
}
