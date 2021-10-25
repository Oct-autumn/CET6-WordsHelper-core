package cn.octautumn.CET6WordsHelper_core.RootClass;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;

import java.util.Iterator;
import java.util.List;

public abstract class WarningDialog extends DialogWindow
{
    public enum Options
    {
        YES, CANCEL
    }

    Options option = Options.YES;

    protected WarningDialog(String title, String msg, Options... enabledButton)
    {
        super(title);
        this.setHints(List.of(Window.Hint.CENTERED));

        Panel warningPanel = new Panel().setLayoutManager(new GridLayout(1));

        warningPanel.addComponent(0, new Panel().setLayoutManager(new GridLayout(1)));
        warningPanel.addComponent(1, new Panel().setLayoutManager(new GridLayout(enabledButton.length)));

        ((Panel)warningPanel.getChildrenList().get(0)).addComponent(new Label(msg));

        for (Options it : enabledButton)
        {
            switch (it)
            {
                case YES ->
                        ((Panel)warningPanel.getChildrenList().get(1)).addComponent(new Button(" Y 好 ", this::YesOption));
                case CANCEL ->
                        ((Panel)warningPanel.getChildrenList().get(1)).addComponent(new Button(" C 取消 ", this::NoOption));
            }
        }
        this.setComponent(warningPanel);
    }

    protected abstract void YesOption();

    protected abstract void NoOption();
}
