package com.empower;

import com.empower.utils.ConfigReader;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.util.List;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    private static final String AUTH_USER = ConfigReader.get("auth.user");
    private static final String AUTH_PASS = ConfigReader.get("auth.pass");
    private static final String BROWSER_TYPE = ConfigReader.get("browser", "chromium");
    private static final boolean HEADLESS = ConfigReader.getBoolean("headless", false);

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        playwright = Playwright.create();
        browser = launchBrowser();

        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setViewportSize(1920, 1080);

        if (AUTH_USER != null && !AUTH_USER.isEmpty() &&
                AUTH_PASS != null && !AUTH_PASS.isEmpty()) {
            contextOptions.setHttpCredentials(AUTH_USER, AUTH_PASS);
        }

        context = browser.newContext(contextOptions);
        page = context.newPage();
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);

        System.out.println("\n🚀 Browser started: " + BROWSER_TYPE + (HEADLESS ? " (headless)" : ""));
        System.out.println("📝 Test: " + testInfo.getDisplayName());
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    public Page getPage() {
        return page;
    }

    public BrowserContext getContext() {
        return context;
    }

    private Browser launchBrowser() {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(HEADLESS);

        if (HEADLESS) {
            options.setArgs(List.of("--headless=new"));
        }

        return switch (BROWSER_TYPE.toLowerCase()) {
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit", "safari" -> playwright.webkit().launch(options);
            default -> playwright.chromium().launch(options);
        };
    }
}
