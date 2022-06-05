package agh.ii.jtp.fp;

import agh.ii.jtp.fp.dal.ImdbTop250;
import agh.ii.jtp.fp.model.Movie;
import agh.ii.jtp.fp.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface PlayWithMovies {

    /**
     * Returns the movies (only titles) directed (or co-directed) by a given director
     */
    static Set<String> ex01(String director) {
        if (ImdbTop250.movies().isEmpty()) {
            return new HashSet<>();
        }

        return ImdbTop250.movies().get().stream()
                .filter(s -> s.directors().contains(director))
                .map(s -> s.title())
                .collect(Collectors.toSet());
    }

    /**
     * Returns the movies (only titles) in which an actor played
     */
    static Set<String> ex02(String actor) {
        return ImdbTop250.movies().get().stream()
                .filter(s -> s.actors().contains(actor))
                .map(s -> s.title())
                .collect(Collectors.toSet());
    }

    /**
     * Returns the number of movies per director (as a map)
     */
    static Map<String, Long> ex03() {
        //TODO: simple test --> go to Scratch_1_for_ex3
        return ImdbTop250.movies().get().stream()
                .map(s -> Utils.oneToManyByDirector(s))
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        key -> key.directors().get(0),
                        value -> 1L,
                        (previousValue, nextValue) -> previousValue + 1L
                ));
    }

    /**
     * Returns the 10 directors with the most films on the list
     */
    static Map<String, Long> ex04() {
        Map<String, Long> directorsAmountOfFilms = ImdbTop250.movies().get().stream()
                .map(s -> Utils.oneToManyByDirector(s))
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        key -> key.directors().get(0),
                        value -> 1L,
                        (previousValue, nextValue) -> previousValue + 1L
                ));


        return directorsAmountOfFilms.entrySet().stream()
                //TODO: simple test --> go to Scratch_1_for_ex4
                .sorted((s1, s2) -> s1.getValue() >= s2.getValue() ? -1 : 1)
                .limit(10)
                .collect(Collectors.toMap(
                        eSet -> eSet.getKey(),
                        eSet -> eSet.getValue(),
                        (previous, next) -> previous,
                        LinkedHashMap::new
                ));
    }

    /**
     * Returns the movies (only titles) made by each of the 10 directors found in {@link PlayWithMovies#ex04 ex04}
     */
    static Map<String, Set<String>> ex05() {
        throw new RuntimeException("ex05 is not implemented!");
    }

    /**
     * Returns the number of movies per actor (as a map)
     */
    static Map<String, Long> ex06() {
        throw new RuntimeException("ex06 is not implemented!");
    }

    /**
     * Returns the 9 actors with the most films on the list
     */
    static Map<String, Long> ex07() {
        throw new RuntimeException("ex07 is not implemented!");
    }

    /**
     * Returns the movies (only titles) of each of the 9 actors from {@link PlayWithMovies#ex07 ex07}
     */
    static Map<String, Set<String>> ex08() {
        throw new RuntimeException("ex08 is not implemented!");
    }

    /**
     * Returns the 5 most frequent actor partnerships (i.e., appearing together most often)
     */
    static Map<String, Long> ex09() {
        throw new RuntimeException("ex08 is not implemented!");
    }

    /**
     * Returns the movies (only titles) of each of the 5 most frequent actor partnerships
     */
    static Map<String, Set<String>> ex10() {
        throw new RuntimeException("ex10 is not implemented!");
    }
}


