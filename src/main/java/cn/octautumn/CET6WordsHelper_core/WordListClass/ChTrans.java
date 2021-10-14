package cn.octautumn.CET6WordsHelper_core.WordListClass;

import java.util.List;

public class ChTrans
{
    int id;
    String Pos;
    List<String> mean;

    public ChTrans setId(int id)
    {
        this.id = id;
        return this;
    }

    public ChTrans setPos(String pos)
    {
        Pos = pos;
        return this;
    }

    public ChTrans addMean(String mean)
    {
        this.mean.add(mean);
        return this;
    }

    public int getId()
    {
        return id;
    }

    public List<String> getMean()
    {
        return mean;
    }

    public String getPos()
    {
        return Pos;
    }
}
