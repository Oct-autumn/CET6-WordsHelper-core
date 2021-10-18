package cn.octautumn.CET6WordsHelper_core.OnRunning.CM1;

import cn.octautumn.CET6WordsHelper_core.Main;
import cn.octautumn.CET6WordsHelper_core.DictionaryClass.DictEntry;
import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.Label;

import java.util.ArrayList;

public class RunMode1
{
    public static class Countdown implements Runnable
    {
        Label countDownLabel;
        Thread func;
        int totalTime = 300;

        public Countdown(Label label, Thread func)
        {
            countDownLabel = label;
            this.func = func;
        }

        @Override
        public void run()
        {
            int countTime = totalTime;
            while (countTime != 0)
            {
                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                countTime--;
                int tmp_cal = (countTime) * 100 / totalTime;
                countDownLabel.setText("█".repeat(Math.max(0, tmp_cal / 5)) +
                        "▒".repeat(Math.max(0, 20 - (tmp_cal / 5))) + String.format("  %3ds", countTime));
            }

            countDownLabel.setText("时间到！挑战结束");
        }
    }

    public static class func implements Runnable
    {
        Label wordLabel;
        ActionListBox transSelections;

        public func(Label label, ActionListBox selections)
        {
            wordLabel = label;
            transSelections = selections;
        }

        @Override
        public void run()
        {
            int errorCount = 0;
            int wordSum = Main.dictionary.getWordCount();
            for (int wordCount = 0; wordCount < 20; wordCount++)
            {
                ArrayList<DictEntry> selWord = new ArrayList<>();
                for (int i = 0; i < 4; i++)
                {
                    int randID = (int) (Math.random() * (wordSum));
                    if (!selWord.contains(Main.dictionary.getData().get(randID)))
                    {
                        selWord.add(Main.dictionary.getData().get(randID));
                    }
                }

                int randID;

                wordLabel.setText(selWord.get(0).getEnS());
            }
        }
    }
}
