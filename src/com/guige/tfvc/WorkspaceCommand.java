// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.


package com.guige.tfvc;

import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.List;

@Command(name = "workspace")
public class WorkspaceCommand implements Runnable {
    @ArgGroup(exclusive = true, multiplicity = "0")
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
    public void run() {

    }
}
