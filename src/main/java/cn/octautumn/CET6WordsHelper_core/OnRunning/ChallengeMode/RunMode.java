package cn.octautumn.CET6WordsHelper_core.OnRunning.ChallengeMode;

public abstract class RunMode implements Runnable
{
    public int Status = 0; //0-Running 1-AnswerStop 2-TimeQuit 3-ErrorQuit 4-OtherStop/Quit

    protected int getRandom(int start, int end)
    {
        return (int) (Math.random() * (end - start + 1) + start);
    }
}
