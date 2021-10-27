package cn.octautumn.CET6WordsHelper_core.OnRunning;

import cn.octautumn.CET6WordsHelper_core.DictionaryClass.*;
import cn.octautumn.CET6WordsHelper_core.Main;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.table.Table;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static cn.octautumn.CET6WordsHelper_core.Main.*;

public class LookReview
{
    public static void ShowReview(Panel panel) throws RuntimeException
    {
        TextBox searchBox = (TextBox) panel.getChildrenList().get(1);
        Table<String> table = (Table<String>) panel.getChildrenList().get(3);

        for (int i = 0; i < mainDict.getCount(); i++)
        {
            DictEntry it = mainDict.getData().get(i);
            switch (it.getFamiliar())
            {
                case notFamiliar, N_passInMode1, N_passInMode2 -> table.getTableModel().addRow(String.valueOf(i), it.getEnS());
            }
        }

        table.setSelectAction(() -> {
            List<String> data = table.getTableModel().getRow(table.getSelectedRow());

            DictEntry it = mainDict.getData().get(Integer.parseInt(data.get(0)));

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

            new MessageDialogBuilder()
                    .setTitle("单词")
                    .setText(strBuilder.toString())
                    .build()
                    .showDialog(MultiWindowGUI);
        });

        /*

        */
    }
}
