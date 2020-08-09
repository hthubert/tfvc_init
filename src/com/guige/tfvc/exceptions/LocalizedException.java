// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.

package com.guige.tfvc.exceptions;

public interface LocalizedException {
    String getMessageKey();
    String[] getMessageParameters();
}
