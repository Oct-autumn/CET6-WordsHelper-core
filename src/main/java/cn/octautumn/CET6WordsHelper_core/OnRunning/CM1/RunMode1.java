package cn.octautumn.CET6WordsHelper_core.OnRunning.CM1;

import cn.octautumn.CET6WordsHelper_core.DictionaryClass.ChTrans;
import cn.octautumn.CET6WordsHelper_core.Main;
import cn.octautumn.CET6WordsHelper_core.DictionaryClass.DictEntry;
import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Label;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class RunMode1
{
    public static int getRandom(int start, int end)
    {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    public static class Countdown implements Runnable
    {
        public static boolean Running;
        Label countDownLabel;
        final func func;
        int totalTime = 300;

        public Countdown(Label label, func func)
        {
            Running = true;
            countDownLabel = label;
            this.func = func;
        }

        @Override
        public void run()
        {
            int countTime = totalTime;
            while (countTime != 0 && Running)
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

            synchronized (func)
            {
                func.Status = 2;
                func.notify();
            }
        }
    }

    public static class func implements Runnable
    {
        public int Status = 0; //0-Running 1-AnswerStop 2-TimeQuit 3-ErrorQuit 4-OtherStop/Quit
        Label wordLabel;
        ActionListBox transSelections;
        BasicWindow thisWindow;
        BasicWindow menuWindow;

        public func(BasicWindow thisWindow, Label label, ActionListBox selections, BasicWindow menuWindow)
        {
            this.thisWindow = thisWindow;
            wordLabel = label;
            transSelections = selections;
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
                    transSelections.clearItems();
                    transSelections.addItem("选择以退出", () -> {
                        thisWindow.close();
                        menuWindow.setVisible(true);
                        Main.MultiWindowGUI.setActiveWindow(menuWindow);
                        Main.MultiWindowGUI.waitForWindowToClose(menuWindow);
                    });
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
                        Countdown.Running = false;
                        wordLabel.setText("错误太多啦! 再接再厉吧.");
                        transSelections.clearItems();
                        transSelections.addItem("选择以退出", () -> {
                            thisWindow.close();
                            menuWindow.setVisible(true);
                            Main.MultiWindowGUI.setActiveWindow(menuWindow);
                            Main.MultiWindowGUI.waitForWindowToClose(menuWindow);
                        });
                        return;
                    }
                }
            }
        }
    }
}
