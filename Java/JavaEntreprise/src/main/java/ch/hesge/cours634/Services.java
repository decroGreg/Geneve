package ch.hesge.cours634;

import ch.hesge.cours634.entity.OrganisationEntity;
import ch.hesge.cours634.entity.ServiceEntity;
import ch.hesge.cours634.svc.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;

@ApplicationScoped
@Log
public class Services {

    @PersistenceContext(unitName = "OrgaPU")
    EntityManager em;

    @Transactional
    public List<OrganisationEntity> getOrganisations(){
        Query query=em.createQuery("SELECT name FROM OrganisationEntity ");
        List<OrganisationEntity> list=query.getResultList();

        return list;
    }
    @Transactional
    public OrganisationEntity createOrganisations(OrganisationEntity orga){
        orga.setServices(new ArrayList<ServiceEntity>());
        em.persist(orga);
        return orga;
    }
    @Transactional
    public OrganisationEntity getOrganisation(String id){
        OrganisationEntity orga=em.find(OrganisationEntity.class,id);
        return orga;
    }
    @Transactional
    public ServiceEntity addService(ServiceEntity s,String id){
        em.persist(s);
        OrganisationEntity orga=em.find(OrganisationEntity.class,id);
        orga.getServices().add(s);
        em.merge(orga);
        return s;
    }
    @Transactional
    public List<ServiceEntity> searchService(String id) {
        OrganisationEntity orga=em.find(OrganisationEntity.class,id);
        return orga.getServices();
    }
}
