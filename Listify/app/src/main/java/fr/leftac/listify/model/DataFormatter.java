package fr.leftac.listify.model;

import io.realm.RealmList;

public class DataFormatter {

    public static String duration(int ms) {
        int durationValue = ms / 1000;
        return durationValue / 60 + ":" + (durationValue % 60 < 10 ? "0" : "") + durationValue % 60;

    }

    public static String popularity(int p) {
        return p + " %";
    }

    public static String genres(RealmList<String> list) {
        String s = "";
        for (String g : list) {
            s = s.concat(g + "\n");
        }
        return s;
    }

    public static String followers(int f) {
        String s = f + "";
        StringBuilder res = new StringBuilder();
        int space = 0;
        for (int i = 0; i < s.length(); i++) {
            res.append(s.charAt(s.length() - 1 - i));
            space++;
            if (space == 3) {
                space = 0;
                res.append(" ");
            }

        }
        res.reverse();
        return res.toString();
    }
}
