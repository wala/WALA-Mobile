package com.ibm.wala.mobile;

/**
 * Created by dolby on 7/17/17.
 */

import com.ibm.wala.core.tests.basic.ExtensionGraphTest;
import com.ibm.wala.core.tests.basic.FloydWarshallTest;
import com.ibm.wala.core.tests.basic.GraphDataflowTest;
import com.ibm.wala.core.tests.basic.OrdinalSetTest;
import com.ibm.wala.core.tests.basic.PathFinderTest;
import com.ibm.wala.core.tests.basic.PrimitivesTest;
import com.ibm.wala.core.tests.basic.WelshPowellTest;
import com.ibm.wala.core.tests.collections.SemiSparseMutableIntSetTest;
import com.ibm.wala.core.tests.collections.TwoLevelVectorTest;
import com.ibm.wala.util.io.FileProviderTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ExtensionGraphTest.class,
        FloydWarshallTest.class,
        GraphDataflowTest.class,
        OrdinalSetTest.class,
        PathFinderTest.class,
        PrimitivesTest.class,
        WelshPowellTest.class,
        SemiSparseMutableIntSetTest.class,
        TwoLevelVectorTest.class,
        FileProviderTest.class})
public class BasicTests {

}
