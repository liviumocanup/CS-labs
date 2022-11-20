package com.utm.cslabs.classic.implementations.additionals;

import java.util.ArrayList;
import java.util.List;

public class Polybius {

    public String polybiusCipher(String s) {
        List<Integer> result = new ArrayList<>();
        int row, col;

        for (int i = 0; i < s.length(); i++) {

            row = (int) Math.ceil((s.charAt(i) - 'a') / 5) + 1;

            col = ((s.charAt(i) - 'a') % 5) + 1;

            if (s.charAt(i) == 'k') {
                row = row - 1;
                col = 5 - col + 1;
            } else if (s.charAt(i) >= 'j') {
                if (col == 1) {
                    col = 6;
                    row = row - 1;
                }
                col = col - 1;
            }
            result.add(row);
            result.add(col);
        }

        return result.toString();
    }
}
