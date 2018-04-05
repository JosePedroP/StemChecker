package stemcheckerbeans;

public class AuxSorter implements Comparable {

	protected Object a;
	protected Comparable c;
	
	public AuxSorter(Object a, Comparable c) {
		super();
		this.a = a;
		this.c = c;
	}

	@Override
	public int compareTo(Object arg0) {
		AuxSorter ax = (AuxSorter)arg0;
		return c.compareTo(ax.getC());
	}
	
	public Object getA() {
		return a;
	}

	public Comparable getC() {
		return c;
	}

	public void setA(Object a) {
		this.a = a;
	}

	public void setC(Comparable c) {
		this.c = c;
	}
	
}
