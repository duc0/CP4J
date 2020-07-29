/*
 * Copyright (c) CP4J Project
 */

package main;

import com.vb.tasks.VNOI_CF_LIQ;
import com.vb.tasks.VNOI_CF_NKPALIN;
import com.vb.tasks.VNOI_CF_QBMAX;
import org.junit.Test;

public class NDArrayTest {
    @Test
    public void test() {
        TestUtils.checkTest(new VNOI_CF_LIQ(), "liq1");
        TestUtils.checkTest(new VNOI_CF_NKPALIN(), "nkpalin1");
        TestUtils.checkTest(new VNOI_CF_QBMAX(), "qbmax1");
    }
}
