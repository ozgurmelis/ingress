package tr.gov.tubitak.ingress.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by melis on 06/07/15.
 */
@Entity
public class UsedIdPool {
    @Id
    private Long ingressID;

    public UsedIdPool() {
    }

    public UsedIdPool(long ingressID) {
        this.ingressID = ingressID;
    }

    public Long getIngressID() { return ingressID;}

    public void setIngressID(Long ingressID) {this.ingressID = ingressID;}

}
