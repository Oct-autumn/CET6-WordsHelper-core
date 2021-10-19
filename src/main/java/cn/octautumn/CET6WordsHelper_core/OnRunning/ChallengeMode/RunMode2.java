package cn.octautumn.CET6WordsHelper_core.OnRunning.ChallengeMode;

import cn.octautumn.CET6WordsHelper_core.DictionaryClass.ChTrans;
import cn.octautumn.CET6WordsHelper_core.DictionaryClass.DictEntry;
import cn.octautumn.CET6WordsHelper_core.Main;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class RunMode2 extends RunMode
{
    final Label wordLabel;
    final Label tipLabel;
    final ActionListBox transSelections;
    final BasicWindow thisWindow;
    final BasicWindow menuWindow;

    public RunMode2(BasicWindow thisWindow, BasicWindow menuWindow, Panel panel)
    {
        this.thisWindow = thisWindow;
        this.wordLabel = (Label) panel.getChildrenList().get(1);
        this.tipLabel = (Label) panel.getChildrenList().get(2);
        this.transSelections = (ActionListBox) panel.getChildrenList().get(3);
        this.menuWindow = menuWindow;
    }

    @Override
    public void run()
    {
        AtomicBoolean isCorrect = new AtomicBoolean(false);
        int errorCount = 0;
        int wordSum = Main.dictionary.getWordCount();
        for (int wordCount = 0; wordCount < 20; wordCount++)
        {
            int randID;
            ArrayList<DictEntry> selWord = new ArrayList<>();
            while (selWord.size() < 4)
            {
                randID = (int) (Math.random() * (wordSum));
                if (!selWord.contains(Main.dictionary.getData().get(randID)))
                {
                    selWord.add(Main.dictionary.getData().get(randID));
                }
            }

            wordLabel.setText(selWord.get(0).getEnS());
            transSelections.clearItems();

            boolean[] isIn = {false, false, false, false};
            for (int i = 0; transSelections.getItemCount() < 4; )
            {
                randID = getRandom(0, 3);
                if (isIn[randID])
                    continue;
                int wordId = randID;
                isIn[wordId] = true;
                int chTransId = (int) (Math.random() * (selWord.get(wordId).getChS().size()));
                ChTrans chTrans = selWord.get(wordId).getChS().get(chTransId);
                int meanId = (int) (Math.random() * (chTrans.getMean().size()));
                String mean = chTrans.getMean().get(meanId);
                if (randID == 0)
                {
                    transSelections.addItem(chTrans.getPos() + ". " + mean, () -> {
                        synchronized (this)
                        {
                            isCorrect.set(true);
                            Status = 0;
                            this.notify();
                        }
                    });
                }
                else
                {
                    transSelections.addItem(chTrans.getPos() + ". " + mean, () -> {
                        synchronized (this)
                        {
                            isCorrect.set(false);
                            Status = 0;
                            this.notify();
                        }
                    });
                }
            }
            transSelections.setSelectedIndex(0);

            Status = 1;
            synchronized (this)
            {
                try
                {
                    while (Status == 1)
                    {
                        this.wait();
                    }
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            if (Status == 2)
            {
                wordLabel.setText("超时啦! 再接再厉吧.");
                wordLabel.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                showExitChoice();
                return;
            }

            if (isCorrect.get())
            {
                wordLabel.setText("恭喜你，回答正确.你已答对" + (wordCount - errorCount + 1) + "题");
                try
                {

                    Thread.sleep(1000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                errorCount++;
                wordLabel.setText("对不起，回答错误. 你已答错" + errorCount + "题");
                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                if (errorCount == 2)
                {
                    Status = 3;
                    wordLabel.setText("错误太多啦! 再接再厉吧.");
                    wordLabel.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                    showExitChoice();
                    return;
                }
            }
        }
        Status = 4;
        wordLabel.setText("太棒了，你一共答对了" + (20 - errorCount) + "题");
        wordLabel.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        showExitChoice();
    }

    private void showExitChoice()
    {
        tipLabel.setText("                                  ");
        transSelections.clearItems();
        transSelections.addItem("选择以退出", () -> {
            thisWindow.close();
            menuWindow.setVisible(true);
            Main.MultiWindowGUI.setActiveWindow(menuWindow);
            Main.MultiWindowGUI.waitForWindowToClose(menuWindow);
        });
    }
}
