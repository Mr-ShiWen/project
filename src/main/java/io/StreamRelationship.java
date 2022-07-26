package io;

import istio.v1.auth.Ca;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class StreamRelationship {

    public int numMatchingSubseq(String s, String[] words) {
        int ans=0;
        char[] chs = s.toCharArray();
        for (String word : words) {
            if(isSubSeq(chs,0,word.toCharArray(),0)) ans++;
        }
        return ans;
    }

    private boolean isSubSeq(char[] s, int p1, char[] word, int p2){
        if(p2==word.length) return true;
        else if(p1==s.length) return false;
        else if(word.length-p2>s.length-p1) return false;
        else if(s[p1]==word[p2]) return isSubSeq(s, p1+1, word, p2+1);
        else return isSubSeq(s, p1+1, word, p2);
    }


}
