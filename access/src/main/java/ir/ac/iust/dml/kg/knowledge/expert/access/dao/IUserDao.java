package ir.ac.iust.dml.kg.knowledge.expert.access.dao;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.expert.access.entities.User;
import org.bson.types.ObjectId;

/**
 * Interface dao for user operations
 */
public interface IUserDao {
    void write(User... users);

    void delete(User... users);

    User read(ObjectId id);

    User readByUsername(String username);

    PagingList<User> search(String name, String username, int page, int pageSize);
}
