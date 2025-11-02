package CodeToMd.DirectoryEnum;

public enum CodeTypeEnum {
    JAVA("java"),
    PYTHON("py"),
    CPP("cpp"),
    C("c"),
    JAVASCRIPT("js"),
    PHP("php"),
    RUBY("rb"),
    GO("go"),
    KOTLIN("kt"),
    SWIFT("swift"),
    RUST("rs"),
    HTML("html"),
    CSS("css"),
    TYPESCRIPT("ts"),
    SCSS("scss"),
    LESS("less"),
    VUE("vue"),
    REACT("jsx"),
    XML("xml"),
    ANGULAR("ts");
    //增加xml


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
