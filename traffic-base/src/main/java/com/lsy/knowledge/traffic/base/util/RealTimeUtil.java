package com.lsy.knowledge.traffic.base.util;



import com.lsy.knowledge.traffic.base.domain.Link;
import com.lsy.knowledge.traffic.base.domain.Trie;

import java.io.Serializable;
import java.util.Map;

public class RealTimeUtil implements Serializable {

    private static final long serialVersionUID = -8970773787285938163L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Map<String, Boolean> getCrossLinksHash() {
        return crossLinksHash;
    }

    public Map<String, Link> getLinkAttrs() {
        return linkAttrs;
    }

    public Trie getTrie() {
        return trie;
    }

    public void setCrossLinksHash(Map<String, Boolean> crossLinksHash) {
        this.crossLinksHash = crossLinksHash;
    }

    public void setLinkAttrs(Map<String, Link> linkAttrs) {
        this.linkAttrs = linkAttrs;
    }

    public void setTrie(Trie trie) {
        this.trie = trie;
    }

    // 当前版本的crossLink
    private Map<String, Boolean> crossLinksHash;

    // 当前版本的linkAttrs
    private Map<String, Link> linkAttrs;

    // 当前版本的全城flow构造的trie树
    private Trie trie;

    public FreeUtil getFreeUtil() {
        return freeUtil;
    }

    public void setFreeUtil(FreeUtil freeUtil) {
        this.freeUtil = freeUtil;
    }

    private FreeUtil freeUtil;
}
