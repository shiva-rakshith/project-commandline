package projectgroup;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import redis.clients.jedis.Jedis;

@ShellComponent
public class Command{

    Jedis jedis = new Jedis("127.0.0.1",6379);

    @ShellMethod("creating user")
    public void createuser(String name,int age,String location){
        jedis.lpush("name",name);
        jedis.lpush("age", String.valueOf(age));
        jedis.lpush("location",location);
    }

}
