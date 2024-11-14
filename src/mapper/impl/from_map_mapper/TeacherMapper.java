package mapper.impl.from_map_mapper;

import mapper.impl.from_map_mapper.base.Mapper;
import models.Teacher;

import java.util.Map;

public class TeacherMapper extends Mapper<Teacher> {
    public TeacherMapper() {
        super(Teacher.class);
    }

    public static void main(String[] args) {
        Mapper<Teacher> mapper = new TeacherMapper();
        Teacher teacher = mapper.map(Map.of("name", "Ivan", "surname", "Ivanov", "cathedraId", 10, "id", 5));
        System.out.print("");
    }
}
