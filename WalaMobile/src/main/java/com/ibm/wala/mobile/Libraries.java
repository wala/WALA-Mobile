package com.ibm.wala.mobile;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Libraries {

	public static URI[] analysisLibs() throws IOException, URISyntaxException { 
		List<URI> result = new ArrayList<URI>();
		String libPath = "/data/WALA/stdlibs/" + android.os.Build.VERSION.RELEASE;
		System.err.println(libPath);
		for (String lib : new File(libPath).list()) {
			System.err.println(lib);
			result.add(new File(libPath + "/" + lib).toURI());
		}
		
		return result.toArray(new URI[ result.size() ]);
	}

	public static URI[] systemLibs() {
		List<URI> libs = new ArrayList<URI>();
		for (String dir : new String[]{"arm", "x86_64", "x86"}) {
			File[] fs = new File("/system/framework/" + dir + "/").listFiles((File pathname) -> {
				String name = pathname.getName();
				return name.equals("boot.oat") || name.equals("boot-core-libart.oat");
			});
			if (fs != null) {
				for (File f : fs) {
					System.out.println("adding " + f);
					libs.add(f.toURI());
				}

				return libs.toArray(new URI[libs.size()]);
			}
		}

		assert false;
		return null;
	}
}
