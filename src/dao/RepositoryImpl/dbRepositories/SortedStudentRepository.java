package dao.RepositoryImpl.dbRepositories;

public class SortedStudentRepository extends StudentRepository {
    @Override
    protected String getTableName() {
        return "sorted_student";
    }

}
