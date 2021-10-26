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

import static cn.octautumn.CET6WordsHelper_core.Main.main;
import static cn.octautumn.CET6WordsHelper_core.Main.mainDict;

public class DictOperation
{
    public static void ConstructDictionary(JsonNode input)
    {
        //构造词库数据结构
        int wordSum = input.get("count").asInt();
        mainDict.setVerify(input.get("verify").asText());
        mainDict.setCount(wordSum);
        int wordKey = 0;

        for (Iterator<JsonNode> it = input.get("data").iterator(); it.hasNext(); wordKey++)
        {
            JsonNode NowEntry = it.next();
            DictEntry newEntry = new DictEntry();

            newEntry.setFamiliar(Familiar.valueOf(NowEntry.get("familiar").asText("haveNotAppeared")))
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
        }

        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static JsonNode readAndVerifyDictJson(InputStream input) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode WordListJson = mapper.readTree(input);
        if (WordListJson.has("verify"))
            if (WordListJson.get("verify").asText().equals("T2N0QXV0dW1u"))
                return WordListJson;
        return null;
    }
}
