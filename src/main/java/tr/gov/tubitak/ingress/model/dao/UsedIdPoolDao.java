package tr.gov.tubitak.ingress.model.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.gov.tubitak.ingress.model.UsedIdPool;

import java.util.List;


/**
 * Created by melis on 06/07/15.
 */
@Repository
public interface UsedIdPoolDao extends CrudRepository<UsedIdPool, Long> {

    public List<UsedIdPool> findByIngressID(long ingressID);
}
