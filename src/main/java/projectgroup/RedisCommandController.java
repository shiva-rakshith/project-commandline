package projectgroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class RedisCommandController {

    @Autowired
    private RedisCommand redisCommand;

    @PostMapping("/createUserDetails")
    public String createUserDetails(@RequestParam String phoneNumber,@RequestParam String name,@RequestParam int age,@RequestParam String location){
        return redisCommand.createUser(phoneNumber,name,age,location);
    }

    @PutMapping("/updateUserDetails")
    public String updateUserDetails(@RequestParam String phoneNumber,@RequestParam String name,@RequestParam int age,@RequestParam String location){
        return redisCommand.updateUser(phoneNumber,name,age,location);
    }

    @GetMapping("/showUserDetails")
    public String showUserDetails(@RequestParam String phoneNumber){
       return redisCommand.showUser(phoneNumber);
    }

    @DeleteMapping("/deleteUserDetails")
    public String deleteUserDetails(@RequestParam String phoneNumber){
        return redisCommand.deleteUser(phoneNumber);
    }
}
