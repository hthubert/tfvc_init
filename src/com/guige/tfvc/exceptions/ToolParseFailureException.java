// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.

package com.guige.tfvc.exceptions;

public class ToolParseFailureException extends com.guige.tfvc.exceptions.ToolException {

    public ToolParseFailureException() {
        super(com.guige.tfvc.exceptions.ToolException.KEY_TF_PARSE_FAILURE);
    }

    public ToolParseFailureException(Throwable t) {
        super(com.guige.tfvc.exceptions.ToolException.KEY_TF_PARSE_FAILURE, t);
    }
}
