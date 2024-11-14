package models;

import models.entity.Entity;

public record Student(Integer id, String name, String surname, Integer course, Integer groupNumber,
                      String login, Integer passwordHash, Integer cathedraId) implements Entity<Integer> {
    @Override
    public Integer getId() {
        return id;
    }
}
