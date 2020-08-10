// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.

package com.guige.tfvc.exceptions;

import com.guige.tfvc.models.ToolVersion;

public class ToolVersionException extends com.guige.tfvc.exceptions.ToolException {
    final ToolVersion versionFound;
    final ToolVersion minimumVersion;

    public ToolVersionException(final ToolVersion versionFound, final ToolVersion minimumVersion) {
        this(versionFound, minimumVersion, null);
    }

    public ToolVersionException(final ToolVersion versionFound, final ToolVersion minimumVersion, Throwable t) {
        super(com.guige.tfvc.exceptions.ToolException.KEY_TF_MIN_VERSION_WARNING, t);
        this.versionFound = versionFound;
        this.minimumVersion = minimumVersion;
    }

    @Override
    public String[] getMessageParameters() {
        return new String[] {versionFound.toString(), minimumVersion.toString()};
    }

}
