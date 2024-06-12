package org.example;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<String> tags = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tags.add("Tag #" + i);
        }

        List<String> auth = List.of("Tolstoy", "Bulgakov", "Dostoevskiy", "Gogol", "Duma");
        List<Book> listBooks = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Book book = new Book();
            book.setName("Book #" + i);
            book.setAuth(getRandom(auth));

            LocalDate today = LocalDate.now();
            LocalDate randomDate = today.minusYears(ThreadLocalRandom.current().nextInt(100));
            book.setDate(randomDate);

            book.setPages(ThreadLocalRandom.current().nextInt(200, 2500));
            book.setPrice(ThreadLocalRandom.current().nextInt(1000, 100000) * 1.0);

            int tagsC = ThreadLocalRandom.current().nextInt(6);
            List<String> booktags = new ArrayList<>();
            for (int j = 0; j < tagsC; j++) {
                booktags.add(getRandom(tags));
            }
            book.setTags(booktags);


            listBooks.add(book);
        }

//        System.out.println(findMostExpensive(listBooks));
//        System.out.println(findMostPagesLess(listBooks));
//        System.out.println(findTags(listBooks));
//        System.out.println(groupByAuthor(listBooks).get("Gogol"));
//        System.out.println(getHighPriceBook(listBooks));

    }

    /**
     * для каждого автора найти его самую дорогую книгу
     */
    static Map<String, Book> getHighPriceBook(List<Book> list) {
        return list.stream()
                .collect(Collectors.toMap(
                        Book::getAuth, // b -> b.getAuth()
                        Function.identity(), // b -> b
                        (b1, b2) -> b1.getPrice() > b2.price ? b1 : b2
                    ));
    }

    /**
     * сгруппировать по авторам/ groupBy(x)
     */
    static Map<String, List<Book>> groupByAuthor(List<Book> list) {
        return list.stream()
                .collect(Collectors.groupingBy(Book::getAuth));
    }


    /**
     * получить теги 5 самых молодых книг/ flatMap
     */
    static Set<String> findTags(List<Book> list) {
        return list.stream()
                .sorted(Comparator.comparing(Book::getDate))
                .limit(5)
                .flatMap(b -> b.getTags().stream())
                .collect(Collectors.toSet());
    }


    /**
     * 5 самых маленьких
     */
    static List<Book> findMostPagesLess(List<Book> list) {
        return list.stream()
                .sorted(Comparator.comparingInt(Book::getPages))
                .limit(5)
                .toList();
    }

    /**
     * самая дорогая книга
     */
    static Book findMostExpensive(List<Book> list) {
//        return list.stream()
//                .max((b1, b2) -> Double.compare(b1.getPrice(), b2.getPrice())).get();

        return list.stream()
                .sorted((b1, b2) -> Double.compare(b2.getPrice(), b1.getPrice()))
                .peek(System.out::println)
                .toList()
                .get(0);
    }

    private static <T> T getRandom(List<T> list) {
        int i = ThreadLocalRandom.current().nextInt(0, list.size());
        return list.get(i);
    }


    static class Book {
        private String name;
        private String auth;
        private LocalDate date;
        private int pages;
        private double price;
        private List<String> tags;


        @Override
        public String toString() {
            return "Book{" +
                    "name='" + name + '\'' +
                    ", auth='" + auth + '\'' +
                    ", date=" + date +
                    ", pages=" + pages +
                    ", price=" + price +
                    ", tags=" + tags +
                    "}";
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }


}