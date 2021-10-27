package cn.octautumn.CET6WordsHelper_core;

import cn.octautumn.CET6WordsHelper_core.Dialogs.WarningDialog_DictFileChange;
import cn.octautumn.CET6WordsHelper_core.Dialogs.WarningDialog_License;
import cn.octautumn.CET6WordsHelper_core.DictionaryClass.DictOperation;
import cn.octautumn.CET6WordsHelper_core.RootClass.WarningDialog;
import com.fasterxml.jackson.databind.JsonNode;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;

import java.io.*;

import static cn.octautumn.CET6WordsHelper_core.Main.*;

public class OnLoad
{
    public static File wordListJsonFile;
    public static InputStream inputJsonStream = null;

    public static void onLoad(MultiWindowTextGUI gui) throws IOException, RuntimeException
    {
        new WarningDialog_License().showDialog(gui);

        //加载词库
        final String JsonFilePath = WorkingDir + fileSeparator + "CET6-Words.json"; //常量 词库文件路径
        wordListJsonFile = new File(JsonFilePath);
        if (!(wordListJsonFile).exists())
        {
            while (inputJsonStream == null)
            {
                WarningDialog_DictFileChange dialog = new WarningDialog_DictFileChange("错误",
                        "Err-000 未找到词库文件(CET6-Words.json)\n" +
                                "是否手动导入已有词库？（取消将自动加载包内词库）"
                );
                dialog.showDialog(gui);

                if (dialog.getInputStream().equals(WarningDialog.Options.YES))
                {
                    wordListJsonFile = new FileDialogBuilder()
                            .setTitle("打开词库文件")
                            .setDescription("选择一个CET6-Words.json")
                            .setActionLabel("Open")
                            .build()
                            .showDialog(MultiWindowGUI);
                    if (wordListJsonFile != null && wordListJsonFile.exists())
                        inputJsonStream = new FileInputStream(wordListJsonFile);
                    else
                        inputJsonStream = null;
                }
                else if (dialog.getInputStream().equals(WarningDialog.Options.CANCEL))
                {
                    inputJsonStream = Main.class.getResourceAsStream("/CET6-Words.json");
                    assert inputJsonStream != null;
                }
            }
        }
        else
        {
            inputJsonStream = new FileInputStream(wordListJsonFile);
        }

        JsonNode WordListJson = DictOperation.readAndVerifyDictJson(inputJsonStream);

        while (WordListJson == null)
        {
            WarningDialog_DictFileChange dialog = new WarningDialog_DictFileChange("错误",
                    "Err-001 词库文件校验失败\n" +
                            "是否手动导入已有词库？（取消将自动加载包内词库）"
            );
            dialog.showDialog(gui);
            if (dialog.getInputStream().equals(WarningDialog.Options.YES))
            {
                wordListJsonFile = new FileDialogBuilder()
                        .setTitle("打开词库文件")
                        .setDescription("选择一个CET6-Words.json")
                        .setActionLabel("Open")
                        .build()
                        .showDialog(MultiWindowGUI);
                if (wordListJsonFile != null && wordListJsonFile.exists())
                {
                    inputJsonStream = new FileInputStream(wordListJsonFile);
                }
            }
            else if (dialog.getInputStream().equals(WarningDialog.Options.CANCEL))
            {
                inputJsonStream = Main.class.getResourceAsStream("/CET6-Words.json");
                assert inputJsonStream != null;
            }

            WordListJson = DictOperation.readAndVerifyDictJson(inputJsonStream);
        }

        DictOperation.ConstructDictionary(WordListJson);
        inputJsonStream.close();
    }
}
