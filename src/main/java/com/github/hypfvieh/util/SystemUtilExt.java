package com.github.hypfvieh.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Utility-Class with various operating system related helper methods.
 *
 * @author hypfvieh
 * @since v0.0.5 - 2015-08-05
 */
public final class SystemUtilExt {

    private SystemUtilExt() {

    }

    /**
     * Delete all files and directories in the given path recursively.
     *
     * @param _fileOrDirectoryPath path to delete
     * @param _stopOnError if true throw an exception and exit on any error
     * @throws IOException
     */
    public static void deleteRecursively(String _fileOrDirectoryPath, boolean _stopOnError) throws IOException {

        if (_fileOrDirectoryPath == null || _fileOrDirectoryPath.isEmpty()) {
            return;
        }

        File path = new File(_fileOrDirectoryPath);
        if (!path.exists()) {
            return;
        }

        List<File> filesToDelete = new ArrayList<>();
        List<File> foldersToDelete = new ArrayList<>();

        try (Stream<Path> walk = Files.walk(path.toPath())) {
            
            walk.forEach(e -> {
                if (e.toFile().isDirectory()) {
                    foldersToDelete.add(e.toFile());
                } else {
                    filesToDelete.add(e.toFile());
                }
            });
        } catch (IOException _ex) {
            if (_stopOnError) {
                throw _ex;
            }
        }

        List<String> couldNotRemove = new ArrayList<>();
        for (File file : filesToDelete) {
            if (!file.delete()) {
                if (_stopOnError) {
                    throw new IOException("Could not delete file: " + file);
                }
                couldNotRemove.add(file.getAbsoluteFile().getParent());
            }
        }

        for (File file : foldersToDelete) {
            if (couldNotRemove.contains(file.getAbsoluteFile().getName())) {
                continue;
            } else {
                if (!file.delete()) {
                    if (_stopOnError) {
                        throw new IOException("Could not delete directory: " + file);
                    }
                }
            }
        }
    }

    /**
     * Delete all files and directories in the given path recursively and without throwing any exception.
     * @param _fileOrDirectoryPath
     */
    public static void deleteRecursivelyQuiet(String _fileOrDirectoryPath) {
        try {
            deleteRecursively(_fileOrDirectoryPath, false);
        } catch (IOException _ex) {
        }
    }

}
