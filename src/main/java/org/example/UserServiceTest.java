package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private Dao dao = mock(Dao.class);

    @InjectMocks
    private UserService userService;

    @Test
    void CreateUserTest(){
        User user = new User();
        user.setName("Тест");
        userService.CreateUser(user);
        verify(dao, times(1)).save(user);
    }

    @Test
    void FindAllTest(){
        List<User> expectedUsers = List.of(new User());
        when(dao.findAll()).thenReturn(expectedUsers);
        List<User> actualUsers = userService.FindAll();

        assertEquals(expectedUsers.size(), actualUsers.size());
        verify(dao, times(1)).findAll();
    }

    @Test
    void UpdateTest(){
        User user = new User();
        user.setId(1L);
        user.setName("Измененный Юзер");

        userService.update(user);

        verify(dao, times(1)).update(user);
    }

    @Test
    void DeleteTest(){
        User user = new User();
        user.setName("Юзер для удаления");
        Long id = user.getId();;
        userService.delete(id);
        verify(dao, times(1)).delete(id);
    }
}
