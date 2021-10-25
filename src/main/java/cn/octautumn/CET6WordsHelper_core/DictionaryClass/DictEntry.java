package cn.octautumn.CET6WordsHelper_core.DictionaryClass;

import java.util.ArrayList;

public class DictEntry
{

    int familiar;
    String enS;
    ArrayList<ChTrans> chS;

    public DictEntry()
    {
        familiar = 0;
        enS = "";
        chS = new ArrayList<>();
    }

    public DictEntry setFamiliar(int familiar)
    {
        this.familiar = familiar;
        return this;
    }

    public DictEntry setEnS(String enS)
    {
        this.enS = enS;
        return this;
    }

    public DictEntry addChS(ChTrans chS)
    {
        this.chS.add(chS);
        return this;
    }

    public int getFamiliar()
    {
        return familiar;
    }

    public String getEnS()
    {
        return enS;
    }

    public ArrayList<ChTrans> getChS()
    {
        return chS;
    }
}
