package cn.octautumn.CET6WordsHelper_core;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class warningDialog extends DialogWindow
{
    protected warningDialog(String title)
    {
        super(title);
        this.setHints(List.of(Window.Hint.CENTERED));

        Panel warningPanel = new Panel();
        warningPanel.setLayoutManager(new GridLayout(2));

        warningPanel.addComponent(new Label("未发现词库文件（CET6-Words.json）\n是否从现有txt导入？"));
        warningPanel.addComponent(new EmptySpace());
        warningPanel.addComponent(new Button(" Y 好 "));
        warningPanel.addComponent(new Button(" C 取消 "));

        this.setComponent(warningPanel);
    }

    public void setMessage(String msg)
    {

    }

}
