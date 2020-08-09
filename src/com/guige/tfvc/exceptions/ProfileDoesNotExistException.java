// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.

package com.guige.tfvc.exceptions;

import com.microsoft.alm.client.model.VssServiceException;

// This class must inherit from VssServiceException to be mapped properly
public class ProfileDoesNotExistException extends VssServiceException implements LocalizedException {
    // The constructor has to match what the Exception mapper expects (we don't use the message param)
    public ProfileDoesNotExistException(final String message, final Exception innerException) {
        super(TeamServicesException.KEY_VSO_NO_PROFILE_ERROR, innerException);
    }

    @Override
    public String getMessageKey() {
        return TeamServicesException.KEY_VSO_NO_PROFILE_ERROR;
    }

    @Override
    public String[] getMessageParameters() {
        return new String[0];
    }
}
