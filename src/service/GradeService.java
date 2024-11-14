package service;

import dao.RepositoryImpl.dbRepositories.GradeRepository;
import models.Grade;
import object_manager.ObjectManager;

import java.util.List;
import java.util.Map;

public class GradeService {
    private final GradeRepository gradeRepository = ObjectManager.get(GradeRepository.class);

    public List<Grade> getStudentMarks(int studentId) {
        return gradeRepository.findAllWithParameter(Map.of("studentId", studentId));
    }
}
