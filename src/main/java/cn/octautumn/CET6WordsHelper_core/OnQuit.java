package cn.octautumn.CET6WordsHelper_core;

import cn.octautumn.CET6WordsHelper_core.DictionaryClass.ChTrans;
import cn.octautumn.CET6WordsHelper_core.DictionaryClass.DictEntry;
import cn.octautumn.CET6WordsHelper_core.DictionaryClass.Familiar;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import static cn.octautumn.CET6WordsHelper_core.Main.*;

public class OnQuit
{
    public static void onQuit(MultiWindowTextGUI gui) throws IOException
    {
        System.out.println("Quit");

        final String JsonFilePath = WorkingDir + fileSeparator + "CET6-Words.json"; //常量 词库文件路径
        final String FamiliarFilePath = WorkingDir + fileSeparator + "已掌握单词.txt"; //常量 词库文件路径
        final String notFamiliarFilePath = WorkingDir + fileSeparator + "未掌握单词.txt"; //常量 词库文件路径

        File OutputFile = new File(JsonFilePath);
        File OutputFile_Familiar = new File(FamiliarFilePath);
        File OutputFile_notFamiliar = new File(notFamiliarFilePath);

        if (!OutputFile.exists())
        {
            if (!OutputFile.createNewFile())
            {
                System.out.println("Error. Failed to create output file. Program quit.");
                return;
            }
        }

        BufferedWriter outputWriter = new BufferedWriter(new FileWriter(OutputFile));

        JsonMapper mapper = new JsonMapper();

        String WordListJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Main.mainDict);

        outputWriter.write(WordListJson);

        outputWriter.close();

        if (!OutputFile_Familiar.exists())
        {
            if (!OutputFile_Familiar.createNewFile())
            {
                System.out.println("Error. Failed to create output file - OutputFile_Familiar. Program quit.");
                return;
            }
        }
        if (!OutputFile_notFamiliar.exists())
        {
            if (!OutputFile_notFamiliar.createNewFile())
            {
                System.out.println("Error. Failed to create output file - OutputFile_notFamiliar. Program quit.");
                return;
            }
        }

        BufferedWriter outputWriter_Familiar = new BufferedWriter(new FileWriter(OutputFile_Familiar));
        BufferedWriter outputWriter_notFamiliar = new BufferedWriter(new FileWriter(OutputFile_notFamiliar));

        for (int i = 0; i < mainDict.getCount(); i++)
        {
            DictEntry it = mainDict.getData().get(i);
            switch (it.getFamiliar())
            {
                case familiar:
                    outputWriter_Familiar.write("WordID-" + i + "  " + it.getEnS() + '\n');
                    break;
                case notFamiliar:
                case passInMode1:
                case passInMode2:
                    StringBuilder strBuilder = new StringBuilder();
                    strBuilder.append(it.getEnS());

                    for (ChTrans itChT : it.getChS())
                    {
                        strBuilder.append("\n\t").append(itChT.getPos()).append(". ");
                        for (Iterator<String> itMean = itChT.getMean().iterator(); itMean.hasNext(); )
                        {
                            strBuilder.append(itMean.next());
                            if (itMean.hasNext())
                                strBuilder.append(',');
                            else
                                strBuilder.append(';');
                        }
                    }
                    strBuilder.append('\n');
                    outputWriter_notFamiliar.write(strBuilder.toString());
                    break;
            }
        }

        outputWriter_Familiar.close();
        outputWriter_notFamiliar.close();
    }
}
