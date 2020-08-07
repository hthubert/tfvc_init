// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.


package com.guige.tfvc;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(subcommands = {WorkspaceCommand.class})
public class Program implements Runnable {

    private static CommandLine commandLine;

    public static void main(String... args) {
        commandLine = new CommandLine(new Program());
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        new DeviceAuthorization().getAuthenticationInfo();
        commandLine.printVersionHelp(System.out);
    }
}
