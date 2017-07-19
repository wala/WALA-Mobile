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
		for(File f : new File("/system/framework/").listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				String name = pathname.getName();
				return 
					(name.startsWith("core") || name.startsWith("framework")) && 
					(name.endsWith("jar") || name.endsWith("apk"));
			} 
		})) 
		{
			System.out.println("adding " + f);
			libs.add(f.toURI());
		}
		return libs.toArray(new URI[ libs.size() ]);
	}

	public static URI[] coreLibs() {
		List<URI> libs = new ArrayList<URI>();
		for(File f : new File("/system/framework/").listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				String name = pathname.getName();
				return (name.contains("core") || name.contains("framework")) && name.endsWith(".jar");
			} 
		})) 
		{
			System.out.println("adding " + f);
			libs.add(f.toURI());
		}
		return libs.toArray(new URI[ libs.size() ]);
	}
}
