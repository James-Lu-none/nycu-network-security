/**
 * NYCU AES Example Module
 */
module NYCUAESExample {
    // Java Desktop (Swing) 模組
    requires java.desktop;
    
    // Java Base (包含加密功能)
    requires java.base;
    
    exports nycu.main;
    exports nycu.tools;
}