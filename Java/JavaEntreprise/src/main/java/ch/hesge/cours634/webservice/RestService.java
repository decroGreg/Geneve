package ch.hesge.cours634.webservice;

import ch.hesge.cours634.Services;
import ch.hesge.cours634.entity.OrganisationEntity;
import ch.hesge.cours634.entity.ServiceEntity;

import javax.inject.Inject;
import javax.ws.rs.*;

import java.util.List;

@Path("/")
@Consumes("application/json")
@Produces("application/json")
public class RestService {
    @Inject
    Services s;
    //127.0.0.1:8080/Exam/api/organisations/TesstShcool/any
    @GET
    @Path("/organisations")
    public List<OrganisationEntity> getOrganisations(){
        return s.getOrganisations();
    }
    //127.0.0.1:8080/Exam/api/organisations
    /*
        {"name":"school"}
     */
    @POST
    @Path("/organisations")
    public OrganisationEntity creatOrga(OrganisationEntity orga){
        s.createOrganisations(orga);
        return orga;
    }
    //127.0.0.1:8080/Exam/api/organisations/TesstShcool
    @GET
    @Path("/organisations/{id}")
    public OrganisationEntity getorganisation(@PathParam("id") String id){
        return  s.getOrganisation(id);
    }
    //127.0.0.1:8080/Exam/api/organisations/TesstShcool
    /*
        {
"name":"service",
"url":"blabla"

}
     */
    @POST
    @Path("/organisations/{id}")
    public ServiceEntity addService(ServiceEntity service,@PathParam("id") String id){
        return s.addService(service,id);
    }
    //127.0.0.1:8080/Exam/api/organisations/TesstShcool/services
    @GET
    @Path("/organisations/{id}/services")
    public List<ServiceEntity> searchServices(@PathParam("id") String id){
        return s.searchService(id);
    }
    //127.0.0.1:8080/Exam/api/organisations/TesstShcool/any
    @GET
    @Path("/organisations/{id}/any")
    public String searchAnyService(@PathParam("id") String id){
        return s.searchService(id).get(0).getUrl();
    }
}
