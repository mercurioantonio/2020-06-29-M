package it.polito.tdp.imdb.model;

public class Adicenza {

	private Director d;
	private int shareAct;
	
	
	public Adicenza(Director d, int shareAct) {
		super();
		this.d = d;
		this.shareAct = shareAct;
	}


	public Director getD() {
		return d;
	}


	public void setD(Director d) {
		this.d = d;
	}


	public int getShareAct() {
		return shareAct;
	}


	public void setShareAct(int shareAct) {
		this.shareAct = shareAct;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + shareAct;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adicenza other = (Adicenza) obj;
		if (shareAct != other.shareAct)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return d + " - #attori condivisi: " + shareAct + "\n";
	}
	
	
}
