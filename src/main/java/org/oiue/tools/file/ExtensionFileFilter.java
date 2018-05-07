package org.oiue.tools.file;

import java.io.File;
import java.io.FileFilter;

public class ExtensionFileFilter implements FileFilter {
	private String extension;
	
	public ExtensionFileFilter(String extension) {
		this.extension = extension.toLowerCase();
	}
	
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return false;
		}
		String name = file.getName();// find the last
		int index = name.lastIndexOf(".");
		if (index == -1) {
			return false;
		} else if (index == name.length() - 1) {
			return false;
		} else {
			// return this.extension.equalsIgnoreCase(name.substring(index + 1));
			return name.toLowerCase().endsWith(this.extension.indexOf(".") == 0 ? this.extension : "." + this.extension);
		}
	}
}
