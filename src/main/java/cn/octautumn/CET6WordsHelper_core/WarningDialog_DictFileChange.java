package cn.octautumn.CET6WordsHelper_core;


import cn.octautumn.CET6WordsHelper_core.RootClass.WarningDialog;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;

import java.io.*;
import java.util.List;

//警告
public class WarningDialog_DictFileChange extends WarningDialog
{
    InputStream inputStream;

    public WarningDialog_DictFileChange(String title, String msg)
    {
        super(title, msg, Options.YES, Options.CANCEL);
    }

    @Override
    protected void YesOption()
    {
        File input;
        try
        {
            input = new FileDialogBuilder()
                    .setTitle("打开词库文件")
                    .setDescription("选择一个CET6-Words.json")
                    .setActionLabel("Open")
                    .build()
                    .showDialog(getTextGUI());
            if (input != null && input.exists())
            {
                WarningDialog_DictFileChange.this.close();
                inputStream = new FileInputStream(input);
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void NoOption()
    {
        InputStream inputStream = Main.class.getResourceAsStream("/CET6-Words.json");
        assert inputStream != null;
        this.inputStream = inputStream;
        this.close();
    }

    public InputStream getInputStream()
    {
        return inputStream;
    }
}
