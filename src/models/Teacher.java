package models;

import models.entity.Entity;

public record Teacher(Integer id, String name, String surname, String phoneNumber, String login,
                      Integer passwordHash, Integer cathedraId) implements Entity<Integer> {
    @Override
    public Integer getId() {
        return id;
    }
}
