package ir.ac.iust.dml.kg.knowledge.expert.access.mongo;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.expert.access.dao.ITicketDao;
import ir.ac.iust.dml.kg.knowledge.expert.access.entities.Ticket;
import ir.ac.iust.dml.kg.knowledge.expert.access.entities.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * impl {@link ITicketDao}
 */
@Repository
public class TicketDaoImpl implements ITicketDao {
    @Autowired
    private MongoOperations op;

    @Override
    public void write(Ticket... tickets) {
        for (Ticket t : tickets)
            op.save(t);
    }

    @Override
    public void delete(Ticket... tickets) {
        for (Ticket t : tickets)
            op.remove(t);
    }

    @Override
    public Ticket read(ObjectId id) {
        return op.findOne(
                new Query().addCriteria(Criteria.where("id").is(id)),
                Ticket.class
        );
    }

    @Override
    public Ticket read(User user, String identifier) {
        final Query query = new Query()
                .addCriteria(Criteria.where("user").is(user))
                .addCriteria(Criteria.where("triple.identifier").is(identifier));
        return op.findOne(query, Ticket.class);
    }

    @Override
    public PagingList<Ticket> readAssignedTicket(User user, int page, int pageSize) {
        final Query query = new Query()
                .addCriteria(Criteria.where("user").is(user))
                .addCriteria(Criteria.where("vote").exists(false));
        return DaoUtils.paging(op, Ticket.class, query, page, pageSize);
    }
}
