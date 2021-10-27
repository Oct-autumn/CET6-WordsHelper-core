package cn.octautumn.CET6WordsHelper_core.Dialogs;


import cn.octautumn.CET6WordsHelper_core.RootClass.WarningDialog;

//警告
public class WarningDialog_DictFileChange extends WarningDialog
{
    public WarningDialog_DictFileChange(String title, String msg)
    {
        super(title, msg, Options.YES, Options.CANCEL);
    }

    @Override
    protected void YesOption()
    {
        this.option = Options.YES;
        WarningDialog_DictFileChange.this.close();
    }

    @Override
    protected void NoOption()
    {
        this.option = Options.CANCEL;
        WarningDialog_DictFileChange.this.close();
    }

    public Options getInputStream()
    {
        return this.option;
    }
}
