package cn.octautumn.CET6WordsHelper_core.RootClass;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;

import java.util.List;

public abstract class WarningDialog extends DialogWindow
{
    protected WarningDialog(String title, String msg, Boolean enableCancelButton)
    {
        super(title);
        this.setHints(List.of(Window.Hint.CENTERED));

        Panel warningPanel = new Panel();
        if (enableCancelButton)
        {
            warningPanel.setLayoutManager(new GridLayout(2));
            warningPanel.addComponent(new Label(msg));
            warningPanel.addComponent(new EmptySpace());
            warningPanel.addComponent(new Button(" Y 好 ", this::YesOption));
            warningPanel.addComponent(new Button(" C 取消 ", this::NoOption));
        }
        else
        {
            warningPanel.setLayoutManager(new GridLayout(1));
            warningPanel.addComponent(new Label(msg));
            warningPanel.addComponent(new Button(" Y 好 ", this::YesOption));
        }

        this.setComponent(warningPanel);
    }

    protected abstract void YesOption();

    protected abstract void NoOption();
}
