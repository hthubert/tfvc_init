// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.

package com.guige.tfvc;

import com.guige.tfvc.tools.TfTool;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import java.util.concurrent.Callable;

@Command(subcommands = {WorkspaceCommand.class})
public class Program implements Callable<Integer> {

    private static CommandLine commandLine;

    public static void main(String... args) {
        BasicConfigurator.configure();
        TfTool.TF_HOME = "d:\\app\\TEE-CLC-14.134.0\\tf.cmd";
        //PropertyConfigurator.configure("log4j.properties");
        commandLine = new CommandLine(new Program());
        try {
            commandLine.parseArgs(args);
        } catch (CommandLine.ParameterException e) {
            commandLine.usage(System.out);
            return;
        }
        if (commandLine.isUsageHelpRequested()) {
            commandLine.usage(System.out);
            return;
        } else if (commandLine.isVersionHelpRequested()) {
            commandLine.printVersionHelp(System.out);
            return;
        }
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        commandLine.usage(System.out);
        return 0;
    }
}
