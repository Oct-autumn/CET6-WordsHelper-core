package cn.octautumn.CET6WordsHelper_core.WordListClass;

import java.util.HashMap;
import java.util.List;

public class WordList
{
    private class Entry
    {
        private class ChTrans
        {
            int id;
            String Pos;
            List<String> mean;

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

        int id;
        String EnS;
        List<ChTrans> ChS;

        public int getId()
        {
            return id;
        }

        public String getEnS()
        {
            return EnS;
        }

        public List<ChTrans> getChS()
        {
            return ChS;
        }
    }

    String Verify;
    HashMap<Integer, Entry> data;

    public HashMap<Integer, Entry> getData()
    {
        return data;
    }

    public String getVerify()
    {
        return Verify;
    }
}
