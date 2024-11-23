package service;

import dao.RepositoryImpl.dbRepositories.GradeRepository;
import models.Grade;
import object_manager.ObjectManager;
import utils.Pair;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GradeService {
    private final GradeRepository gradeRepository = ObjectManager.get(GradeRepository.class);

    public List<Grade> getStudentMarks(int studentId) {
        return gradeRepository.findAllWithParameter(Map.of("studentId", studentId));
    }

    public Optional<Grade> getStudentByStudentAndSubject(int studentId, int subjectId) {
        return gradeRepository.findById(new Pair<>(studentId, subjectId));
    }

    public boolean updateGrade(Grade grade) {
        if (grade.points() < 0 || grade.points() > 100) {
            return false;
        }

        return gradeRepository.update(grade);
    }

    public boolean addGrade(Grade grade) {
        if (grade.points() < 0 || grade.points() > 100) {
            return false;
        }

        gradeRepository.save(grade);
        return true;
    }

    public boolean deleteGrade(int studentId, int subjectId) {
        return gradeRepository.delete(new Pair<>(studentId, subjectId));
    }
}
