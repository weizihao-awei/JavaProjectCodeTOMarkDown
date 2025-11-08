The project is a Java-based utility suite designed to process source code files and generate structured Markdown documentation. It recursively parses directories, identifies code files based on permitted extensions, and generates a navigable Markdown representation of the directory structure along with code content.

---

## 📁 Features

- **Directory Tree Construction**: Builds a hierarchical tree of directories and files.
- **Markdown Generation**: Converts code files into Markdown format with navigation support.
- **File Filtering**: Supports filtering of directories and code files based on naming and extensions.
- **Code Copying**: Provides utilities to copy files and string lists to target files.
- **Flexible Output**: Allows customization of Markdown output structure and formatting.

---

## 🛠️ Modules

- **CodeFilesToMd**: Generates Markdown content from a directory tree.
- **Make_md_main**: Entry point for Markdown generation with multiple output modes.
- **FileProcess**: Handles file naming, extension detection, and filtering.
- **TreeNode & ListTreeDir**: Implements directory tree structure and traversal.
- **Read_File & Write_File**: Utilities for reading from and writing to files.
- **Copy_file**: Offers methods to copy files and string content.

---

## 📦 Setup

### Prerequisites

- Java 8 or higher
- Maven (for building the project)

### Build

To build the project using Maven:

```bash
mvn clean package
```

This will compile the source code and generate a JAR file in the `target/` directory.

---

## ▶️ Usage

To generate Markdown documentation from a source directory:

```bash
java -jar target/your-jar-name.jar
```

By default, it processes the directory specified in `DEFAULT_SOURCE_DIR` inside `CodeToMD_Main.java`. You can modify this path or extend the `main` method to accept command-line arguments.

---

## 📄 Example Output

The generated Markdown includes:

- A navigation tree showing the directory structure.
- Code listings for each supported file type.
- Proper indentation and hierarchy based on folder depth.

---

## 📝 Configuration

You can configure:

- **Permitted File Extensions**: Modify `Permitted_suffix_names` in `Make_md_main.java`.
- **File Naming Rules**: Adjust logic in `FileProcess.java`.
- **Output Format**: Extend or modify `CodeFilesToMd.java` and `Make_md_main.java`.

---

## 🤝 Contributing

Contributions are welcome! Please ensure:

- You follow the existing code style.
- You update relevant documentation.
- You test changes thoroughly before submitting a pull request.

---

## 📄 License

Please check the license file in the repository for terms and conditions.