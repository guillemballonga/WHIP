package com.bernal.jonatan.whip;

import com.bernal.jonatan.whip.Models.ChatRelation;

import java.util.Comparator;

public class CustomComparator implements Comparator<ChatRelation> {
    @Override
    public int compare(ChatRelation cr1, ChatRelation cr2) {
        return cr1.getOtherUserId().compareTo(cr2.getOtherUserId());
    }
}
