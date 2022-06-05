package agh.ii.jtp.fp.utils;

import agh.ii.jtp.fp.dal.ImdbTop250;
import agh.ii.jtp.fp.model.Movie;

import java.util.*;
import java.util.stream.Collectors;

public class Scratch_1_for_ex4 {
    public static void main(String[] args) {
        System.out.println(">>>>>>>>> TEST ONLY for ex4");

        List<Person> people = new ArrayList<>();
        people.add(new Person("Tom", 10));
        people.add(new Person("Tom", 10));
        people.add(new Person("Michal", 2));
        people.add(new Person("Michal", 5));
        people.add(new Person("Michal", 1));
        people.add(new Person("Ala", 2));

        Map<String, Integer> collect1 = people.stream()
                .collect(Collectors.toMap(
                        key -> key.getName(),
                        value -> 1,
                        (previousValue, nextValue) -> 1 + previousValue)
                );

        System.out.println(collect1);

        Set<Map.Entry<String, Integer>> entries = collect1.entrySet();

        // TODO: https://stackoverflow.com/questions/29567575/sort-map-by-value-using-lambdas-and-streams
        Map<String, Integer> collect = collect1.entrySet().stream()
                .sorted((s1, s2) -> s1.getValue() >= s2.getValue() ? -1 : 1)  //FIXME: my own comparator
//                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())  //FIXME: stackoverflow
                .limit(2)
                .collect(Collectors.toMap(
                        key -> key.getKey(),
                        value -> value.getValue(),
                        (previousValue, newValue) -> previousValue,  //FIXME: for this case it is dummy
                        LinkedHashMap::new  //FIXME: must be added in order to keep order of adding key-value
                ));
//                .forEach(System.out::println);  //FIXME: when just print, it is sorted, but when created --> problem

        System.out.println(collect);
    }
}