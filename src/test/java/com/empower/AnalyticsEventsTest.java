package com.empower;

import com.empower.pages.AnalyticsEventsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AnalyticsEventsTest extends BaseTest {

    // ==================== CLICK EVENTS ====================

    static Stream<org.junit.jupiter.params.provider.Arguments> clickEventsProvider() {
        return Stream.of(
                // elementName, expectedEvent, expectedEventName, expectedEventDetail
                org.junit.jupiter.params.provider.Arguments.of("primaryButton", "button_click", "button_click_cta", "/products-solutions/private-client"),
                org.junit.jupiter.params.provider.Arguments.of("primaryButton_PC", "button_click", "button_click_cta", "/products-solutions/private-client"),
                org.junit.jupiter.params.provider.Arguments.of("brandedGoldButton", "button_click", "button_click_branded", "/products-solutions/private-client"),
                org.junit.jupiter.params.provider.Arguments.of("secondaryButton", "button_click", "button_click_secondary_white", "/products-solutions/private-client"),
                org.junit.jupiter.params.provider.Arguments.of("secondaryButton_PC", "button_click", "button_click_secondary_white", "/products-solutions/private-client"),
                org.junit.jupiter.params.provider.Arguments.of("tile_1", "tile_event", "tile_click", "/the-currency/money/can-you-retire-a-million-dollars"),
                org.junit.jupiter.params.provider.Arguments.of("tile_2", "tile_event", "tile_click", "/press-center/empower-signs-new-partnership-hard-rock-stadium-and-miami-dolphins"),
                org.junit.jupiter.params.provider.Arguments.of("tile_3", "tile_event", "tile_click", "/the-currency/work/five-habits-of-excellent-retirement-savers"),
                org.junit.jupiter.params.provider.Arguments.of("linkText", "link_click", "link_click", "/"),
                org.junit.jupiter.params.provider.Arguments.of("linkCardText", "link_click", "link_click", "/"),
                org.junit.jupiter.params.provider.Arguments.of("secondaryBentoButton", "button_click", "button_click_bento_white", "/products-solutions/private-client"),
                org.junit.jupiter.params.provider.Arguments.of("PrimaryBentoButton", "button_click", "button_click_bento_blue", "/products-solutions/private-client"),
                org.junit.jupiter.params.provider.Arguments.of("securityCenterButton", "navigation_click", "footer_sub_navigation_click", "/participant/#/articles/securityCenter?"),
                org.junit.jupiter.params.provider.Arguments.of("accessibilityButton", "navigation_click", "footer_sub_navigation_click", "/participant/#/articles/accessibility?"),
                org.junit.jupiter.params.provider.Arguments.of("cybersecurityButton", "navigation_click", "footer_navigation_click", "/individuals/about-empower/cybersecurity"),
                org.junit.jupiter.params.provider.Arguments.of("aboutUsButton", "navigation_click", "footer_navigation_click", "/about-us"),
                org.junit.jupiter.params.provider.Arguments.of("contactUsButton", "navigation_click", "footer_navigation_click", "/contact"),
                org.junit.jupiter.params.provider.Arguments.of("loginButton", "navigation_click", "login_register_click", "/login-v1"),
                org.junit.jupiter.params.provider.Arguments.of("registerButton", "navigation_click", "login_register_click", "/signup"),
                org.junit.jupiter.params.provider.Arguments.of("IndividualsMenu", "navigation_click", "top_navigation_click", "/home"),
                org.junit.jupiter.params.provider.Arguments.of("PlanSponsorsMenu", "navigation_click", "top_navigation_click", "/plan-sponsors"),
                org.junit.jupiter.params.provider.Arguments.of("FinancialProfessMenu", "navigation_click", "top_navigation_click", "/financial-professionals"),
                org.junit.jupiter.params.provider.Arguments.of("expendSubmenuMain", "navigation_click", "sub_navigation_click", "Expand")
        );
    }

    @ParameterizedTest(name = "Click {0} → {1}/{2}")
    @MethodSource("clickEventsProvider")
    @DisplayName("Click Analytics Events")
    void testClickAnalytics(String elementName, String expectedEvent,
                            String expectedEventName, String expectedEventDetail) {

        Map<String, Object> event = new AnalyticsEventsPage(getPage())
                .findElement(elementName)
                .getTextOfElement()
                .clickAndCaptureElement(expectedEvent, expectedEventName)
                .getEvent();

        assertNotNull(event, "Event not captured for: " + elementName);
        assertEquals(expectedEvent, event.get("event"));
        assertEquals(expectedEventName, event.get("event_name"));
        assertEquals(event.get("expectedElementText"), event.get("event_category"));
        assertEquals(expectedEventDetail, event.get("event_detail"));
    }

    // ==================== SOCIAL BUTTONS ====================

    static Stream<org.junit.jupiter.params.provider.Arguments> socialButtonsProvider() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("facebookButton", "social_click", "social_click", "/officialempowertoday"),
                org.junit.jupiter.params.provider.Arguments.of("xButton", "social_click", "social_click", "/empowertoday?lang=en"),
                org.junit.jupiter.params.provider.Arguments.of("snapchatButton", "social_click", "social_click", "/add/empowertoday"),
                org.junit.jupiter.params.provider.Arguments.of("linkedinButton", "social_click", "social_click", "/company/empowertoday"),
                org.junit.jupiter.params.provider.Arguments.of("instagramButton", "social_click", "social_click", "/officialempowertoday/"),
                org.junit.jupiter.params.provider.Arguments.of("youtubeButton", "social_click", "social_click", "/channel/UCFPLlGp16vPjBb-G7SnUWhQ"),
                org.junit.jupiter.params.provider.Arguments.of("tiktokButton", "social_click", "social_click", "/@empowertoday?lang=en")
        );
    }

    @ParameterizedTest(name = "Social click {0}")
    @MethodSource("socialButtonsProvider")
    @DisplayName("Social Buttons Analytics")
    void testClickSocialButtonsAnalytics(String elementName, String expectedEvent,
                                         String expectedEventName, String expectedEventDetail) {

        Map<String, Object> event = new AnalyticsEventsPage(getPage())
                .findElement(elementName)
                .getTitleOfElement()
                .clickAndCaptureElement(expectedEvent, expectedEventName)
                .getEvent();

        assertNotNull(event, "Event not captured for: " + elementName);
        assertEquals(expectedEvent, event.get("event"));
        assertEquals(expectedEventName, event.get("event_name"));
        assertEquals(event.get("expectedElementTitle"), event.get("event_category"));
        assertEquals(expectedEventDetail, event.get("event_detail"));
    }

    // ==================== PRIMARY MENU ====================

    static Stream<org.junit.jupiter.params.provider.Arguments> primaryMenuProvider() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("ProdAndServMenu", "navigation_click", "main_navigation_click"),
                org.junit.jupiter.params.provider.Arguments.of("ToolsMenu", "navigation_click", "main_navigation_click"),
                org.junit.jupiter.params.provider.Arguments.of("LearnMenu", "navigation_click", "main_navigation_click"),
                org.junit.jupiter.params.provider.Arguments.of("WhyEmpowerMenu", "navigation_click", "main_navigation_click")
        );
    }

    @ParameterizedTest(name = "Primary menu {0}")
    @MethodSource("primaryMenuProvider")
    @DisplayName("Primary Menu Analytics")
    void testClickPrimaryMenuAnalytics(String elementName, String expectedEvent,
                                       String expectedEventName) {

        Map<String, Object> event = new AnalyticsEventsPage(getPage())
                .findElement(elementName)
                .getTextOfElement()
                .getLabelOfElement()
                .clickAndCaptureElement(expectedEvent, expectedEventName)
                .getEvent();

        assertNotNull(event, "Event not captured for: " + elementName);
        assertEquals(expectedEvent, event.get("event"));
        assertEquals(expectedEventName, event.get("event_name"));
        assertEquals(event.get("expectedElementText"), event.get("event_category"));
        assertEquals(event.get("expectedElementLabel"), event.get("event_detail"));
    }

    // ==================== CLICK WITH SET CATEGORY ====================

    static Stream<org.junit.jupiter.params.provider.Arguments> clickWithSetCategoryProvider() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("downloadAppStore", "button_click", "app_store_click", "App Store", ""),
                org.junit.jupiter.params.provider.Arguments.of("downloadGooglePlay", "button_click", "app_store_click", "Google Play", ""),
                org.junit.jupiter.params.provider.Arguments.of("empowerLogo", "social_click", "social_click", "Empower logo", "/"),
                org.junit.jupiter.params.provider.Arguments.of("carouselNext", "tile_event", "tile_move", "prev_next", ""),
                org.junit.jupiter.params.provider.Arguments.of("carouselPrev", "tile_event", "tile_move", "prev_next", "")
        );
    }

    @ParameterizedTest(name = "Click {0} with category {3}")
    @MethodSource("clickWithSetCategoryProvider")
    @DisplayName("Click with Set Category Analytics")
    void testClickWithSetCategoryAnalytics(String elementName, String expectedEvent,
                                           String expectedEventName, String expectedEventCategory,
                                           String expectedEventDetail) {

        Map<String, Object> event = new AnalyticsEventsPage(getPage())
                .findElement(elementName)
                .clickAndCaptureElement(expectedEvent, expectedEventName)
                .getEvent();

        assertNotNull(event, "Event not captured for: " + elementName);
        assertEquals(expectedEvent, event.get("event"));
        assertEquals(expectedEventName, event.get("event_name"));
        assertEquals(expectedEventCategory, event.get("event_category"));
        assertEquals(expectedEventDetail, event.get("event_detail"));
    }

    // ==================== FAQ EXPAND/CONTRACT ====================

    @Test
    @DisplayName("FAQ Expand Analytics")
    void testClickFAQExpendAnalytics() {
        String elementName = "faqExpendContract";

        Map<String, Object> event = new AnalyticsEventsPage(getPage())
                .findElement(elementName)
                .getTextOfElement()
                .clickAndCaptureElement("expand_contract", "expand_contract")
                .getEvent();

        assertNotNull(event, "Event not captured for: " + elementName);
        assertEquals("expand_contract", event.get("event"));
        assertEquals("expand_contract", event.get("event_name"));
        assertEquals("faq", event.get("event_category"));
        assertEquals(event.get("expectedElementText"), event.get("event_detail"));
    }

    @Test
    @DisplayName("FAQ Contract Analytics")
    void testClickFAQContractAnalytics() {
        String elementName = "faqExpendContract";

        Map<String, Object> event = new AnalyticsEventsPage(getPage())
                .expandFAQ()
                .findElement(elementName)
                .getTextOfElement()
                .clickAndCaptureElement("expand_contract", "expand_contract")
                .getEvent();

        assertNotNull(event, "Event not captured for: " + elementName);
        assertEquals("expand_contract", event.get("event"));
        assertEquals("expand_contract", event.get("event_name"));
        assertEquals("faq", event.get("event_category"));
        assertEquals(event.get("expectedElementText"), event.get("event_detail"));
    }

    // ==================== MENU EVENTS ====================

    static Stream<org.junit.jupiter.params.provider.Arguments> menuEventsProvider() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("wealthManagementMenu", "navigation_click", "sub_navigation_click", "Expand"),
                org.junit.jupiter.params.provider.Arguments.of("highYieldMenu", "navigation_click", "navigation_click", "/cash"),
                org.junit.jupiter.params.provider.Arguments.of("rolloverMenu", "navigation_click", "navigation_click", "/products-solutions/rollover"),
                org.junit.jupiter.params.provider.Arguments.of("irasMenu", "navigation_click", "navigation_click", "/products-solutions/iras"),
                org.junit.jupiter.params.provider.Arguments.of("investmentMenu", "navigation_click", "navigation_click", "/products-solutions/investment-accounts")
        );
    }

    @ParameterizedTest(name = "Menu click {0}")
    @MethodSource("menuEventsProvider")
    @DisplayName("Menu Click Analytics")
    void testClickMenuAnalytics(String elementName, String expectedEvent,
                                String expectedEventName, String expectedEventDetail) {

        Map<String, Object> event = new AnalyticsEventsPage(getPage())
                .clickProdAndServiceMenu()
                .findElement(elementName)
                .getTextOfElement()
                .clickAndCaptureElement(expectedEvent, expectedEventName)
                .getEvent();

        assertNotNull(event, "Event not captured for: " + elementName);
        assertEquals(expectedEvent, event.get("event"));
        assertEquals(expectedEventName, event.get("event_name"));
        assertEquals(event.get("expectedElementText"), event.get("event_category"));
        assertEquals(expectedEventDetail, event.get("event_detail"));
    }

    // ==================== SUBMENU EVENTS ====================

    static Stream<org.junit.jupiter.params.provider.Arguments> submenuEventsProvider() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("wealthManagementMenu", "navigation_click", "sub_navigation_click", "Expand"),
                org.junit.jupiter.params.provider.Arguments.of("privetClientMenu", "navigation_click", "navigation_click", "/products-solutions/private-client"),
                org.junit.jupiter.params.provider.Arguments.of("personalStrategyMenu", "navigation_click", "navigation_click", "/products-solutions/personal-strategy")
        );
    }

    @ParameterizedTest(name = "Submenu click {0}")
    @MethodSource("submenuEventsProvider")
    @DisplayName("Submenu Click Analytics")
    void testClickSubmenuAnalytics(String elementName, String expectedEvent,
                                   String expectedEventName, String expectedEventDetail) {

        Map<String, Object> event = new AnalyticsEventsPage(getPage())
                .clickProdAndServiceMenu()
                .clickWealthManagementSubmenu()
                .findElement(elementName)
                .getTextOfElement()
                .clickAndCaptureElement(expectedEvent, expectedEventName)
                .getEvent();

        assertNotNull(event, "Event not captured for: " + elementName);
        assertEquals(expectedEvent, event.get("event"));
        assertEquals(expectedEventName, event.get("event_name"));
        assertEquals(event.get("expectedElementText"), event.get("event_category"));
        assertEquals(expectedEventDetail, event.get("event_detail"));
    }

    // ==================== MODAL EVENTS ====================

    static Stream<org.junit.jupiter.params.provider.Arguments> modalEventsProvider() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("continueButton", "button_click", "button_click_modal", ""),
                org.junit.jupiter.params.provider.Arguments.of("cancelButton", "button_click", "button_click_modal", "")
        );
    }

    @ParameterizedTest(name = "Modal click {0}")
    @MethodSource("modalEventsProvider")
    @DisplayName("Modal Click Analytics")
    void testClickModalAnalytics(String elementName, String expectedEvent,
                                 String expectedEventName, String expectedEventDetail) {

        Map<String, Object> event = new AnalyticsEventsPage(getPage())
                .clickTextLink()
                .findElement(elementName)
                .getTextOfElement()
                .clickAndCaptureElement(expectedEvent, expectedEventName)
                .getEvent();

        assertNotNull(event, "Event not captured for: " + elementName);
        assertEquals(expectedEvent, event.get("event"));
        assertEquals(expectedEventName, event.get("event_name"));
        assertEquals(event.get("expectedElementText"), event.get("event_category"));
        assertEquals(expectedEventDetail, event.get("event_detail"));
    }

    // ==================== HOVER MENU EVENTS ====================

    static Stream<org.junit.jupiter.params.provider.Arguments> hoverMenuEventsProvider() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("wealthManagementMenu", "navigation_click", "sub_navigation_click", "Expand"),
                org.junit.jupiter.params.provider.Arguments.of("highYieldMenu", "navigation_click", "navigation_click", "/cash"),
                org.junit.jupiter.params.provider.Arguments.of("rolloverMenu", "navigation_click", "navigation_click", "/products-solutions/rollover"),
                org.junit.jupiter.params.provider.Arguments.of("irasMenu", "navigation_click", "navigation_click", "/products-solutions/iras"),
                org.junit.jupiter.params.provider.Arguments.of("investmentMenu", "navigation_click", "navigation_click", "/products-solutions/investment-accounts")
        );
    }

    @ParameterizedTest(name = "Hover menu {0}")
    @MethodSource("hoverMenuEventsProvider")
    @DisplayName("Hover Menu Analytics")
    void testHoverMenuAnalytics(String elementName, String expectedEvent,
                                String expectedEventName, String expectedEventDetail) {

        Map<String, Object> event = new AnalyticsEventsPage(getPage())
                .clickProdAndServiceMenu()
                .findElement(elementName)
                .getTextOfElement()
                .hoverAndCaptureElement(expectedEvent, expectedEventName)
                .getEvent();

        assertNotNull(event, "Event not captured for: " + elementName);
        assertEquals(expectedEvent, event.get("event"));
        assertEquals(expectedEventName, event.get("event_name"));
        assertEquals(event.get("expectedElementText"), event.get("event_category"));
        assertEquals(expectedEventDetail, event.get("event_detail"));
    }

    // ==================== HOVER SUBMENU EVENTS ====================

    static Stream<org.junit.jupiter.params.provider.Arguments> hoverSubmenuEventsProvider() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("privetClientMenu", "navigation_hover", "navigation_hover", "")
        );
    }

    @ParameterizedTest(name = "Hover submenu {0}")
    @MethodSource("hoverSubmenuEventsProvider")
    @DisplayName("Hover Submenu Analytics")
    void testHoverSubmenuAnalytics(String elementName, String expectedEvent,
                                   String expectedEventName, String expectedEventDetail) {

        Map<String, Object> event = new AnalyticsEventsPage(getPage())
                .clickProdAndServiceMenu()
                .clickWealthManagementSubmenu()
                .findElement(elementName)
                .getTextOfElement()
                .hoverAndCaptureElement(expectedEvent, expectedEventName)
                .getEvent();

        assertNotNull(event, "Event not captured for: " + elementName);
        assertEquals(expectedEvent, event.get("event"));
        assertEquals(expectedEventName, event.get("event_name"));
        assertEquals(event.get("expectedElementText"), event.get("event_category"));
        assertEquals(expectedEventDetail, event.get("event_detail"));
    }

    // ==================== BENTO BOX HOVER ====================

    @Test
    @DisplayName("Hover Bento Box Analytics")
    void testHoverBentoBoxAnalytics() {
        String elementName = "expectedBentoBox";

        Map<String, Object> event = new AnalyticsEventsPage(getPage())
                .findElement(elementName)
                .getTextOfElement()
                .hoverAndCaptureElement("hover_event", "bento_hover")
                .getEvent();

        assertNotNull(event, "Event not captured for: " + elementName);
        assertEquals("hover_event", event.get("event"));
        assertEquals("bento_hover", event.get("event_name"));
        assertEquals(event.get("expectedElementText"), event.get("event_category"));
        assertEquals("", event.get("event_detail"));
    }
}