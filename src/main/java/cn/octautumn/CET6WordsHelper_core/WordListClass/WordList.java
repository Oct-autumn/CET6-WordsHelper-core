package cn.octautumn.CET6WordsHelper_core.WordListClass;

import java.util.HashMap;

public class WordList
{
    String Verify;
    HashMap<Integer, DictEntry> DATA;

    public WordList()
    {
        Verify = "";
        DATA = new HashMap<>();
    }

    public void setVerify(String verify)
    {
        Verify = verify;
    }

    public void setDATA(HashMap<Integer, DictEntry> data)
    {
        this.DATA = data;
    }

    public void addEntry(int key, DictEntry entry)
    {
        DATA.put(key, entry);
    }

    public HashMap<Integer, DictEntry> getData()
    {
        return DATA;
    }

    public String getVerify()
    {
        return Verify;
    }
}
