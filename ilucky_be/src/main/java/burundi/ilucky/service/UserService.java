package burundi.ilucky.service;

import java.util.Date;
import java.util.List;

import burundi.ilucky.constants.GlobalConstant;
import burundi.ilucky.exception.ObjectAlreadyExistsException;
import burundi.ilucky.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import burundi.ilucky.model.User;
import burundi.ilucky.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public User findByUserName(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User", "Username", username)
            );
        return user;
    }
    public User findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "Id", Long.toString(id))
        );
        return user;
    }
    public void createUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ObjectAlreadyExistsException("User already registered with given username: "+ username);
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setLastCheckIn(new Date());
        user.setTotalPlay(GlobalConstant.FREE_TURN);
        saveUser(user);
    }
    public User saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public boolean isAlreadyGetDailyFreeTurn(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User", "Username", username)
        );
        Date currentDate = new Date();
        currentDate.setHours(0);
        currentDate.setMinutes(0);
        currentDate.setSeconds(0);
        long millisecondsInOneDay = 24 * 60 * 60 * 1000;

        long checkInGap = currentDate.getTime() - user.getLastCheckIn().getTime();

        if (checkInGap < 0) {
            return true;// already get
        } else {
            if (checkInGap <= millisecondsInOneDay) {
                user.setSteakCount(user.getSteakCount()+1);
            }else {
                user.setSteakCount(0);
            }
            user.setLastCheckIn(new Date());
            user.setTotalPlay(user.getTotalPlay()+ GlobalConstant.FREE_TURN);
            userRepository.save(user);
            return false;
        }
    }
    public List<User> getTopHighStar(int topNumber) {
        return userRepository.findTopUsersByTotalStar(topNumber);
    }
}
