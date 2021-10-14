package cn.octautumn.CET6WordsHelper_core;


import cn.octautumn.CET6WordsHelper_core.RootClass.WarningDialog;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

//警告
public class WarningDialog_WordListDamage extends WarningDialog
{
    protected WarningDialog_WordListDamage(String title, String msg)
    {
        super(title, msg, true);
    }

    @Override
    protected void YesOption()
    {
        File input;
        do
        {
            input = new FileDialogBuilder()
                    .setTitle("打开词库文件")
                    .setDescription("选择一个CET6-Words.json")
                    .setActionLabel("Open")
                    .build()
                    .showDialog(getTextGUI());
        }while(!input.exists());
        OnLoad.wordListJsonFile = input;
        WarningDialog_WordListDamage.this.close();
    }

    @Override
    protected void NoOption()
    {
        System.out.println("no");
        InputStream inputStream = Main.class.getResourceAsStream("/CET6-Words.json");
        assert inputStream != null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

    }
}
