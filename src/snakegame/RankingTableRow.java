package snakegame;

import java.util.Date;

public class RankingTableRow implements Comparable {
    String username;
    int score;
    Date date;
    public RankingTableRow(String u, int s, Date d) {
        username = u; score = s; date = d;
    }

    @Override
    public int compareTo(Object o) {
        RankingTableRow row = (RankingTableRow)o;
        if (score != row.score) return -(((Integer)score).compareTo(row.score));
        if (date.compareTo(row.date) != 0) return date.compareTo(row.date);
        return username.compareTo(row.username);
    }
}