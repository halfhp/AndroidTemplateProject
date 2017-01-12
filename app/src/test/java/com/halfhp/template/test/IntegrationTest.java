package com.halfhp.template.test;

import android.content.Context;

import com.halfhp.template.TemplateApp;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

/**
 * Created by halfhp on 1/11/17.
 */
@Ignore
@Config(application = TemplateApp.class, sdk = 23, manifest = "src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class IntegrationTest {

    @BeforeClass
    public static void beforeClass() {
        ShadowLog.stream = System.out;
    }

    protected Context getContext() {
        return RuntimeEnvironment.application;
    }

//    /**
//     * Provides equivalent functionality to the {@link org.mockito.runners.MockitoJUnitRunner}.
//     * http://site.mockito.org/mockito/docs/current/org/mockito/junit/MockitoRule.html
//     */
//    @Rule
//    public MockitoRule rule = MockitoJUnit.rule();
}
