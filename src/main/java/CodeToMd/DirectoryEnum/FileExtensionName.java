package CodeToMd.DirectoryEnum;

public enum FileExtensionName {
    MD(".md")   ;
    private final String extension;
    FileExtensionName(String extension) {
        this.extension = extension;
    }
    public String getExtension() {
        return extension;
    }
}
