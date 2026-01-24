package net.collectively.geode.core.util;

import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface FileHelper {
    static List<Path> listFiles(Path path, Predicate<Path> validate) throws IOException {
        final List<Path> paths = new ArrayList<>();

        DeepListFileVisitor visitor = new DeepListFileVisitor(validate, paths::add);
        Files.walkFileTree(path, visitor);

        return paths;
    }

    static void clearDirectory(Path directory) throws IOException {
        if (!Files.exists(directory)) {
            return;
        }

        List<Path> compiledChildren = new ArrayList<>();

        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
            @Override
            public @NonNull FileVisitResult visitFile(@NotNull Path file, @NonNull BasicFileAttributes attrs) throws IOException {
                compiledChildren.add(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public @NonNull FileVisitResult postVisitDirectory(@NotNull Path dir, @Nullable IOException exc) throws IOException {
                compiledChildren.add(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });

        for (Path existingFile : compiledChildren) {
            Files.delete(existingFile);
        }
    }

    static void writeFileParent(Path path) throws IOException {
        if (path.getParent() != null && !Files.exists(path.getParent())) {
            writeFileParent(path.getParent());
        }

        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
    }

    static void writeFileSafe(Path path, String content) throws IOException {
        writeFileParent(path.getParent());
        Files.writeString(path, content);
    }

    class DeepListFileVisitor extends SimpleFileVisitor<Path> {
        public final Consumer<Path> onValidFileFound;
        public final Predicate<Path> validateFile;

        DeepListFileVisitor(Predicate<Path> validateFile, Consumer<Path> onValidFileFound) {
            this.onValidFileFound = onValidFileFound;
            this.validateFile = validateFile;
        }

        @Override
        public @NonNull FileVisitResult visitFile(@NonNull Path file, @NonNull BasicFileAttributes attrs) throws IOException {
            if (validateFile.test(file)) {
                onValidFileFound.accept(file);
            }

            return super.visitFile(file, attrs);
        }
    }
}
