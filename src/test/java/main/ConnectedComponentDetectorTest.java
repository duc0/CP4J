/*
 * Copyright (c) CP4J Project
 */

package main;

import com.vb.tasks.VNOI_CF_NKCITY;
import org.junit.Test;

public class ConnectedComponentDetectorTest {
    @Test
    public void test() {
        TestUtils.checkTest(new VNOI_CF_NKCITY(), "nkcity1");
    }
}
