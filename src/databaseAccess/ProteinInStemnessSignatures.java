package databaseAccess;

// Generated 24/Set/2015 16:00:24 by Hibernate Tools 3.4.0.CR1

/**
 * ProteinInStemnessSignatures generated by hbm2java
 */
public class ProteinInStemnessSignatures implements java.io.Serializable {

	private Integer id;
	private StemnessSignatures stemnessSignatures;
	private ProteinList proteinList;

	public ProteinInStemnessSignatures() {
	}

	public ProteinInStemnessSignatures(StemnessSignatures stemnessSignatures,
			ProteinList proteinList) {
		this.stemnessSignatures = stemnessSignatures;
		this.proteinList = proteinList;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StemnessSignatures getStemnessSignatures() {
		return this.stemnessSignatures;
	}

	public void setStemnessSignatures(StemnessSignatures stemnessSignatures) {
		this.stemnessSignatures = stemnessSignatures;
	}

	public ProteinList getProteinList() {
		return this.proteinList;
	}

	public void setProteinList(ProteinList proteinList) {
		this.proteinList = proteinList;
	}

}