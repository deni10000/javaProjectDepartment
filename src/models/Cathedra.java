package models;

import models.entity.Entity;

public record Cathedra(Integer id, String name, String phoneNumber) implements Entity<Integer> {
    @Override
    public Integer getId() {
        return id;
    }
}
