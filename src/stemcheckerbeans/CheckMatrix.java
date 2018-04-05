package stemcheckerbeans;

public class CheckMatrix {

	protected String[][] data;
	protected String id;
	protected String species;
	protected String databseId;
	protected String geneSymbol;
	
	public CheckMatrix(int num, String id) {
		this.data = new String[num][5];
		this.id = id;
		this.species = null;
		this.databseId = null;
		this.geneSymbol = null;
	}
	public String[][] getData() {
		return data;
	}
	public void setData(String[][] data) {
		this.data = data;
	}
	public void setData(int i, String[] data) {
		this.data[i] = data;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public String getDatabseId() {
		return databseId;
	}
	public void setDatabseId(String databseId) {
		this.databseId = databseId;
	}
	public String getGeneSymbol() {
		return geneSymbol;
	}
	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}
	
}
