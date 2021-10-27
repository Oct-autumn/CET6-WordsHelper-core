package cn.octautumn.CET6WordsHelper_core.OnRunning.ChallengeMode;

import cn.octautumn.CET6WordsHelper_core.DictionaryClass.ChTrans;
import cn.octautumn.CET6WordsHelper_core.DictionaryClass.DictEntry;
import cn.octautumn.CET6WordsHelper_core.DictionaryClass.Familiar;
import cn.octautumn.CET6WordsHelper_core.Main;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class RunMode2 extends RunMode
{
    final Label wordLabel;
    final Label wordTipLabel;
    final Label tipLabel;
    final TextBox answer;
    final BasicWindow thisWindow;
    final BasicWindow menuWindow;
    final RunMode2 thisClass;

    public RunMode2(BasicWindow thisWindow, BasicWindow menuWindow, Panel panel)
    {
        this.thisWindow = thisWindow;
        this.wordLabel = (Label) panel.getChildrenList().get(1);
        this.wordTipLabel = (Label) panel.getChildrenList().get(2);
        this.tipLabel = (Label) panel.getChildrenList().get(3);
        this.answer = (TextBox) panel.getChildrenList().get(4);
        this.menuWindow = menuWindow;
        thisClass = this;
    }

    @Override
    public void run()
    {
        AtomicBoolean isCorrect = new AtomicBoolean(false);
        int errorCount = 0;
        int wordSum = Main.mainDict.getCount();
        int selWordId;

        for (int wordCount = 0; wordCount < 20; wordCount++)
        {
            int randID;
            DictEntry correctAnswer;
            do
            {
                randID = (int) (Math.random() * (wordSum));
                correctAnswer = Main.mainDict.getData().get(randID);
            } while (correctAnswer.getFamiliar().equals(Familiar.familiar));
            selWordId = randID;

            String correctSpell = correctAnswer.getEnS();
            //System.out.println(correctSpell);
            tipLabel.setText("");
            tipLabel.setForegroundColor(TextColor.ANSI.BLACK)
                    .setText("根据提示在下面拼写该单词： ");
            answer.setText("").takeFocus();

            int tipSize;   //智能提示，减少单词过短时提示过多[Doge]
            if (correctAnswer.getFamiliar().equals(Familiar.notFamiliar)
                    || correctAnswer.getFamiliar().equals(Familiar.N_passInMode1)
                    || correctSpell.length() < 3)
                tipSize = 0;
            else if (correctSpell.length() < 6)
                tipSize = 1;
            else
                tipSize = getRandom(1, 2);

            char[] tipWord = "*".repeat(correctSpell.length()).toCharArray();

            for (int i = 0; i < tipSize; i++)
            {
                while(true)
                {
                    randID = getRandom(0, correctSpell.length() - 1);
                    if (tipWord[randID] != '*')
                        continue;
                    tipWord[randID] = correctSpell.charAt(randID);
                    break;
                }
            }
            wordLabel.setText(String.valueOf(tipWord));

            ArrayList<ChTrans> chTrans = correctAnswer.getChS();
            StringBuilder wordTipWord = new StringBuilder();
            for (ChTrans it : chTrans)
            {
                wordTipWord.append(it.getPos()).append(' ');
                ArrayList<String> mean = it.getMean();
                for (int i = 0 ; i < mean.size() - 2; i++)
                {
                    wordTipWord.append(mean.get(i)).append(',');
                }
                wordTipWord.append(mean.get(mean.size() - 1)).append(';');
                wordTipWord.append('\n');
            }
            wordTipLabel.setText("");
            wordTipLabel.setText(wordTipWord.toString());

            answer.setTextChangeListener((s, b) -> {
                if (s.endsWith("\n"))
                {
                    synchronized (this)
                    {
                        String nowAnswer = s.substring(0, s.length() - 1);
                        answer.setText(nowAnswer);
                        answer.setEnabled(false);
                        isCorrect.set(nowAnswer.equalsIgnoreCase(correctSpell));
                        Status = 0;
                        this.notify();
                    }
                }
            });

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
                        .setText("超时啦! 再接再厉吧. ");
                answer.setEnabled(false).setText("");
                tipLabel.setText("");
                wordTipLabel.setText("");
                return;
            }

            if (isCorrect.get())
            {
                tipLabel.setText("");
                tipLabel.setForegroundColor(TextColor.ANSI.GREEN)
                        .setText("恭喜你，回答正确. 你已答对" + (wordCount - errorCount + 1) + "题 ");
                switch (Main.mainDict.getData().get(selWordId).getFamiliar())
                {
                    case haveNotAppeared -> Main.mainDict.getData().get(selWordId).setFamiliar(Familiar.passInMode2);
                    case notFamiliar -> Main.mainDict.getData().get(selWordId).setFamiliar(Familiar.N_passInMode2);
                    case passInMode1, N_passInMode1 -> Main.mainDict.getData().get(selWordId).setFamiliar(Familiar.familiar);
                }

                try
                {
                    answer.setText("");
                    Thread.sleep(1000);
                    answer.setEnabled(true);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            } else
            {
                errorCount++;
                tipLabel.setText("");
                tipLabel.setForegroundColor(TextColor.ANSI.RED)
                        .setText("对不起，回答错误. 你已答错" + errorCount + "题 \n" +
                                "正确答案是：" + correctSpell + " ");
                switch (Main.mainDict.getData().get(selWordId).getFamiliar())
                {
                    case haveNotAppeared, passInMode2, N_passInMode2 -> Main.mainDict.getData().get(selWordId).setFamiliar(Familiar.notFamiliar);
                }

                try
                {
                    if (errorCount == 2)
                    {
                        Status = 3;
                        Thread.sleep(5000);
                        wordLabel.setForegroundColor(TextColor.ANSI.RED)
                                .setText("错误太多啦! 再接再厉吧.");
                        answer.setText("");
                        tipLabel.setText("");
                        wordTipLabel.setText("");
                        return;
                    }
                    Thread.sleep(5000);
                    answer.setEnabled(true);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        Status = 4;
        wordLabel.setForegroundColor(TextColor.ANSI.GREEN)
                .setText("太棒了，你一共答对了" + (20 - errorCount) + "题 ");
        answer.setEnabled(false).setText("");
        wordTipLabel.setText("");
        tipLabel.setText("");
    }
}
