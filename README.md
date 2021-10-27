# CET6-WordsHelper-core

[![License](https://img.shields.io/github/license/Oct-autumn/CET6-WordsHelper-core)](LICENSE)

Java大作业（课设），考虑了一下决定开源造福同学们

在此感谢CSU-OSA的大佬们的帮助！

---

本程序基于Java语言编写，同时支持Windows平台和Linux（Unix）平台。

程序使用的单词词库为Json格式，具体编码格式如下例：
```Json
{
  "verify" : "verify",
  "count" : 1,
  "data" : {
    "0" : {
      "familiar" : "familiar",
      "enS" : "abandon",
      "chS" : [ {
        "id" : 0,
        "pos" : "vt",
        "mean" : [ "放弃", "遗弃" ]
      }, {
        "id" : 1,
        "pos" : "n",
        "mean" : [ "放任", "狂热" ]
      } ]
    }
  }
}
```

---

注意，本程序及其源码使用GPL-3.0证书，但有以下例外：

-“CET6words.txt”来自于 GitHub用户 mahavivo 的[常用英语词汇表](https://github.com/mahavivo/english-wordlists)仓库，不适用于该证书
