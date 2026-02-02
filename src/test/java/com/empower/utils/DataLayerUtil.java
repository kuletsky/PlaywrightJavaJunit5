package com.empower.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;
import java.util.Map;

public class DataLayerUtil {
    @SuppressWarnings("unchecked")
    public static Map<String, Object> clickAndCaptureEvent(Page page, Locator button,
                                                     String eventType, String eventName) {
        return (Map<String, Object>) page.evaluate("""
                    (args) => {
                        const [element, eventType, eventName] = args;
                        return new Promise((resolve) => {
                            const originalPush = window.dataLayer.push.bind(window.dataLayer);
                            window.dataLayer.push = function() {
                                for (let i = 0; i < arguments.length; i++) {
                                    const e = arguments[i];
                                    if (e && e.event === eventType && e.event_name === eventName) {
                                        resolve(JSON.parse(JSON.stringify(e)));
                                    }
                                }
                                return originalPush.apply(window.dataLayer, arguments);
                            };
                
                            element.click();
                
                            setTimeout(() => resolve(null), 3000);
                        });
                    }
                """, List.of(button.elementHandle(), eventType, eventName));
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> hoverAndCaptureEvent(Page page, Locator element,
                                                           String eventType, String eventName) {
//        return (Map<String, Object>) page.evaluate("""
//                (args) => {
//                    const [element, eventType, eventName] = args;
//                    return new Promise((resolve) => {
//                        const originalPush = window.dataLayer.push.bind(window.dataLayer);
//                        window.dataLayer.push = function() {
//                            for (let i = 0; i < arguments.length; i++) {
//                                const e = arguments[i];
//                                if (e && e.event === eventType && e.event_name === eventName) {
//                                    resolve(JSON.parse(JSON.stringify(e)));
//                                }
//                            }
//                            return originalPush.apply(window.dataLayer, arguments);
//                        };
//
//                        element.dispatchEvent(new MouseEvent('mouseenter', {
//                            bubbles: true,
//                            cancelable: true,
//                            view: window
//                        }));
//
//                        setTimeout(() => resolve(null), 3000);
//                    });
//                }
//            """, List.of(element.elementHandle(), eventType, eventName));


        page.evaluate("""
            (args) => {
                const [eventType, eventName] = args;
                window.__capturedHoverEvent = null;
                window.__allCapturedEvents = [];

                const originalPush = window.dataLayer.push.bind(window.dataLayer);
                window.dataLayer.push = function() {
                    for (let i = 0; i < arguments.length; i++) {
                        const e = arguments[i];
                        // Сохраняем ВСЕ события для отладки
                        try {
                            window.__allCapturedEvents.push(JSON.parse(JSON.stringify(e)));
                        } catch(err) {}

                        if (e && e.event === eventType && e.event_name === eventName) {
                            window.__capturedHoverEvent = JSON.parse(JSON.stringify(e));
                            console.log('✅ CAPTURED:', e);
                        }
                    }
                    return originalPush.apply(window.dataLayer, arguments);
                };
                console.log('🔧 Interceptor installed for:', eventType, eventName);
            }
        """, List.of(eventType, eventName));

        System.out.println("🔧 Interceptor installed");

        // 2. Hover через Playwright
        element.hover();
        System.out.println("👆 Hover performed");

        // 3. Ждём подольше
        page.waitForTimeout(5000);

        // 4. Проверяем что пришло
        Object allEvents = page.evaluate("() => window.__allCapturedEvents || []");
        System.out.println("📊 All captured events after hover: " + allEvents);

        // 5. Также проверим весь dataLayer
        Object dataLayerLength = page.evaluate("() => window.dataLayer ? window.dataLayer.length : 0");
        System.out.println("📊 dataLayer length: " + dataLayerLength);

        // 6. Получаем результат
        Map<String, Object> result = (Map<String, Object>) page.evaluate("() => window.__capturedHoverEvent");
        System.out.println("📊 Captured hover event: " + result);

        return result;


//        page.evaluate("""
//            (args) => {
//                const [eventType, eventName] = args;
//                window.__capturedHoverEvent = null;
//
//                const originalPush = window.dataLayer.push.bind(window.dataLayer);
//                window.dataLayer.push = function() {
//                    for (let i = 0; i < arguments.length; i++) {
//                        const e = arguments[i];
//                        if (e && e.event === eventType && e.event_name === eventName) {
//                            window.__capturedHoverEvent = JSON.parse(JSON.stringify(e));
//                        }
//                    }
//                    return originalPush.apply(window.dataLayer, arguments);
//                };
//            }
//        """, List.of(eventType, eventName));
//
//        // 2. Только Playwright hover (это работало!)
//        element.hover();
//
//        // 3. Ждём
//        page.waitForTimeout(1000);
//
//        // 4. Результат
//        return (Map<String, Object>) page.evaluate("() => window.__capturedHoverEvent");
    }
}
