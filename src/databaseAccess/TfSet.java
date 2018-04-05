package databaseAccess;

// Generated 24/Set/2015 16:00:24 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * TfSet generated by hbm2java
 */
public class TfSet implements java.io.Serializable {

	private Integer id;
	private String name;
	private String type;
	private String description;
	private String sample;
	private String platform;
	private String species;
	private String source;
	private String summary;
	private Set proteinInTfs = new HashSet(0);
	private Set tfPubmedRefses = new HashSet(0);

	public TfSet() {
	}

	public TfSet(String name, String type, String species) {
		this.name = name;
		this.type = type;
		this.species = species;
	}

	public TfSet(String name, String type, String description, String sample,
			String platform, String species, String source, String summary,
			Set proteinInTfs, Set tfPubmedRefses) {
		this.name = name;
		this.type = type;
		this.description = description;
		this.sample = sample;
		this.platform = platform;
		this.species = species;
		this.source = source;
		this.summary = summary;
		this.proteinInTfs = proteinInTfs;
		this.tfPubmedRefses = tfPubmedRefses;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSample() {
		return this.sample;
	}

	public void setSample(String sample) {
		this.sample = sample;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSpecies() {
		return this.species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Set getProteinInTfs() {
		return this.proteinInTfs;
	}

	public void setProteinInTfs(Set proteinInTfs) {
		this.proteinInTfs = proteinInTfs;
	}

	public Set getTfPubmedRefses() {
		return this.tfPubmedRefses;
	}

	public void setTfPubmedRefses(Set tfPubmedRefses) {
		this.tfPubmedRefses = tfPubmedRefses;
	}

}