package uem.br.ag.peps.utils;

public class CustomStringBuilder {

	final String separator = System.getProperty("line.separator");

	private StringBuilder builder = new StringBuilder();

	public CustomStringBuilder appendLine(String str){
	    builder.append(str + separator);
	    return this;
	}
	
	public CustomStringBuilder appendLine(){
		builder.append(separator);
		return this;
	}

	public CustomStringBuilder append(String str){
	    builder.append(str);
	    return this;
	}

	@Override
	public String toString() {
	    return this.builder.toString();
	}
	
}
