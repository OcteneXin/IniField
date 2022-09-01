package com.octenexin.inifield.utils;

import java.util.Comparator;

public class RandomComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        return Math.random()>0.5? 1 : -1;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}

