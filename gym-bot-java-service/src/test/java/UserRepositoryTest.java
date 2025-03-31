import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.gymbot.GymBotApplication;
import com.example.gymbot.Entities.UserEntity;
import com.example.gymbot.Repositories.UserRepository;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;

@SpringBootTest(classes = GymBotApplication.class)
@ActiveProfiles("test")
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  public void testSaveUser(){
    UserEntity user = new UserEntity();
    user.setChatId((long) 13133);
    user.setAge(30);
    user.setCalorieNorm(4000.0);
    user.setActivityLevel("Высокий");
    user.setHeight(186.5);

    UserEntity savedUser = userRepository.save(user);

    assertNotNull(savedUser.getChatId());
    assertNotNull(savedUser.getAge());
    assertEquals(user.getAge(), savedUser.getAge());
  }
  
}
