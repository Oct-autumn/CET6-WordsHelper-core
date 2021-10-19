package cn.octautumn.CET6WordsHelper_core.OnRunning.ChallengeMode;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

public class CountDown implements Runnable
{
    Panel panel;
    final RunMode func;
    int totalTime;

    public CountDown(Panel panel, RunMode func, int totalTime)
    {
        this.panel = panel;
        this.func = func;
        this.totalTime = totalTime;
    }

    @Override
    public void run()
    {
        int countTime = totalTime;
        while (countTime != 0 && func.Status != 3 && func.Status != 4)
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
            ((Label) panel.getChildrenList().get(0)).setText("█".repeat(Math.max(0, tmp_cal / 5)) +
                    "▒".repeat(Math.max(0, 20 - (tmp_cal / 5))) + String.format("  %3ds", countTime));
        }

        synchronized (func)
        {
            func.Status = 2;
            func.notify();
        }
    }
}
