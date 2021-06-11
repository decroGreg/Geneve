package ch.hesge.cours634.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Entity
public class OrganisationEntity {

	@Id
	private String name;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "organization")
	List<ServiceEntity> services= new ArrayList();

	public OrganisationEntity() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ServiceEntity> getServices() {
		return services;
	}

	public void setServices(List<ServiceEntity> services) {
		this.services = services;
	}

	public HashMap<String, String> getServicesAsMap() {
		HashMap map = new HashMap();
		for ( ServiceEntity svc : services){
			map.put(svc.getName(), svc.getUrl());
		}
		return map;
	}

	public void add(String serviceName, String url) {
		ServiceEntity service = new ServiceEntity(serviceName, url);
		services.add(service);
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		OrganisationEntity that = (OrganisationEntity) o;
		return Objects.equals(name, that.name);
	}

	@Override public int hashCode() {
		return Objects.hash(name);
	}


	@Override
	public String toString() {
		return "OrganisationEntity{" +
				"name='" + name + '\'' +
				", services=" + services +
				'}';
	}
}
