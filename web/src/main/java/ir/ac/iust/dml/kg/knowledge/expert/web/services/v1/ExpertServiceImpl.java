package ir.ac.iust.dml.kg.knowledge.expert.web.services.v1;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.expert.access.dao.ITicketDao;
import ir.ac.iust.dml.kg.knowledge.expert.access.entities.Ticket;
import ir.ac.iust.dml.kg.knowledge.expert.access.entities.User;
import ir.ac.iust.dml.kg.knowledge.expert.web.security.MyUserDetails;
import ir.ac.iust.dml.kg.knowledge.store.client.Triple;
import ir.ac.iust.dml.kg.knowledge.store.client.V1StoreClient;
import ir.ac.iust.dml.kg.knowledge.store.client.Vote;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Impl {@link IExpertServices}
 */
@WebService(endpointInterface = "ir.ac.iust.dml.kg.knowledge.expert.web.services.v1.IExpertServices")
public class ExpertServiceImpl implements IExpertServices {
    @Autowired
    private ITicketDao ticketDao;
    @Autowired
    private V1StoreClient client;

    @Override
    public List<String> login() {
        final Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        final List<String> result = new ArrayList<>();
        authorities.forEach(a -> result.add(a.getAuthority()));
        return result;
    }

    @Override
    public List<Ticket> triplesNew(int count) {
        final User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        final List<Triple> newTriples = client.triples(user.getUsername(), 50);
        final List<Ticket> newTickets = new ArrayList<>();
        newTriples.forEach(triple -> {
            final Ticket old = ticketDao.read(user, triple.getIdentifier());
            if (old != null) {
                old.setTriple(triple);
                ticketDao.write(old);
                newTickets.add(old);
            } else {
                final Ticket newTicket = new Ticket(triple, user);
                ticketDao.write(newTicket);
                newTickets.add(newTicket);
            }
        });
        return newTickets;
    }

    @Override
    public PagingList<Ticket> triplesCurrent(int page, int pageSize) {
        final User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return ticketDao.readAssignedTicket(user, page, pageSize);
    }

    @Override
    public Boolean vote(String identifier, Vote vote) {
        final User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        final Ticket ticket = ticketDao.read(new ObjectId(identifier));
        if (ticket != null) {
            ticket.setVoteEpoch(System.currentTimeMillis());
            ticket.setVote(vote);
            if (client.vote(ticket.getTriple().getIdentifier(), user.getUsername(), vote)) {
                ticketDao.write(ticket);
                return true;
            }
        }
        return false;
    }
}
