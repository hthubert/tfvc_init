// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.

package com.guige.tfvc.exceptions;

public class ToolBadExitCodeException extends com.guige.tfvc.exceptions.ToolException {
    private final int exitCode;

    public ToolBadExitCodeException(int exitCode) {
        super(com.guige.tfvc.exceptions.ToolException.KEY_TF_BAD_EXIT_CODE);
        this.exitCode = exitCode;
    }

    public ToolBadExitCodeException(int exitCode, Throwable t) {
        super(com.guige.tfvc.exceptions.ToolException.KEY_TF_BAD_EXIT_CODE, t);
        this.exitCode = exitCode;
    }

    @Override
    public String[] getMessageParameters() {
        return new String[] {Integer.toString(exitCode)};
    }
}
