package projectgroup;

import redis.clients.jedis.Jedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.Config;
import org.json.JSONObject;

@ShellComponent
public class Command implements UserActions {

    String userDetails;

    //To get redis server hostname and port from application.conf file
    Config conf = ConfigFactory.load();
    String hostname = conf.getString("redis-hostname");
    int port = conf.getInt("redis-port");

    //Connecting to redis database.
    Jedis jedis = new Jedis(hostname,port);

    //Creating logger to print on console.
    private static final Logger logger = LoggerFactory.getLogger(Command.class);

    @Override @ShellMethod("To create user")
    public void createUser(String phoneNumber,String name,int age,String location){
       if(phoneNumber.length()==10) {
           userDetails = "{\"name\":" + name + ",\"age\":" + age + ",\"location\":" + location + "}";
           jedis.set(phoneNumber, userDetails);
           logger.info("Created user details successfully: "+phoneNumber+" "+name+" "+age+" "+location);
           System.out.println("Created user details successfully.");
       }
       else
       {
        System.out.println("Invalid phone number");
       }
    }

    @Override @ShellMethod("To update user")
    public void updateUser(String phoneNumber,String name,int age,String location){
        if(jedis.exists(phoneNumber)) {
            userDetails = "{\"name\":" + name + ",\"age\":" + age + ",\"location\":" + location + "}";
            jedis.set(phoneNumber, userDetails);
            logger.info("New details are successfully updated: "+phoneNumber+" "+name+" "+age+" "+location);
            System.out.println("User details updated successfully");
        }
        else
        {
            System.out.println("User do not exit");
        }
    }

    @Override @ShellMethod("To delete user")
    public void deleteUser(String phoneNumber){
        if(jedis.exists(phoneNumber)) {
            jedis.del(phoneNumber);
            logger.info(phoneNumber + "user details deleted successfully.");
            System.out.println("Deleted user details successfully");
        }
        else
        {
            System.out.println("User do not exit");
        }
    }

    @Override @ShellMethod("To show user details")
    public void showUser(String phoneNumber) {

        if(jedis.exists(phoneNumber)) {
            userDetails = jedis.get(phoneNumber);
            JSONObject obj=new JSONObject(userDetails);
            System.out.println("Name:" + obj.getString("name"));
            System.out.println("Age:" + obj.getInt("age"));
            System.out.println("Location:" + obj.getString("location"));
        }
        else
        {
            System.out.println("User details do not exist.");
        }
    }

}
