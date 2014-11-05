package edu.clarkson.cs.clientlib.ripeatlas.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.clarkson.cs.httpjson.json.JsonAttribute;
import edu.clarkson.cs.persistence.EntityObject;

@Entity
@Table(name = "probe")
public class Probe implements EntityObject {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "address_v4")
	private String addressV4;

	@Column(name = "address_v6")
	private String addressV6;

	@Column(name = "asn_v4")
	private int asnV4;

	@Column(name = "asn_v6")
	private int asnV6;

	@Column(name = "country_code")
	private String countryCode;

	@Column(name = "is_anchor")
	@JsonAttribute("is_anchor")
	private boolean anchor;

	@Column(name = "is_public")
	@JsonAttribute("is_public")
	private boolean publicc;

	@Column(name = "resource_uri")
	private String resourceUri;

	@Column(name = "prefix_v4")
	private String prefixV4;

	@Column(name = "prefix_v6")
	private String prefixV6;

	@Column(name = "status")
	private int status;

	@Column(name = "status_since")
	@Temporal(TemporalType.TIMESTAMP)
	private Date statusSince;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddressV4() {
		return addressV4;
	}

	public void setAddressV4(String addressV4) {
		this.addressV4 = addressV4;
	}

	public String getAddressV6() {
		return addressV6;
	}

	public void setAddressV6(String addressV6) {
		this.addressV6 = addressV6;
	}

	public int getAsnV4() {
		return asnV4;
	}

	public void setAsnV4(int asnV4) {
		this.asnV4 = asnV4;
	}

	public int getAsnV6() {
		return asnV6;
	}

	public void setAsnV6(int asnV6) {
		this.asnV6 = asnV6;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public boolean isAnchor() {
		return anchor;
	}

	public void setAnchor(boolean anchor) {
		this.anchor = anchor;
	}

	public boolean isPublicc() {
		return publicc;
	}

	public void setPublicc(boolean publicc) {
		this.publicc = publicc;
	}

	public String getResourceUri() {
		return resourceUri;
	}

	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	public String getPrefixV4() {
		return prefixV4;
	}

	public void setPrefixV4(String prefixV4) {
		this.prefixV4 = prefixV4;
	}

	public String getPrefixV6() {
		return prefixV6;
	}

	public void setPrefixV6(String prefixV6) {
		this.prefixV6 = prefixV6;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getStatusSince() {
		return statusSince;
	}

	public void setStatusSince(Date statusSince) {
		this.statusSince = statusSince;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getStatusText() {
		if (getStatus() < 0 || getStatus() >= ProbeStatus.values().length)
			return null;
		return ProbeStatus.values()[getStatus()].name();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Probe))
			return false;
		Probe other = (Probe) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
