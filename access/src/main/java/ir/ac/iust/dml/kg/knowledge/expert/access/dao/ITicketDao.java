package ir.ac.iust.dml.kg.knowledge.expert.access.dao;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.expert.access.entities.Ticket;
import ir.ac.iust.dml.kg.knowledge.expert.access.entities.User;
import org.bson.types.ObjectId;

/**
 * Interface dao for ticket entity operation
 */
public interface ITicketDao {
    void write(Ticket... tickets);

    void delete(Ticket... tickets);

    Ticket read(ObjectId id);

    PagingList<Ticket> readAssignedTicket(User user, int page, int pageSize);
}
