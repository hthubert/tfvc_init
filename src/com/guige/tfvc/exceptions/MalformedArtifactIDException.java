// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.

package com.guige.tfvc.exceptions;

import com.guige.tfvc.artifact.ArtifactID;

import java.text.MessageFormat;

/**
 * Thrown when an artifact ID is not in the proper format.
 */
public class MalformedArtifactIDException extends RuntimeException {
    public MalformedArtifactIDException(final ArtifactID id) {
        super(MessageFormat.format(
                "Malformed artifact id: tool=[{0}] artifactType=[{1}] toolSpecificId=[{2}]",
                id.getTool(),
                id.getArtifactType(),
                id.getToolSpecificID()));
    }
}
