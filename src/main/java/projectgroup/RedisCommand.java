package projectgroup;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.Config;
import org.json.JSONObject;

@ShellComponent @Service
public class RedisCommand implements UserActions {

        String userDetails,phoneNumber,name,location;
        int age;

        //To get redis server hostname and port from application.conf file
        Config conf = ConfigFactory.load();
        String hostname = conf.getString("redis-hostname");
        int port = conf.getInt("redis-port");

        //Connecting to redis database.
        Jedis jedis = new Jedis(hostname, port);

        //Creating logger to print on console.
        private static final Logger logger = LoggerFactory.getLogger(RedisCommand.class);

        @Override @ShellMethod("To create user ex: create-user [10-digit phone number][name][age][location]")
        public String createUser (String phoneNumber, String name,int age, String location){
            if (phoneNumber.length() == 10) {
                userDetails = "{\"name\":" +"\""+ name +"\""+ ",\"age\":" + age + ",\"location\":" +"\""+ location+"\"" +"}";
                jedis.set(phoneNumber, userDetails);
                logger.info("Created user details successfully: " + phoneNumber + " " + name + " " + age + " " + location);
                return "Created user details successfully.";
            } else {
                return "Invalid phone number";
            }
        }

        @Override @ShellMethod("To update user ex: create-user [10-digit phone number][name][age][location]")
        public String updateUser (String phoneNumber, String name,int age, String location){
            if (jedis.exists(phoneNumber)) {
                userDetails = "{\"name\":" +"\""+ name +"\""+ ",\"age\":" + age + ",\"location\":" +"\""+ location+"\"" +"}";
                jedis.set(phoneNumber, userDetails);
                logger.info("New details are successfully updated: " + phoneNumber + " " + name + " " + age + " " + location);
                return "User details updated successfully";
            } else {
                return "User do not exit";
            }
        }

        @Override @ShellMethod("To delete user ex: delete-user [10-digit phone number]")
        public String deleteUser (String phoneNumber){
            if (jedis.exists(phoneNumber)) {
                jedis.del(phoneNumber);
                logger.info(phoneNumber + " user details deleted successfully.");
                return "Deleted user details successfully";
            } else {
                return "User do not exit";
            }
        }

        @Override @ShellMethod("To show user details ex: show-user [10-digit phone number]")
        public String showUser (String phoneNumber){
            if (jedis.exists(phoneNumber)) {
                userDetails = jedis.get(phoneNumber);
                JSONObject obj = new JSONObject(userDetails);
                this.phoneNumber =  phoneNumber;
                this.name = obj.getString("name");
                this.age  = obj.getInt("age");
                this.location = obj.getString("location");
                return userDetails;
            } else {
                return "User details do not exist.";
            }
        }

}
