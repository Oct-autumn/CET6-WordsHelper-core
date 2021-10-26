package cn.octautumn.CET6WordsHelper_core.DictionaryClass;

import java.util.ArrayList;

public class DictEntry
{
    Familiar familiar;   //三位二进制 从高位到低位分别代表——是否在模式二中通过 是否在模式一中通过 是否熟悉； 0即没出现过，1即熟悉，2即仅在模式一中通过
    String enS;
    ArrayList<ChTrans> chS;

    public DictEntry()
    {
        familiar = Familiar.haveNotAppeared;
        enS = "";
        chS = new ArrayList<>();
    }

    public DictEntry setFamiliar(Familiar familiar)
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

    public Familiar getFamiliar()
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
