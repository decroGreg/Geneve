package ch.hesge.cours634.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ServiceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 10)
	private String name;
	@Column(nullable = false)
	private String url;



	public ServiceEntity() {
	}

	public ServiceEntity(String name, String url) {
		this.name=name;
		this.url=url;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ServiceEntity service = (ServiceEntity) o;
		return Objects.equals(name, service.name) && Objects.equals(url, service.url);
	}

	@Override public int hashCode() {
		return Objects.hash(name, url);
	}
}
