# Steps

1. In settings, set Gradle JVM to Java 23
2. Copy content of `root-build-backup.txt` to `build.gradle` of the root project
3. In the Project structure, for the submodule, Dependencies tab, set Java 23

### Why above needed

The outer project is build using external JetBrains' plugin which seems not to work with Java 23 yet.