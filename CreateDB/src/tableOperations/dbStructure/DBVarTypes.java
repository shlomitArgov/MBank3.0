package tableOperations.dbStructure;
public enum DBVarTypes {
	VARCHAR("VARCHAR(255)"), BIGINT("BIGINT"), INTEGER("INTEGER"), DOUBLE("DOUBLE"), LONGVARCHAR(
			"VARCHAR(1024)"), DATE("TIMESTAMP");

	private DBVarTypes(String name) {
		this.name = name;
	}

	private final String name;

	public String getName() {
		return name;
	}

}
