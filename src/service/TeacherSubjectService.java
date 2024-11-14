package service;

import dao.RepositoryImpl.dbRepositories.TeacherSubjectRepository;
import models.Subject;
import object_manager.ObjectManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TeacherSubjectService {
    private final TeacherSubjectRepository teacherSubjectRepository = ObjectManager.get(TeacherSubjectRepository.class);
    private final SubjectService subjectService = ObjectManager.get(SubjectService.class);

    public List<Subject> getTeacherSubjects(int teacherId) {
        return teacherSubjectRepository.findAllWithParameter(Map.of("teacherId", teacherId)).stream().
                map(x -> subjectService.getSubjectById(x.subjectId())).
                filter(Optional::isPresent).map(Optional::get).toList();
    }
}
