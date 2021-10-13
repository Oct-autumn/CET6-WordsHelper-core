package cn.octautumn.CET6WordsHelper_core;


import cn.octautumn.CET6WordsHelper_core.RootClass.WarningDialog;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;

import java.io.File;

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
        File input = null;
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
    }
}
