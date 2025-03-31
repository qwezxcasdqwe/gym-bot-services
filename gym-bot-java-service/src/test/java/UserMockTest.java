import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.gymbot.Entities.UserEntity;
import com.example.gymbot.Repositories.UserRepository;
import com.example.gymbot.Services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserMockTest {

  @Mock
  private UserRepository userRepository; //мок репы

  @InjectMocks
  private UserService userService;   //объект для теста

  private UserEntity testUser = new UserEntity();

  @BeforeEach
  public void setUp(){
     testUser.setCalorieNorm(4000.0);
     testUser.setAge(30);
     testUser.setActivityLevel("Высокий");
     testUser.setHeight(181.0);
     testUser.setWeight(111.0);
     testUser.setChatId((long) 100);
  }


  @Test
  public void testSaveUser(){
    //mock поведение
    when(userRepository.save(testUser)).thenReturn(testUser);

    UserEntity savedUser = userRepository.save(testUser);

    assertNotNull(savedUser.getChatId());
    assertEquals(savedUser.getChatId(), testUser.getChatId());
  }

  @Test
  public void testGetUserById(){
    when(userRepository.findById(100L)).thenReturn(Optional.of(testUser));

    Optional<UserEntity> retrivedUser = userRepository.findById(100L);
    assertTrue(retrivedUser.isPresent());
    assertEquals(retrivedUser.get().getChatId(), testUser.getChatId());
  }

  @Test
  public void userNotFoundTest(){
      when(userRepository.findById(9999L)).thenReturn(Optional.empty());

      Optional<UserEntity> user = userService.findUserByIdService(9999L);

      assertFalse(user.isPresent());
      assertTrue(user.isEmpty());

  }
 
}
