package models;

import models.entity.Entity;
import utils.Pair;

public record Grade(Integer studentId, Integer subjectId, Integer points) implements Entity<Pair<Integer, Integer>> {
    @Override
    public Pair<Integer, Integer> getId() {
        return new Pair<>(studentId, subjectId);
    }
}
