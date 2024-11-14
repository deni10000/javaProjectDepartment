package models;

import models.entity.Entity;
import utils.Pair;

public record TeacherSubject(Integer subjectId, Integer teacherId) implements Entity<Pair<Integer, Integer>> {
    @Override
    public Pair<Integer, Integer> getId() {
        return new Pair<>(subjectId, teacherId);
    }
}
