package me.unlegitiment.test4.objects;

import javax.annotation.Nullable;

public class Rank {
    private String name;
    private String type;
    private int rankVal;
    private final String prefix;
    private final String suffix;
    private final String prefixColorz;
    private final String suffixColorz;
    public Rank(String name, String type, int rankVal, @Nullable String prefix,@Nullable String suffix,@Nullable String prefixColorz,@Nullable String suffixColorz){
        this.name = name;
        this.type = type;
        this.rankVal = rankVal;
        this.prefix = prefix;
        this.suffix = suffix;
        this.prefixColorz = prefixColorz;
        this.suffixColorz = suffixColorz;
    }
    public String getName() {
        return name;
    }

    public Rank setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public Rank setType(String type) {
        this.type = type;
        return this;
    }

    public int getRankVal() {
        return rankVal;
    }

    public Rank setRankVal(int rankVal) {
        this.rankVal = rankVal;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getPrefixColorz() {
        return prefixColorz;
    }

    public String getSuffixColorz() {
        return suffixColorz;
    }
}
