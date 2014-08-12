package edu.clarkson.cs.clientlib.geolite.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import edu.clarkson.cs.persistence.EntityObject;

@Entity
@Table(name = "geolite_block")
public class Block implements EntityObject {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "from_ip")
	private Long fromIp;

	@Column(name = "to_ip")
	private Long toIp;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "block", referencedColumnName = "id")
	private Location location;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getFromIp() {
		return fromIp;
	}

	public void setFromIp(Long fromIp) {
		this.fromIp = fromIp;
	}

	public Long getToIp() {
		return toIp;
	}

	public void setToIp(Long toIp) {
		this.toIp = toIp;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
