package CodeToMd.DirectoryEnum;

public enum CodeTypeEnum {
    JAVA("java"),
    PYTHON("py"),
    CPP("cpp"),
    C("c"),
    XML("xml"),
    TEXT("txt"),
    SQL("sql"),
    JSON("json"),
    //增加对yaml文件的支持
    YAML("yml");



    private final String extension;

    CodeTypeEnum(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
    public static boolean contains(String extension) {
        for (CodeTypeEnum type : CodeTypeEnum.values()) {
            if (type.extension.equals(extension)) {
                return true;
            }
        }
        return false;
    }
}
