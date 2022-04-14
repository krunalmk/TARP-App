package com.kmk.awesomescanner.util;

import com.kmk.awesomescanner.ui.GpCodeScanner;

/**
 * Awesome Scanner auto focus mode
 *
 * @see GpCodeScanner#setAutoFocusMode(AutoFocusMode)
 */
public enum AutoFocusMode {
    /**
     * Auto focus camera with the specified interval
     *
     * @see GpCodeScanner#setAutoFocusInterval(long)
     */
    SAFE,

    /**
     * Continuous auto focus, may not work on some devices
     */
    CONTINUOUS
}
