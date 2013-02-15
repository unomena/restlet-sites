package com.restlet.frontend.util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.SaxRepresentation;
import org.restlet.representation.FileRepresentation;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class NavigationBook {

    public static void main(String[] args) throws IOException {
        if (args != null && args.length == 2) {
            File rootDir = new File(args[0]);
            File toc = new File(args[1]);
            if (!toc.exists()) {
                toc = new File(rootDir, args[1]);
            }
            new SaxRepresentation(new FileRepresentation(toc,
                    MediaType.APPLICATION_XML)).parse(new NavigationHandler(
                    rootDir));
        } else {
            System.out
                    .println("Generate the whole hierarchy of user guide given 2 arguments:");
            System.out
                    .println(" - full path of the directory where are located the markdowned files");
            System.out
                    .println(" - full path of the table of content file, or path relative to the first argument");
            System.out
                    .println("The user guide is generated inside the first argument.");
        }
    }

    private static class NavigationHandler extends DefaultHandler {

        private File rootDir;

        private BufferedWriter bw;

        private File currentDirectory;

        private List<Boolean> dirs;

        public NavigationHandler(File rootDir) throws IOException {
            this.rootDir = rootDir;
            bw = new BufferedWriter(new FileWriter(
                    new File(rootDir, "index.md")));
            currentDirectory = rootDir;
            dirs = new ArrayList<Boolean>();
        }

        private Boolean popIsDir() {
            Boolean result = null;
            if (!dirs.isEmpty()) {
                result = dirs.get(dirs.size() - 1);
                dirs.remove(dirs.size() - 1);
            }
            return result;
        }

        private void pushDir(Boolean isDir) {
            dirs.add(isDir);
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            if ("doc".equals(qName)) {
                if (popIsDir()) {
                    currentDirectory = currentDirectory.getParentFile();
                }
            }
        }

        @Override
        public void endDocument() throws SAXException {
            try {

                bw.flush();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void startElement(String uri, String localName, String qName,
                Attributes attributes) throws SAXException {
            if ("doc".equals(qName)) {
                File dir = getDirectory(attributes);
                File file = getFichier(attributes.getValue("id"));
                String dirname = attributes.getValue("dirname");
                String filename = attributes.getValue("name");

                try {
                    String label = attributes.getValue("label");
                    String rootUrl = dir.getAbsolutePath().substring(
                            rootDir.getAbsolutePath().length());
                    if (rootUrl.startsWith("/")) {
                        rootUrl = rootUrl.substring(1);
                    }
                    for (int i = 0; i < rootUrl.length(); i++) {
                        if (rootUrl.charAt(i) == '/') {
                            bw.append("    ");
                        }
                    }

                    if (dirname != null) {
                        bw.append("-   [" + label + "]");
                        bw.append("(" + rootUrl + "/ \"" + label + "\")");
                    } else {
                        bw.append("    -   [" + label + "]");
                        bw.append("(" + rootUrl + "/" + filename + " \""
                                + label + "\")");
                    }
                    bw.append("\n");
                    // - [Features](introduction/features "Features")
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                // Migrer le fichier de même id que le doc
                if (file != null) {
                    File to = null;
                    if (dirname != null) {
                        to = new File(dir, "index" + ".md");
                    } else if (filename != null) {
                        to = new File(dir, filename + ".md");
                    } else {
                        System.out.println("what???"
                                + attributes.getValue("id"));
                    }
                    try {
                        copyFile(file, to);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("not found : "
                            + attributes.getValue("id"));
                }
            }
        }

        private File getFichier(String id) {
            File result = null;
            File[] fichiers = rootDir.listFiles();
            for (File file : fichiers) {
                if (file.isFile() && file.getName().endsWith("-" + id + ".md")) {
                    result = file;
                    break;
                }
            }

            return result;
        }

        private File getDirectory(Attributes attributes) {
            String dirname = attributes.getValue("dirname");
            if (dirname != null) {
                currentDirectory = new File(currentDirectory, dirname);
                currentDirectory.mkdirs();
                pushDir(Boolean.TRUE);
            } else {
                pushDir(Boolean.FALSE);
            }
            return currentDirectory;
        }
    }

    public static void copyFile(File sourceFile, File destFile)
            throws IOException {
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();

            // previous code: destination.transferFrom(source, 0,
            // source.size());
            // to avoid infinite loops, should be:
            long count = 0;
            long size = source.size();
            while ((count += destination.transferFrom(source, count, size
                    - count)) < size)
                ;
        } finally {
            if (source != null) {
                source.close();
                sourceFile.delete();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }
}
