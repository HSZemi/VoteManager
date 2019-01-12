/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.cgro.votemanager.util;

import java.util.ArrayList;

/**
 * @author fabian
 */
public class CheckArrayList<T> extends ArrayList<T> {

    @Override
    public String toString() {
        String result = "";
        for (T item : this) {
            result += item.toString() + "\n";

        }

        return result;
    }
}
