// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.


package com.guige.tfvc;

import com.microsoft.alm.auth.oauth.DeviceFlowResponse;
import com.microsoft.alm.helpers.Action;
import java.util.Scanner;

/**
 * Implements DeviceFlowResponsePrompt which creates a console to to show user
 * relevant information from the device flow response.
 */
public class DeviceFlowResponseConsolePrompt implements DeviceFlowResponsePrompt {
    @Override
    public Action<DeviceFlowResponse> getCallback(Action<String> cancellationCallback) {
        return response -> {
            System.out.println("To complete the authentication process, visit the following URL:");
            System.out.printf("  %s%n", response.getVerificationUri().toString());
            System.out.println("When prompted, enter the following code:");
            System.out.printf("  %s%n", response.getUserCode());
            System.out.println("Once authenticated from the browser, press enter key.");
            new Scanner(System.in).nextLine();
        };
    }
}
