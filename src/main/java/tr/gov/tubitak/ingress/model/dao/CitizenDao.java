package tr.gov.tubitak.ingress.model.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.gov.tubitak.ingress.model.Citizen;

import java.util.List;

/**
 * Created by melis on 06/07/15.
 */
@Repository
public interface CitizenDao extends CrudRepository<Citizen, Long> {
	
	public List<Citizen> findBySurname(String surname);

    public List<Citizen> findByIngressID(String ingressID);
}
