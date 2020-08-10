// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.

package com.guige.tfvc.models;

import java.net.URI;

/**
 * A piece of information about workspace, either basic or detailed.
 */
public class WorkspaceInformation {
    public static class BasicInformation {
        private final String workspaceName;
        private final URI collectionUri;

        public BasicInformation(String workspaceName, URI collectionUri) {
            this.workspaceName = workspaceName;
            this.collectionUri = collectionUri;
        }

        public String getWorkspaceName() {
            return workspaceName;
        }

        public URI getCollectionUri() {
            return collectionUri;
        }
    }

    private final BasicInformation basic;
    private final Workspace detailed;

    private WorkspaceInformation(BasicInformation basic, Workspace detailed) {
        this.basic = basic;
        this.detailed = detailed;
    }

    public static WorkspaceInformation basic(BasicInformation basic) {
        return new WorkspaceInformation(basic, null);
    }

    public static WorkspaceInformation detailed(Workspace detailed) {
        return new WorkspaceInformation(null, detailed);
    }

    public BasicInformation getBasic() {
        return basic;
    }

    public Workspace getDetailed() {
        return detailed;
    }
}
