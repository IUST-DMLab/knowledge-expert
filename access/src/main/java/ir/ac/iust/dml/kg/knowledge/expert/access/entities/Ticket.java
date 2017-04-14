package ir.ac.iust.dml.kg.knowledge.expert.access.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.ac.iust.dml.kg.knowledge.store.client.Triple;
import ir.ac.iust.dml.kg.knowledge.store.client.Vote;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * entity for encapsulate current assigned triples to user
 */
@Document(collection = "tickets")
@CompoundIndexes({
        @CompoundIndex(name = "triple_index", def = "{'triple.identifier': 1, 'user' : 2, }", unique = true)
})
public class Ticket {
    @Id
    @JsonIgnore
    private ObjectId id;
    private Triple triple;
    @DBRef
    private User user;
    private long assignEpoch;
    private long voteEpoch;
    private Vote vote;

    public Ticket() {
    }

    public Ticket(Triple triple, User user) {
        this.triple = triple;
        this.user = user;
        this.assignEpoch = System.currentTimeMillis();
    }

    public String getIdentifier() {
        return id.toString();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Triple getTriple() {
        return triple;
    }

    public void setTriple(Triple triple) {
        this.triple = triple;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getAssignEpoch() {
        return assignEpoch;
    }

    public void setAssignEpoch(long assignEpoch) {
        this.assignEpoch = assignEpoch;
    }

    public long getVoteEpoch() {
        return voteEpoch;
    }

    public void setVoteEpoch(long voteEpoch) {
        this.voteEpoch = voteEpoch;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }
}
