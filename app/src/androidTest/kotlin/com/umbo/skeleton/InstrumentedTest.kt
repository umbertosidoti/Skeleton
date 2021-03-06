package com.umbo.skeleton

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.umbo.skeleton.pages.DetailPage.Companion.whileOnDetailScreen
import com.umbo.skeleton.pages.ListPage.Companion.whileOnListScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class InstrumentedTest {

    @get:Rule
    var mActivityRule: ActivityTestRule<MainActivity> = SkeletonActivityTestRule(MainActivity::class.java)

    @Test
    fun testE2E() {
        whileOnListScreen {
            shouldSeeLoading()
            shouldSeeListResults()
            Thread.sleep(500)
            tapOnElement(0)
        }

        whileOnDetailScreen {
            shouldSeeTitle()
            shouldSeeAlbum()
            shouldSeeUrl()
            shouldSeeImage()
            tapBack()
        }

        whileOnListScreen {
            shouldSeeListResults()
        }
    }
}
