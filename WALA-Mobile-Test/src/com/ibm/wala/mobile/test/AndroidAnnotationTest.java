package com.ibm.wala.mobile.test;

import java.io.IOException;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import android.util.Log;

import com.ibm.wala.core.tests.ir.AnnotationTest;
import com.ibm.wala.dalvik.test.DalvikTestBase;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.mobile.Libraries;

public class AndroidAnnotationTest extends AndroidTestCase {

	private static IClassHierarchy makeCHA() {
		try {
			AnalysisScope dalvikScope = DalvikTestBase.makeDalvikScope(Libraries.coreLibs(), null, TEST_DEX);
			return ClassHierarchy.make(dalvikScope);    
		} catch (IOException | ClassHierarchyException e) {
			Assert.assertFalse("failed to make class hierarchy: " + e.getMessage(), true);
			return null;
		}
	}

	private static AnnotationTest multipleInheritence = new AnnotationTest(makeCHA()) { 
		  @Override
		  protected void assertEquals(Object a, Object b) {
			  Log.i("AnnotationTest", a + " must equal " + b);
		    Assert.assertEquals(a, b);
		  }

		  @Override
		  protected void assertNotNull(String msg, Object obj) {
			  Log.i("AnnotationTest", msg);
		    Assert.assertNotNull(msg, obj);
		  }

		  @Override
		  protected void assertTrue(String x, boolean b) {
			  Log.i("AnnotationTest", x);
		    Assert.assertTrue(x, b);
		  }
	};
	
	public static final String TEST_DEX = "/data/test/com.ibm.wala.core.testdata_1.0.0a.dex";
		
	/**
	 * @throws Exception
	 * @see com.ibm.wala.core.tests.ir.AnnotationTest#testClassAnnotations1()
	 */
	public void testClassAnnotations1() throws Exception {
		multipleInheritence.testClassAnnotations1();
	}

	/**
	 * @throws Exception
	 * @see com.ibm.wala.core.tests.ir.AnnotationTest#testClassAnnotations2()
	 */
	public void testClassAnnotations2() throws Exception {
		multipleInheritence.testClassAnnotations2();
	}

	/**
	 * @throws Exception
	 * @see com.ibm.wala.core.tests.ir.AnnotationTest#testClassAnnotations3()
	 */
	public void testClassAnnotations3() throws Exception {
		multipleInheritence.testClassAnnotations3();
	}

	/**
	 * @throws Exception
	 * @see com.ibm.wala.core.tests.ir.AnnotationTest#testClassAnnotations4()
	 */
	public void testClassAnnotations4() throws Exception {
		multipleInheritence.testClassAnnotations4();
	}

	/**
	 * @throws Exception
	 * @see com.ibm.wala.core.tests.ir.AnnotationTest#testParamAnnotations1()
	 */
	public void testParamAnnotations1() throws Exception {
		multipleInheritence.testParamAnnotations1();
	}
	
	
}
