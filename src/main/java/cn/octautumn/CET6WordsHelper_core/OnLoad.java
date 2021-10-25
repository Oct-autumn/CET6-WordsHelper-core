package cn.octautumn.CET6WordsHelper_core;

import cn.octautumn.CET6WordsHelper_core.DictionaryClass.DictOperation;
import com.googlecode.lanterna.gui2.*;
import com.fasterxml.jackson.databind.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static cn.octautumn.CET6WordsHelper_core.Main.*;

public class OnLoad
{
    public static File wordListJsonFile;
    public static InputStream inputJsonStream;

    public static void onLoad(MultiWindowTextGUI gui) throws IOException, RuntimeException
    {
        //加载词库
        final String JsonFilePath = WorkingDir + fileSeparator + "CET6-Words.json";
        wordListJsonFile = new File(JsonFilePath);
        if (!(wordListJsonFile).exists())
        {
            WarningDialog_DictFileChange dialog = new WarningDialog_DictFileChange("错误",
                    "Err-000 未找到词库文件(CET6-Words.json)\n" +
                            "是否手动导入已有词库？（取消将自动加载包内词库）"
            );
            dialog.showDialog(gui);
            inputJsonStream = dialog.getInputStream();
        }else
        {
            inputJsonStream = new FileInputStream(wordListJsonFile);
        }

        while (!DictOperation.verifyDict(inputJsonStream))
        {
            WarningDialog_DictFileChange dialog = new WarningDialog_DictFileChange("错误",
                    "Err-001 词库文件校验失败\n" +
                            "是否手动导入已有词库？（取消将自动加载包内词库）"
            );
            dialog.showDialog(gui);
            inputJsonStream = dialog.getInputStream();
        }

        DictOperation.readDictionary(inputJsonStream);
        inputJsonStream.close();
    }
}
