package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IdGenerator {
    public static int nextId(List<Integer> idList) {
        var newList = new ArrayList<>(idList);
        Collections.sort(newList);
        idList = newList;
        for (int i = 1; i < idList.size(); i++) {
            if (idList.get(i) > idList.get(i - 1) + 1) {
                return idList.get(i - 1) + 1;
            }
        }
        return idList.isEmpty() ? 1 : idList.getLast() + 1;
    }
}
