package service;

import dao.RepositoryImpl.dbRepositories.SortedStudentRepository;
import object_manager.ObjectManager;

public class SortedStudentService extends StudentService {
    public SortedStudentService() {
        super();
        studentRepository = ObjectManager.get(SortedStudentRepository.class);
    }
}
