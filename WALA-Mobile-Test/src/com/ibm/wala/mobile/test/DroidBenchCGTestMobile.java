package com.ibm.wala.mobile.test;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.ibm.wala.dalvik.test.callGraph.DroidBenchCGTest;
import com.ibm.wala.mobile.Libraries;
import com.ibm.wala.types.MethodReference;

public abstract class DroidBenchCGTestMobile extends DroidBenchCGTest {

	protected DroidBenchCGTestMobile(URI[] androidLibs, File androidJavaJar, String apkFile, Set<MethodReference> uncalled) {
		super(androidLibs, androidJavaJar, apkFile, uncalled);
	}

	@Override
	protected void assertion(String string, boolean empty) {
		Assert.assertTrue(string, empty);
	}
		  
	public static Test suite(Class<? extends DroidBenchCGTestMobile> cls, String dir) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		TestSuite suite = new TestSuite(dir);
		suite.setName(dir);
		final Constructor<? extends DroidBenchCGTest> ctor = cls.getConstructor(URI[].class, File.class, String.class, Set.class);
		for(final Object[] params : DroidBenchCGTest.generateData(Libraries.systemLibs(), null, dir)) {
			suite.addTest(new TestCase() {
				private final DroidBenchCGTest test = ctor.newInstance(params);
				@Override
				public String getName() {
					return test.apkFile();
				}
				@Override
				public void runTest() throws Exception {
					test.runTest();
				}
			});
		}					
		return suite;
	}
}
