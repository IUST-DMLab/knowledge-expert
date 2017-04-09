package ir.ac.iust.dml.kg.knowledge.expert.access.test;

import ir.ac.iust.dml.kg.knowledge.expert.access.dao.IUserDao;
import ir.ac.iust.dml.kg.knowledge.expert.access.entities.User;
import ir.ac.iust.dml.kg.knowledge.expert.access.entities.UserPermission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for access
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:persistence-context.xml")
public class AccessTest {
    @Autowired
    IUserDao userDao;

    @Test
    public void testUserDao() {
        final User user1 = new User("user1", "pass1", "name1", UserPermission.Expert);
        final User user2 = new User("user2", "pass2", "name2", UserPermission.Expert);
        userDao.write(user1, user2);
        try {
            userDao.write(new User("user1", "pass1", "name1", UserPermission.Expert));
            assert false;
        } catch (Throwable th) {
            assert true;
        }
        assert userDao.read(user1.getId()).getUsername().equals(user1.getUsername());
        userDao.delete(user1, user2);
    }
}
