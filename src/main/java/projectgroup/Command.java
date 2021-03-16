package projectgroup;

import redis.clients.jedis.Jedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.Config;

@ShellComponent
public class Command {
    //To get redis server hostname and port from application.conf file
    Config conf = ConfigFactory.load();
    String hostname = conf.getString("redis-hostname");
    int port = conf.getInt("redis-port");

    //Connecting to redis database.
    Jedis jedis = new Jedis(hostname,port);

    //Creating logger to print on console.
    private static final Logger logger = LoggerFactory.getLogger(Command.class);

    @ShellMethod("To create user")
    public void createUser(String name,int age,String location){
        jedis.lpush("name",name);
        jedis.lpush("age", String.valueOf(age));
        jedis.lpush("location",location);
        logger.info("Created user details successfully.");
    }

    @ShellMethod("To delete user")
    public void deleteUser(String name,int age,String location){
        jedis.lrem("name",0,name);
        jedis.lrem("age",0, String.valueOf(age));
        jedis.lrem("location",0,location);
        logger.info("Deleted user details successfully.");
    }

    @ShellMethod("To update user")
    public void updateUser(String newName,int newAge,String newLocation){
        jedis.lset("name",0,newName);
        jedis.lset("age",0,String.valueOf(newAge));
        jedis.lset("location",0,newLocation);
        logger.info("New details are successfully updated.");
    }

}
