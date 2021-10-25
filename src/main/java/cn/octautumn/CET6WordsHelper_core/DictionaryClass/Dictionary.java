package cn.octautumn.CET6WordsHelper_core.DictionaryClass;

import java.util.HashMap;

public class Dictionary
{
    String verify;
    int count;
    HashMap<Integer, DictEntry> data;

    public Dictionary()
    {
        verify = "";
        data = new HashMap<>();
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public void setVerify(String verify)
    {
        this.verify = verify;
    }

    public void setData(HashMap<Integer, DictEntry> data)
    {
        this.data = data;
    }

    public void addEntry(int id, DictEntry entry)
    {
        data.put(id, entry);
    }

    public String getVerify()
    {
        return verify;
    }

    public int getCount()
    {
        return count;
    }

    public HashMap<Integer, DictEntry> getData()
    {
        return data;
    }
}
