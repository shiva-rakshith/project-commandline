package projectgroup;

public interface UserActions {
    String createUser(String phoneNumber,String name,int age,String location);
    String updateUser(String phoneNumber,String name,int age,String location);
    String deleteUser(String phoneNumber);
    String showUser(String phoneNumber);

}
