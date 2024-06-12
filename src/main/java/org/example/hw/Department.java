package org.example.hw;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private String name;

    @Override
    public String toString() {
        return "Department(" + name + ")";
    }
}
