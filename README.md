# 🖼️ Image Viewer

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-GUI-blue?style=for-the-badge)
![License](https://img.shields.io/github/license/Cocodrulo/imageviewer?style=for-the-badge)
![Stars](https://img.shields.io/github/stars/Cocodrulo/imageviewer?style=for-the-badge)
![Issues](https://img.shields.io/github/issues/Cocodrulo/imageviewer?style=for-the-badge)
![Release](https://img.shields.io/github/v/release/Cocodrulo/imageviewer?style=for-the-badge)

**A modern, feature-rich image viewer with advanced zoom, panning, and navigation capabilities.**

[📥 Download Latest Release](https://github.com/Cocodrulo/imageviewer/releases/latest) • [🐛 Report Bug](https://github.com/Cocodrulo/imageviewer/issues) • [✨ Request Feature](https://github.com/Cocodrulo/imageviewer/issues)

</div>

---

## 🌟 Features

### 🎨 Modern UI

-   ✅ **Dark Theme**: Sleek dark interface with custom title bar
-   ✅ **Rounded Borders**: Modern rounded window corners (square when maximized)
-   ✅ **Custom Window Controls**: Stylish minimize, maximize/restore, and close buttons
-   ✅ **Smooth Animations**: Hover effects and smooth transitions
-   ✅ **Status Bar**: Real-time display of current image and zoom level

### 🔍 Advanced Zoom

-   ✅ **Zoom In/Out**: Up to 500% zoom with smooth scaling
-   ✅ **Mouse Wheel Zoom**: Intuitive scroll-to-zoom functionality
-   ✅ **Reset Zoom**: Quickly return to fit-to-window view
-   ✅ **Image Panning**: Drag zoomed images to explore details
-   ✅ **Keyboard Shortcuts**: Ctrl+Plus/Minus for quick zoom control

### 📁 Image Management

-   ✅ **Folder Loading**: Load entire folders of images at once
-   ✅ **Multiple Selection**: Select and load multiple individual images
-   ✅ **Supported Formats**: JPG, JPEG, PNG, GIF, BMP
-   ✅ **Dynamic Queue**: Image queue updates when loading new folder/files
-   ✅ **Circular Navigation**: Seamlessly loop through images

### ⌨️ Keyboard Shortcuts

-   ✅ **Arrow Keys**: Navigate between images
-   ✅ **Ctrl+O**: Open file/folder browser
-   ✅ **Ctrl+Plus/Equals**: Zoom in
-   ✅ **Ctrl+Minus**: Zoom out
-   ✅ **Ctrl+0**: Reset zoom to 100%
-   ✅ **Mouse Wheel**: Zoom in/out

### 🏗️ Clean Architecture

-   ✅ **MVC Pattern**: Clear separation of model, view, and controller
-   ✅ **Command Pattern**: Flexible command-based actions
-   ✅ **Dependency Injection**: Loose coupling and testability
-   ✅ **Repository Pattern**: Abstract data access layer

---

## 🖼️ Screenshots

<div align="center">
  <img src="docs/screenshot.png" alt="Image Viewer Screenshot" width="600">
  <p><i>Image Viewer main interface</i></p>
</div>

---

## 📦 Installation

### Prerequisites

-   **Java 21** or higher
-   **Maven 3.6+** (to build from source)

### Direct Download

Download the executable for your platform from the [releases page](https://github.com/Cocodrulo/imageviewer/releases/latest):

#### Windows

```bash
# Run the installer
ImageViewer-1.0-SNAPSHOT.exe

# Or use the universal JAR
java -jar imageviewer.jar
```

#### macOS

```bash
# Open the DMG or install the PKG
open ImageViewer-1.0-SNAPSHOT.dmg

# Or use the universal JAR
java -jar imageviewer.jar
```

#### Linux

```bash
# Install DEB (Debian/Ubuntu)
sudo dpkg -i imageviewer_1.0-SNAPSHOT.deb

# Install RPM (Fedora/RHEL)
sudo rpm -i imageviewer-1.0-SNAPSHOT.rpm

# Or use the universal JAR
java -jar imageviewer.jar
```

### Build from Source

```bash
# Clone the repository
git clone https://github.com/Cocodrulo/imageviewer.git
cd imageviewer

# Build with Maven
mvn clean package

# Run the application
java -jar target/imageviewer-1.0-SNAPSHOT-jar-with-dependencies.jar
```

---

## 🚀 Usage

### Getting Started

1. **Launch the application**
2. **Click the 📁 button** or press **Ctrl+O** to load images
3. **Select a folder** (loads all images) or **select individual files**
4. **Navigate** using arrow keys or toolbar buttons
5. **Zoom** with mouse wheel or Ctrl+Plus/Minus
6. **Pan** zoomed images by dragging with the mouse

### Keyboard Shortcuts Reference

| Shortcut                     | Action                |
| ---------------------------- | --------------------- |
| `Left Arrow` / `Right Arrow` | Previous / Next image |
| `Ctrl+O`                     | Load folder or images |
| `Ctrl+Plus` / `Ctrl+Equals`  | Zoom in               |
| `Ctrl+Minus`                 | Zoom out              |
| `Ctrl+0`                     | Reset zoom to 100%    |
| `Mouse Wheel Up`             | Zoom in               |
| `Mouse Wheel Down`           | Zoom out              |
| `Mouse Drag` (when zoomed)   | Pan image             |

---

## 🏗️ Project Architecture

The project follows a **clean architecture** with clear separation of responsibilities:

```
imageviewer/
├── architecture/              # Architecture layer (interfaces & abstractions)
│   ├── model/
│   │   ├── Image.java            → Image representation with navigation
│   │   ├── ImageProvider.java    → Manages image collection
│   │   └── Canvas.java           → Canvas calculations for fit-to-window
│   │
│   ├── interfaces/
│   │   ├── ImageDisplay.java          → Display interface
│   │   ├── ZoomableImageDisplay.java  → Extended with zoom capabilities
│   │   └── ImageStore.java            → Image storage abstraction
│   │
│   └── commands/             # Command Pattern implementations
│       ├── Command.java           → Base command interface
│       ├── NextCommand.java       → Navigate to next image
│       ├── PrevCommand.java       → Navigate to previous image
│       ├── ZoomInCommand.java     → Zoom in action
│       ├── ZoomOutCommand.java    → Zoom out action
│       ├── ResetZoomCommand.java  → Reset zoom level
│       └── LoadImageCommand.java  → Load external images
│
└── application/              # Specific implementations
    ├── gui/                  # Swing GUI implementation
    │   ├── Main.java              → Application entry point
    │   ├── Desktop.java           → Main window with modern UI
    │   ├── CustomTitleBar.java    → Custom window title bar
    │   └── SwingImageDisplay.java → Image display with zoom & pan
    │
    └── FileImageStore.java   # File-based image storage
```

### 📐 Design Patterns Used

#### 1. **Model-View-Controller (MVC)**

-   **Model**: `Image`, `ImageProvider`, `Canvas`
-   **View**: `Desktop`, `SwingImageDisplay`, `CustomTitleBar`
-   **Controller**: Command implementations

#### 2. **Command Pattern**

All actions are encapsulated as commands for flexibility and extensibility:

```java
public interface Command {
    void execute();
}

// Example: Zoom In
public class ZoomInCommand implements Command {
    private final ZoomableImageDisplay display;

    @Override
    public void execute() {
        display.zoomIn();
    }
}
```

#### 3. **Repository Pattern**

The `ImageStore` interface abstracts data access:

```java
public interface ImageStore {
    Stream<String> images();
}

// File-based implementation
public class FileImageStore implements ImageStore {
    // Load images from file system
}
```

#### 4. **Dependency Injection**

The `Main` class wires up all dependencies:

```java
SwingImageDisplay imageDisplay = new SwingImageDisplay();
Desktop desktop = Desktop.create(imageDisplay);

desktop.put("zoomIn", new ZoomInCommand(imageDisplay));
desktop.put("load", new LoadImageCommand(Main::loadNewImages));
```

---

## 🔧 Technologies Used

| Technology   | Purpose                                   |
| ------------ | ----------------------------------------- |
| **Java 21**  | Programming language with modern features |
| **Swing**    | GUI framework with custom rendering       |
| **Maven**    | Dependency management and build           |
| **JPackage** | Native installer creation                 |

---

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. **Fork** the project
2. Create a **feature branch** (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. Open a **Pull Request**

### 📋 Contribution Guidelines

-   Follow the existing code style and architecture patterns
-   Write descriptive commits
-   Document significant changes
-   Test your code before submitting PR
-   Respect the MVC architecture and command pattern

---

## 👥 Contributors

<a href="https://github.com/Cocodrulo/imageviewer/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=Cocodrulo/imageviewer" />
</a>

---

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## ✉️ Contact

**Cocodrulo** - [@Cocodrulo](https://github.com/Cocodrulo)

**Project Link**: [https://github.com/Cocodrulo/imageviewer](https://github.com/Cocodrulo/imageviewer)

---

## ⚠️ Disclaimer

> [!NOTE]
> This README and the GitHub Actions workflow were created with AI assistance, but not entirely generated by artificial intelligence. The core application code and architecture were developed independently.

---

<div align="center">

**⭐ If this project has been useful to you, consider giving it a star ⭐**

</div>
