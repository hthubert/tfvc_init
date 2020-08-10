// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.

package com.guige.tfvc.utils;

import com.sun.jna.Platform;
import org.apache.commons.io.comparator.PathFileComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.UnknownHostException;

public class SystemHelper {
    private static Logger logger = LoggerFactory.getLogger(SystemHelper.class);
    private final static String COMPUTER_NAME = "computername";

    /**
     * Gets the computer name
     *
     * @return local host name if found, falls back to computername env variable otherwise
     */
    public static String getComputerName() {
        try {
            java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
            return localMachine.getHostName();
        } catch (UnknownHostException e) {
            logger.warn("getComputerName failed", e);
            return System.getenv(COMPUTER_NAME);
        }
    }

    /**
     * This method is a wrapper for the System.getenv method which cannot be mocked.
     *
     * @param name
     * @return
     */
    public static String getEnvironmentVariable(final String name) {
        return System.getenv(name);
    }

    /**
     * Takes in a path and converts it to Unix standard if Windows OS is detected
     *
     * @param path
     * @return path with forward slashes
     */
    public static String getUnixPath(final String path) {
        if (Platform.isWindows()) {
            return path.replace("\\", "/");
        } else {
            return path;
        }
    }

    /**
     * Compares to file paths to see if they are the same based on the OS that is being used
     *
     * @param path1
     * @param path2
     * @return
     */
    public static boolean areFilePathsSame(final String path1, final String path2) {
        return PathFileComparator.PATH_SYSTEM_COMPARATOR.compare(new File(path1), new File(path2)) == 0 ? true : false;
    }

    /**
     * Convert a string value into an integer or a default value.
     *
     * @return
     */
    public static int toInt(final String value, final int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.warn("Could not convert '" + value + "' into an integer.", e);
            return defaultValue;
        }
    }
}
