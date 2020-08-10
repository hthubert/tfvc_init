// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.

package com.guige.tfvc.exceptions;

/**
 * Exception for when the tool doesn't have enough memory to run
 */
public class ToolMemoryException extends com.guige.tfvc.exceptions.ToolException {
    private static final String TOOL_MEMORY_OUTPUT = "Error occurred during initialization of VM\nCould not reserve enough space for";
    private final String toolPath;

    public ToolMemoryException(final String toolPath) {
        super(com.guige.tfvc.exceptions.ToolException.KEY_TF_OOM);
        this.toolPath = toolPath;
    }

    @Override
    public String[] getMessageParameters() {
        return new String[]{toolPath};
    }

    public static String getErrorMsg() {
        return TOOL_MEMORY_OUTPUT;
    }
}
