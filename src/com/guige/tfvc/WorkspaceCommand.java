// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.


package com.guige.tfvc;

import com.guige.tfvc.commands.CreateWorkspaceCommand;
import com.guige.tfvc.models.Workspace;
import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "workspace",
        description = "Enables you to create, delete, and modify properties and mappings associated with a workspace.")
public class WorkspaceCommand implements Callable<Integer> {
    @ArgGroup(exclusive = true, multiplicity = "1")
    private Action action;

    private static class Action {
        @Option(names = "-new", required = true)
        boolean _new;
        @Option(names = "-delete", required = true)
        boolean _delete;
    }

    @Option(names = "-collection", required = true, description = "<url>")
    private String collection;

    @Option(names = "-noprompt")
    private boolean noprompt;

    @Option(names = "-comment")
    private String comment;

    @Option(names = "-location", defaultValue = "local", description = "server|local")
    private String location;

    @Parameters(arity = "1", description = "at least one File")
    private List<String> workspacename;

    @Override
    public Integer call() {
        AuthenticationInfo info = new DeviceAuthorization().getAuthenticationInfo();
        ServerContext.getInstance().authenticationInfo(info);
        ServerContext.getInstance().collectionURI(collection);
        System.out.printf("User: %s%n",info.getUserName());
        System.out.printf("Password: %s%n",info.getPassword());
        if (action._new) {
            if (StringUtils.isEmpty(comment)){
                comment = "Workspace created through tfvc_init";
            }

            final CreateWorkspaceCommand command = new CreateWorkspaceCommand(
                    ServerContext.getInstance(),
                    workspacename.get(0),
                    comment,
                    null,
                    null,
                    Workspace.Location.fromString(location));
            command.runSynchronously();
        }
        return 0;
    }
}
