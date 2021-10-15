package cn.octautumn.CET6WordsHelper_core.WordListClass;

import java.util.ArrayList;

public class DictEntry
{

    int id;
    String EnS;
    ArrayList<ChTrans> ChS;

    public DictEntry()
    {
        id = 0;
        EnS = "";
        ChS = new ArrayList<>();
    }

    public DictEntry setId(int id)
    {
        this.id = id;
        return this;
    }

    public DictEntry setEnS(String enS)
    {
        EnS = enS;
        return this;
    }

    public DictEntry addChS(ChTrans chS)
    {
        ChS.add(chS);
        return this;
    }

    public int getId()
    {
        return id;
    }

    public String getEnS()
    {
        return EnS;
    }

    public ArrayList<ChTrans> getChS()
    {
        return ChS;
    }
}
