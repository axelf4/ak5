/**
 * 
 */
package org.gamelib.util.slow.vfs;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.gamelib.util.slow.collection.AbstractIterator;


/** an implementation of {@link org.reflections.vfs.Vfs.Dir} for directory {@link java.io.File} */
public class SystemDir implements Vfs.Dir {
    private final File file;

    public SystemDir(File file) {
        if (file == null || !file.exists() || !file.isDirectory() || !file.canRead()) {
            throw new RuntimeException("cannot use dir " + file);
        }

        this.file = file;
    }

    public String getPath() {
        return file.getPath().replace("\\", "/");
    }

    public Iterable<Vfs.File> getFiles() {
        return new Iterable<Vfs.File>() {
            public Iterator<Vfs.File> iterator() {
                return new AbstractIterator<Vfs.File>() {
                    final Stack<File> stack = new Stack<File>();
                    {stack.addAll(listFiles(file));}

                    protected Vfs.File computeNext() {
                        while (!stack.isEmpty()) {
                            final File file = stack.pop();
                            if (file.isDirectory()) {
                                stack.addAll(listFiles(file));
                            } else {
                                return new SystemFile(SystemDir.this, file);
                            }
                        }

                        return endOfData();
                    }
                };
            }
        };
    }

    private static List<File> listFiles(final File file) {
        File[] files = file.listFiles();

        if (files != null)
            return new ArrayList<>(Arrays.asList(files));
        else
            return new ArrayList<>();
    }

    public void close() {
    }

    @Override
    public String toString() {
        return file.toString();
    }
}
