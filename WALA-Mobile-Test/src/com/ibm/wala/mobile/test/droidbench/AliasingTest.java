package com.ibm.wala.mobile.test.droidbench;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Set;

import junit.framework.Test;

import com.ibm.wala.types.MethodReference;

public class AliasingTest extends DroidBenchCGTestMobile {

	public AliasingTest(URI[] androidLibs, File androidJavaJar, String apkFile, Set<MethodReference> uncalled) {
		super(androidLibs, androidJavaJar, apkFile, uncalled);
	}
	
	public static Test suite() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		return suite(AliasingTest.class, "Aliasing");
	}
}
