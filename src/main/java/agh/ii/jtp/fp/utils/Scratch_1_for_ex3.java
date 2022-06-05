package agh.ii.jtp.fp.utils;

import agh.ii.jtp.fp.dal.ImdbTop250;
import agh.ii.jtp.fp.model.Movie;

import java.util.*;
import java.util.stream.Collectors;

public class Scratch_1_for_ex3 {
    public static void main(String[] args) {
        System.out.println(">>>>>>>>> TEST ONLY");

        List<Person> people = new ArrayList<>();
        people.add(new Person("Tom", 3));
        people.add(new Person("Michal", 2));
        people.add(new Person("Michal", 5));
        people.add(new Person("Ala", 2));


        // TODO: http://java.edu.pl/java/newJava8/2.stream.php --> Wyrażenie (oldValue,newValue) -> String.join
        Map<String, String> collect = people.stream()
                .collect(Collectors.toMap(
                        s -> s.getName(),
                        s -> String.valueOf(s.getAge()),
                        (String previous, String next) -> String.join(", ", previous.toString(), next.toString())
                ));

        Map<String, Integer> collect1 = people.stream()
                .collect(Collectors.toMap(
                        key -> key.getName(),
                        value -> 1,
                        (previousValue, nextValue) -> 1 + previousValue)
                );

        System.out.println(collect);
        System.out.println(collect1);

    }

    private void testA() {
        Optional<List<Movie>> movies = ImdbTop250.movies();

        String author = "Christopher Nolan";

        // Returns the movies (only titles) directed (or co-directed) by a given director
        Set<String> foundMovies = movies.get().stream()
                .filter(s -> s.directors().contains(author))
                .map(s -> s.title())
                .collect(Collectors.toSet());
    }

}
