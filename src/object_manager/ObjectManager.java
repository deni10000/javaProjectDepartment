package object_manager;

import dao.RepositoryImpl.listRepositories.CathedraRepository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ObjectManager {
    /*  static {
          Map<Class<?>, Object> classes = new HashMap<>();

          classes.put(AssessmentRepository.class, new AssessmentRepository());
          classes.put(GradeRepository.class, new GradeRepository());
          classes.put(StudentRepository.class, new StudentRepository());
          classes.put(SubjectRepository.class, new SubjectRepository());
          classes.put(TeacherSubjectRepository.class, new TeacherSubjectRepository());
          classes.put(TeacherRepository.class, new TeacherRepository());

          classes.put(GradeService.class, new GradeService());
          classes.put(StudentService.class, new StudentService());
          classes.put(SubjectService.class, new SubjectService());
          classes.put(TeacherService.class, new TeacherService());
          classes.put(TeacherSubjectService.class, new TeacherSubjectService());

          classes.put(AddStudentController.class, new AddStudentController());
          classes.put(AuthorizationController.class, new AuthorizationController());
          classes.put(StudentController.class, new StudentController());
          classes.put(StudentListController.class, new StudentListController());
          classes.put(StudentMarksController.class, new StudentMarksController());
          classes.put(TeacherController.class, new TeacherController());
          classes.put(TeacherSubjectController.class, new TeacherSubjectController());
      }
  */
    private static final ConcurrentMap<Class<?>, Object> classes = new ConcurrentHashMap<>();

    static public <T> T get(Class<T> clazz) {
        if (!classes.containsKey(clazz)) {
            synchronized (ObjectManager.class) {
                if (!classes.containsKey(clazz)) {
                    try {
                        classes.put(clazz, clazz.newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return (T) classes.get(clazz);
    }


}
