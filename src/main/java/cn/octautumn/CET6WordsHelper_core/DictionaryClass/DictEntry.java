package cn.octautumn.CET6WordsHelper_core.DictionaryClass;

import java.util.ArrayList;

public class DictEntry
{

    int familiar;
    String EnS;
    ArrayList<ChTrans> ChS;

    public DictEntry()
    {
        familiar = 0;
        EnS = "";
        ChS = new ArrayList<>();
    }

    public DictEntry isFamiliar(int familiar)
    {
        this.familiar = familiar;
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

    public int getFamiliar()
    {
        return familiar;
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
