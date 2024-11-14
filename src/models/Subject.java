package models;

import models.entity.Entity;

public record Subject(Integer id, String name, Integer assessmentId) implements Entity<Integer> {
    @Override
    public Integer getId() {
        return id;
    }
}
