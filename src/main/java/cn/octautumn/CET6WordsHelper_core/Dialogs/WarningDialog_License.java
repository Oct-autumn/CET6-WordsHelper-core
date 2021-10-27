package cn.octautumn.CET6WordsHelper_core.Dialogs;

import cn.octautumn.CET6WordsHelper_core.RootClass.WarningDialog;

public class WarningDialog_License extends WarningDialog
{
    public WarningDialog_License()
    {
        super("License info", """
                        English WordsHelper
                        Copyright (C) 2021  Oct_Autumn

                            This program is free software: you can redistribute it and/or modify
                            it under the terms of the GNU General Public License as published by
                            the Free Software Foundation, either version 3 of the License, or
                            (at your option) any later version.

                            This program is distributed in the hope that it will be useful,
                            but WITHOUT ANY WARRANTY; without even the implied warranty of
                            MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
                            GNU General Public License for more details.

                            You should have received a copy of the GNU General Public License
                            along with this program. If not, see <https://www.gnu.org/licenses/>.

                            Contact me by E-mail: lljkiff@163.com"""
                , Options.YES);
    }

    @Override
    protected void YesOption()
    {
        this.close();
    }

    @Override
    protected void NoOption() {}
}
