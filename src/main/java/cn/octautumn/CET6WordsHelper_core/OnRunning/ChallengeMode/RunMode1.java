package cn.octautumn.CET6WordsHelper_core.OnRunning.ChallengeMode;

import cn.octautumn.CET6WordsHelper_core.DictionaryClass.ChTrans;
import cn.octautumn.CET6WordsHelper_core.DictionaryClass.DictEntry;
import cn.octautumn.CET6WordsHelper_core.DictionaryClass.Familiar;
import cn.octautumn.CET6WordsHelper_core.Main;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class RunMode1 extends RunMode
{
        final Label wordLabel;
        final Label tipLabel;
        final ActionListBox transSelections;
        final BasicWindow thisWindow;
        final BasicWindow menuWindow;

        public RunMode1(BasicWindow thisWindow, BasicWindow menuWindow, Panel panel)
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
            int wordSum = Main.mainDict.getCount();
            int selWordId = -1;
            String correctMeaning = "";

            for (int wordCount = 0; wordCount < 20; wordCount++)
            {
                int randID;
                ArrayList<DictEntry> selWord = new ArrayList<>();
                while (selWord.size() < 4)
                {
                    randID = (int) (Math.random() * (wordSum));
                    if (!Main.mainDict.getData().get(randID).getFamiliar().equals(Familiar.familiar)
                            && !selWord.contains(Main.mainDict.getData().get(randID)))
                    {
                        if (selWord.isEmpty())
                        {
                            selWordId = randID;
                            selWord.add(Main.mainDict.getData().get(randID));
                            wordLabel.setText(Main.mainDict.getData().get(randID).getEnS());
                        }
                        else
                        {
                            selWord.add(Main.mainDict.getData().get(randID));
                        }
                    }
                }

                tipLabel.setText("");
                tipLabel.setForegroundColor(TextColor.ANSI.BLACK)
                        .setText("????????????????????????????????????????????????????????? ");
                transSelections.clearItems();

                boolean[] isIn = {false, false, false, false};
                while (transSelections.getItemCount() < 4)
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
                        correctMeaning = chTrans.getPos() + ". " + mean;
                        transSelections.addItem(correctMeaning, () -> {
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
                transSelections.setSelectedIndex(0).takeFocus();

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
                    wordLabel.setForegroundColor(TextColor.ANSI.RED)
                            .setText("?????????! ???????????????. ");
                    tipLabel.setText("");
                    tipLabel.setText("");
                    transSelections.clearItems();
                    return;
                }

                if (isCorrect.get())
                {
                    tipLabel.setText("");
                    tipLabel.setForegroundColor(TextColor.ANSI.GREEN)
                            .setText("????????????????????????. ????????????" + (wordCount - errorCount + 1) + "??? ");
                    switch (Main.mainDict.getData().get(selWordId).getFamiliar())
                    {
                        case haveNotAppeared -> Main.mainDict.getData().get(selWordId).setFamiliar(Familiar.passInMode1);
                        case notFamiliar -> Main.mainDict.getData().get(selWordId).setFamiliar(Familiar.N_passInMode1);
                        case passInMode2, N_passInMode2 -> Main.mainDict.getData().get(selWordId).setFamiliar(Familiar.familiar);
                    }

                    try
                    {
                        transSelections.clearItems();
                        Thread.sleep(1000);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    errorCount++;
                    tipLabel.setText("");
                    tipLabel.setForegroundColor(TextColor.ANSI.RED)
                            .setText("????????????????????????. ????????????" + errorCount + "??? \n" +
                                    "??????????????????" + correctMeaning + " ");
                    switch (Main.mainDict.getData().get(selWordId).getFamiliar())
                    {
                        case haveNotAppeared, passInMode1, N_passInMode1 -> Main.mainDict.getData().get(selWordId).setFamiliar(Familiar.notFamiliar);
                    }

                    transSelections.clearItems();
                    try
                    {
                        if (errorCount == 2)
                        {
                            Status = 3;
                            Thread.sleep(5000);
                            wordLabel.setForegroundColor(TextColor.ANSI.RED)
                                    .setText("???????????????! ???????????????. ");
                            tipLabel.setText("");
                            transSelections.clearItems();
                            return;
                        }
                        Thread.sleep(5000);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            Status = 4;
            wordLabel.setForegroundColor(TextColor.ANSI.GREEN)
                    .setText("??????????????????????????????" + (20 - errorCount) + "??? ");
            tipLabel.setText("");
            transSelections.clearItems();
        }

}
