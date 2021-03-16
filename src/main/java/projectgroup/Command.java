package projectgroup;

import redis.clients.jedis.Jedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class Command{

    Jedis jedis = new Jedis("127.0.0.1",6379);

    private static final Logger logger = LoggerFactory.getLogger(Command.class);

    @ShellMethod("creating user")
    public void createuser(String name,int age,String location){
        jedis.lpush("name",name);
        jedis.lpush("age", String.valueOf(age));
        jedis.lpush("location",location);
        logger.debug("Created user details successfully:");
    }

    @ShellMethod("deleting user")
    public void deleteuser(String name,int age,String location){
        jedis.lrem("name",0,name);
        jedis.lrem("age",0, String.valueOf(age));
        jedis.lrem("location",0,location);
        logger.debug("Deleted user details successfully:");
    }

}
