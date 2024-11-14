package models;

import models.entity.Entity;

public record Assessment(Integer id, String name) implements Entity<Integer> {
    @Override
    public Integer getId() {
        return id;
    }
}
