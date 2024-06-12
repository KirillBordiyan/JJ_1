package org.example.hw;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Homework {

    public static void main(String[] args) {

        List<Person> personList = new ArrayList<>();
        List<String> deps = List.of("Accounting", "Marketing", "Sales department", "Production");

        for (int i = 0; i < 30; i++) {
            Person person = new Person();
            person.setAge(ThreadLocalRandom.current().nextInt(25, 50));
            person.setSalary(ThreadLocalRandom.current().nextInt(10_000, 40_000) * 1.0);
            person.setDepart(new Department(getRandom(deps)));
            person.setName("Сотрудник №" + i);
            personList.add(person);
        }

        System.out.println(personList);
        System.out.println();
//        System.out.println(findMostYoungestPerson(personList));
//        System.out.println(findMostExpensiveDepartment(personList));
//        System.out.println(groupByDepartment(personList));
//        System.out.println(groupByDepartmentName(personList));
//        System.out.println(getDepartmentOldestPerson(personList));
        System.out.println(cheapPersonsInDepartment(personList));
    }


    private static <T> T getRandom(List<T> list) {
        int i = ThreadLocalRandom.current().nextInt(0, list.size());
        return list.get(i);
    }


    /**
     * Найти самого молодого сотрудника
     */
    static Optional<Person> findMostYoungestPerson(List<Person> people) {
        return people.stream()
                .min(Comparator.comparingInt(Person::getAge));
    }

    /**
     * Найти департамент, в котором работает сотрудник с самой большой зарплатой
     */
    static Optional<Department> findMostExpensiveDepartment(List<Person> people) {
        return Optional.ofNullable(people.stream()
                .max(Comparator.comparingDouble(Person::getSalary)).get().getDepart());
    }

    /**
     * Сгруппировать сотрудников по департаментам
     */
    static Map<Department, List<Person>> groupByDepartment(List<Person> people) {
        return people.stream()
                .collect(Collectors.groupingBy(Person::getDepart));
    }

    /**
     * Сгруппировать сотрудников по названиям департаментов
     */
    static Map<String, List<Person>> groupByDepartmentName(List<Person> people) {
        return people.stream()
                .collect(Collectors.groupingBy(p -> p.getDepart().getName()));
    }

    /**
     * В каждом департаменте найти самого старшего сотрудника
     */
    static Map<String, Person> getDepartmentOldestPerson(List<Person> people) {
        return people.stream()
                .collect(Collectors.toMap(
                        p -> p.getDepart().getName(),
                        Function.identity(),
                        (p1, p2) -> p1.getAge() > p2.getAge() ? p1 : p2
                ));
    }

    /**
     * *Найти сотрудников с минимальными зарплатами в своем отделе
     * (прим. можно реализовать в два запроса)
     */
    static List<Person> cheapPersonsInDepartment(List<Person> people) {
         return people.stream()
                .collect(Collectors.toMap(
                        p -> p.getDepart().getName(),
                        Function.identity(),
                        (p1, p2) -> p1.getSalary() > p2.getSalary() ? p2 : p1
                ))
                 .values().stream().toList();

    }
}
