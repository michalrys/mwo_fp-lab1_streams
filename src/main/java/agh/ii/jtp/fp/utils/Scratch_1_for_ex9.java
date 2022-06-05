package agh.ii.jtp.fp.utils;

import java.util.ArrayList;
import java.util.List;

public class Scratch_1_for_ex9 {
    public static void main(String[] args) {
        List<String> actors = new ArrayList<>();
        actors.add("Michal Rys");
        actors.add("Roman Polanski");

        String pair = actors.toString();
        String pairFinal = pair.substring(1, pair.length() - 1);

        System.out.println(pairFinal);

    }
}
