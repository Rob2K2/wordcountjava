package net.macaws.count;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.*;

public class App {
    public static void main(String[] args) throws IOException {

        Path path = Paths.get("words.txt"); //file route
        Map<String, Long> wordCount = Files.lines(path).flatMap(line -> Arrays.stream(line.trim().split(" "))) //split each word
            .map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim()) //convert especial chars to empty char and convert all words to lowercase
            .filter(word -> word.length() > 0) //filter if a word is greater than 0
            .map(word -> new SimpleEntry<>(word, 1))
            .collect(Collectors.groupingBy(SimpleEntry::getKey, Collectors.counting())); //count each key and value

        //sorting map by value                        
        Map<String, Long> wordCountSorted = wordCount.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) //compare and sort by descending
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> { //create a new map
            throw new IllegalStateException();
            }, LinkedHashMap::new));

        //show to the console the result
        wordCountSorted.forEach((k, v) -> System.out.println(String.format("%s => %d", k, v)));
    }
}
