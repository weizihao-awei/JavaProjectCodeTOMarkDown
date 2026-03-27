# SourceCode_become_md - 源码转 Markdown 工具

[![Java](https://img.shields.io/badge/Java-8+-blue.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-4.0.0-orange.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

## 📖 项目简介

**SourceCode_become_md** 是一个实用的 Java 工具，用于将源代码目录结构自动转换为结构化的 Markdown 文档。该工具可以：

- 🔍 自动扫描源代码目录结构
- 📝 生成包含导航栏的 Markdown 文件
- 🔗 支持标准 Markdown 双链和 Obsidian 双链格式
- 📂 保留原始目录层级关系
- 💻 支持多种编程语言文件

## ✨ 主要特性

- **智能目录遍历**: 自动构建目录树结构，过滤空目录
- **灵活的链接格式**: 
  - 模式 1: 标准 Markdown 双链格式
  - 模式 2: Obsidian 双链格式
- **导航栏生成**: 自动生成包含上下级目录、文件列表的导航栏
- **代码高亮**: 自动识别文件扩展名并应用语法高亮
- **日志记录**: 使用 Logback 记录处理过程

## 🚀 快速开始

### 环境要求

- JDK 1.8 或更高版本
- Maven 3.x+

### 安装与构建

```bash
# 克隆项目
git clone <repository-url>
cd SourceCode_become_md

# 构建项目
mvn clean package
```

### 使用方法

1. 运行主类 `CodeToMD_Main`

```bash
java -cp target/SourceCode_become_md-1.0-SNAPSHOT.jar CodeToMD.CodeToMD_Main
```

或直接通过 IDE 运行 `CodeToMD_Main.java`

2. 根据提示输入:
   - 源代码目录的绝对路径
   - 生成的 Markdown 文件存放目录的绝对路径
   - 选择输出模式（1: 标准 MD 双链 / 2: Obsidian 双链）

### 使用示例

```
请输入要处理的源代码目录绝对地址:
D:\MyProject\src

请输入要生成的 md 文件存放的文件夹的绝对地址:
D:\Notes\CodeDocs

请选择生成 md 文件的模式 (输入数字):
1:表示标准 md 双链 2:表示 obsidian 格式双链
1
```

## 📁 项目结构

```
SourceCode_become_md/
├── src/main/java/
│   ├── CodeToMd/              # 核心功能模块
│   │   ├── CodeToMD_Main.java    # 主入口类
│   │   ├── CodeFilesToMd.java    # 代码文件转换逻辑
│   │   ├── Copy_file.java        # 文件复制工具
│   │   └── DirectoryEnum/        # 枚举定义
│   │       ├── CodeTypeEnum.java
│   │       ├── DirStructEnum.java
│   │       ├── FileExtensionName.java
│   │       ├── FilterDirectory.java
│   │       └── StaticField.java
│   └── Tool/                    # 工具类模块
│       ├── FileProcess.java     # 文件处理工具
│       ├── ListTreeDir.java     # 目录树遍历
│       ├── Read_File.java       # 文件读取
│       ├── Stirng_Process.java  # 字符串处理
│       ├── TreeNode.java        # 树节点定义
│       └── Write_File.java      # 文件写入
├── pom.xml                      # Maven 配置
├── LICENSE                      # 开源许可证
└── README.md                    # 项目说明文档
```

## 🔧 核心组件

### CodeToMD_Main
主程序入口，负责:
- 接收用户输入
- 初始化配置
- 协调整个转换流程

### CodeFilesToMd
核心转换逻辑:
- 生成导航栏（支持两种链接格式）
- 读取并格式化代码文件
- 拼接 Markdown 内容

### TreeNode
目录树节点数据结构:
- 表示目录层级关系
- 存储子目录和文件列表
- 提供目录过滤和遍历功能

## 📊 输出示例

生成的 Markdown 文件包含:

```markdown
## 笔记导航栏

当前笔记代码包在电脑位置:
[打开文件夹](file:///D:/MyProject/src/com/example)

- [根目录](MyProject.md)
- [返回上级目录](com.md)
- 下级目录
  - [controller](controller.md)
    - UserController.java
  - [service](service.md)
    - UserService.java

## 代码

### UserController.java
```java
// 代码内容...
```
```

## 🛠️ 技术栈

- **语言**: Java 8+
- **构建工具**: Maven
- **日志框架**: Logback + SLF4J
- **依赖管理**: Maven Dependencies

## 📝 待改进功能

项目中标注的 TODO:
- ✅ 循环输入验证，直到用户输入正确路径
- ✅ 增加退出机制
- 🔄 验证用户输入的目录路径是否存在
- 🔄 根据文件大小、日期、内容进行过滤
- 🔄 前端模式支持

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 👨‍💻 作者

SourceCode_become_md 项目

## 🙏 致谢

感谢所有为本项目做出贡献的开发者！

---

**注意**: 本工具主要用于学习和文档化目的，帮助开发者更好地组织和理解代码结构。
