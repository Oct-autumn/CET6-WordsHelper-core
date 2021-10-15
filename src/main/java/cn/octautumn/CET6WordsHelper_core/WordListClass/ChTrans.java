package cn.octautumn.CET6WordsHelper_core.WordListClass;

import java.util.ArrayList;

public class ChTrans
{
    int id;
    String pos;
    ArrayList<String> mean;

    public ChTrans()
    {
        id = 0;
        pos = "";
        mean = new ArrayList<>();
    }

    public ChTrans setId(int id)
    {
        this.id = id;
        return this;
    }

    public ChTrans setPos(String pos)
    {
        this.pos = pos;
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

    public ArrayList<String> getMean()
    {
        return mean;
    }

    public String getPos()
    {
        return pos;
    }
}
