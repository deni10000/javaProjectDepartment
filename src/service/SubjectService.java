package service;

import dao.RepositoryImpl.dbRepositories.SubjectRepository;
import models.Subject;
import object_manager.ObjectManager;

import java.util.Optional;

public class SubjectService {
    private final SubjectRepository subjectRepository = ObjectManager.get(SubjectRepository.class);

    public Optional<Subject> getSubjectById(int subjectId) {
        return subjectRepository.findById(subjectId);
    }
}
