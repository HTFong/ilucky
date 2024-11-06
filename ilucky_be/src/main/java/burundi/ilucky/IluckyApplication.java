package burundi.ilucky;

import burundi.ilucky.model.User;
import burundi.ilucky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
@EnableJpaAuditing
public class IluckyApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(IluckyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-2);
        userRepository.save(new User(1L,"user1", encoder.encode("user1"),null,null,10,100000,100, calendar.getTime(), 1));
        userRepository.save(new User(2L,"user2", encoder.encode("user2"),null,null,10,100000,100, calendar.getTime(), 2));
        userRepository.save(new User(3L,"user3", encoder.encode("user3"),null,null,10,5000,200, calendar.getTime(), 3));
        userRepository.save(new User(4L,"user4", encoder.encode("user4"),null,null,10,100000,200, calendar.getTime(), 4));
        userRepository.save(new User(5L,"user5", encoder.encode("user5"),null,null,10,100000,300, new Date(), 5));
        userRepository.save(new User(6L,"user6", encoder.encode("user6"),null,null,0,100000,10, calendar.getTime(), 0));
    }
}
