package CodeToMd.DirectoryEnum;

public enum DirStructEnum {
    CURRENT("当前目录", "File"),
    SUB("子层目录", "List<File>"),
    PARENT("父级目录", "List<File>");

    private final String value;
    private final String dataType;

    DirStructEnum(String value, String dataType) {
        this.value = value;
        this.dataType = dataType;
    }

    public String getValue() {
        return value;
    }

    public String getDataType() {
        return dataType;
    }
}
