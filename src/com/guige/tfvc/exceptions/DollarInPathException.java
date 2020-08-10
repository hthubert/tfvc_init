// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.

package com.guige.tfvc.exceptions;

public class DollarInPathException extends ToolException {
    private final String myServerFilePath;

    public DollarInPathException(String fileName) {
        super(KEY_TF_DOLLAR_IN_PATH);
        this.myServerFilePath = fileName;
    }

    @Override
    public String getMessage() {
        return "'$' was found in path component: " + myServerFilePath;
    }

    public String getServerFilePath() {
        return myServerFilePath;
    }

    @Override
    public String[] getMessageParameters() {
        return new String[]{myServerFilePath};
    }
}
