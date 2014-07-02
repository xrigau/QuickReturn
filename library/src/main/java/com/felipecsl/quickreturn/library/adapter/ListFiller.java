package com.felipecsl.quickreturn.library.adapter;

import java.util.ArrayList;
import java.util.List;

public class ListFiller {
    public List<Integer> allZeros(int size) {
        return getZeroFilledList(size);
    }

    private List<Integer> getZeroFilledList(int size) {
        List<Integer> itemsVerticalOffset = new ArrayList<Integer>(size);
        for (int i = 0; i < size; i++) {
            itemsVerticalOffset.add(0);
        }
        return itemsVerticalOffset;
    }
}
