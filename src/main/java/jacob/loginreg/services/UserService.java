package jacob.loginreg.services;
import java.util.Optional;
    
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
    
import jacob.loginreg.models.LoginUser;
import jacob.loginreg.models.User;
import jacob.loginreg.repositories.UserRepository;
    
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;
    
    // TO-DO: Write register and login methods!
    public User register(User newUser, BindingResult result) {
        Optional<User> user = userRepo.findByEmail(newUser.getEmail());
        if(user.isPresent()){
            result.rejectValue("email","Email","Email is already registered");
        }
        if(!newUser.getPassword().equals(newUser.getConfirm())){
            result.rejectValue("confirm","Confirm","Passwords must match!");
        }
        if(result.hasErrors()){
            return null;
        }
        String hashed = BCrypt.hashpw(newUser.getPassword(),BCrypt.gensalt());
        newUser.setPassword(hashed);
        return userRepo.save(newUser);
    }

    public User login(LoginUser newLoginObject, BindingResult result) {
        Optional<User> user = userRepo.findByEmail(newLoginObject.getEmail());
        User temp = null;
        if(user.isPresent()){
            temp = user.get();
            if(!BCrypt.checkpw(newLoginObject.getPassword(), temp.getPassword())) {
                result.rejectValue("password", "Matches", "Invalid Password!");
            }
        }else{
            result.rejectValue("email","Matches","ACCORDING TO HARRY THIS EMAIL DOES NOT EXIST IN THE DATABASE!");
        }
        return user.orElse(null);
    }
}