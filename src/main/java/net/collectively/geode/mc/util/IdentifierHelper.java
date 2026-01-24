package net.collectively.geode.mc.util;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public interface IdentifierHelper {
    /**
     * Attempts to create an {@link Identifier} using the given file {@link Path}.
     * @param path The path of the file or directory that should be turned into an identifier.
     * @param excludeStartPaths Removes this string from the start of the path. Leave empty to ignore.
     * @param keepExtension Whether to keep any potential ".*" at the end of the identifier.
     * @return The created {@link Identifier} using {@link Identifier#tryParse(String, String)} or null if it couldn't be created.
     */
    static @Nullable Identifier parsePath(Path path, String excludeStartPaths, boolean keepExtension) {
        // File identifier base path is either "assets" or "data".
        String pathAsString = path.toString();
        int assetsIndex = pathAsString.indexOf("\\assets\\");
        int dataIndex = pathAsString.indexOf("\\data\\");
        if (assetsIndex > dataIndex) pathAsString = pathAsString.substring(assetsIndex + "\\assets\\".length());
        else pathAsString = pathAsString.substring(dataIndex + "\\data\\".length());
        pathAsString = pathAsString.replace("\\", "/");

        // Determine the namespace and path of the file, and exclude the potential start paths.
        String identifierNamespace = pathAsString.split("/")[0];
        String identifierPath = pathAsString.substring(identifierNamespace.length() + 1);
        if (identifierPath.startsWith(excludeStartPaths)) {
            identifierPath = identifierPath.substring(excludeStartPaths.length());
        }

        // Manage the extension.
        if (!keepExtension && identifierPath.contains(".")) {
            identifierPath = identifierPath.substring(0, identifierPath.lastIndexOf("."));
        }

        return Identifier.tryParse(identifierNamespace, identifierPath);
    }
}
