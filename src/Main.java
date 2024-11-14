import controller.console.AuthorizationController;
import object_manager.ObjectManager;

public class Main {
    public static void main(String[] args) {
        AuthorizationController controller = ObjectManager.get(AuthorizationController.class);
        int l = 10;
        for (int i = 0; i < l; i++) {
            i++;
        }
        System.out.println(l);
        controller.show();
    }
}