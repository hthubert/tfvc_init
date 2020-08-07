// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.

package com.guige.tfvc.common.exceptions;

/**
 * Thrown when a URI string is invalid.
 */
public class MalformedURIException extends RuntimeException {
    public MalformedURIException(final String msg) {
        super(msg);
    }
}
