package cn.octautumn.CET6WordsHelper_core.DictionaryClass;

import java.util.HashMap;

public class Dictionary
{
    private String Verify;
    private int wordCount;
    private HashMap<Integer, DictEntry> DATA;

    public Dictionary()
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

    public void setWordCount(int wordCount)
    {
        this.wordCount = wordCount;
    }

    public void addEntry(int key, DictEntry entry)
    {
        DATA.put(key, entry);
    }

    public HashMap<Integer, DictEntry> getData()
    {
        return DATA;
    }

    public int getWordCount()
    {
        return wordCount;
    }

    public String getVerify()
    {
        return Verify;
    }
}
