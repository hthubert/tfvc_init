// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.

package com.guige.tfvc.exceptions;

public class ToolAuthenticationException extends com.guige.tfvc.exceptions.ToolException {
    public ToolAuthenticationException() {
        super(com.guige.tfvc.exceptions.ToolException.KEY_TF_AUTH_FAIL);
    }
}