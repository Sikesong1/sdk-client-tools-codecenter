/*
 * Black Duck Software Suite SDK
 * Copyright (C) 2016 Black Duck Software, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package com.blackducksoftware.sdk.codecenter.client.examples.application.attachment;

import org.kohsuke.args4j.Argument;
import org.springframework.util.StringUtils;

import com.blackducksoftware.sdk.codecenter.application.data.ApplicationAttachment;
import com.blackducksoftware.sdk.codecenter.application.data.ApplicationAttachmentToken;
import com.blackducksoftware.sdk.codecenter.application.data.ApplicationNameVersionToken;
import com.blackducksoftware.sdk.codecenter.client.examples.BDCodeCenterSample;
import com.blackducksoftware.sdk.codecenter.client.util.CodeCenterServerProxy;
import com.blackducksoftware.sdk.codecenter.user.data.User;

public class SampleGetApplicationAttachmentDetails extends BDCodeCenterSample {

    public static void main(String[] args) {
        new SampleGetApplicationAttachmentDetails(args).run();
    }

    @Argument(index = 3, required = true, metaVar = "applicationName", usage = "the name of the application to retrieve attachment details from")
    protected String applicationName;

    @Argument(index = 4, required = true, metaVar = "applicationVersion", usage = "the application version")
    protected String applicationVersion;

    @Argument(index = 5, required = true, metaVar = "attachmentName", usage = "the name of the attachment to retrieve details about")
    protected String attachmentName;

    public SampleGetApplicationAttachmentDetails(String[] args) {
        super(args);
    }

    @Override
    public void run(CodeCenterServerProxy proxy) throws Exception {
        // Create the attachment token
        ApplicationNameVersionToken appToken = new ApplicationNameVersionToken();
        appToken.setName(applicationName);
        appToken.setVersion(applicationVersion);

        ApplicationAttachmentToken token = new ApplicationAttachmentToken();
        token.setApplicationId(appToken);
        token.setFileName(attachmentName);

        // Retrieve the attachment details
        ApplicationAttachment attachment = proxy.getApplicationApi().getApplicationAttachmentDetails(token);

        // Retrieve the uploader's details
        User uploader = proxy.getUserApi().getUser(attachment.getUserUploaded());

        // Print success information
        System.out.println("Successfully retrieved attachment details from application \"" + applicationName + "\" version \"" + applicationVersion + "\":");
        System.out.println();
        System.out.println("ID:           " + attachment.getId());
        System.out.println("Name:         " + attachment.getFileName());
        System.out.println("Type:         " + attachment.getContentType());
        System.out.println("Size (bytes): " + attachment.getFilesizeBytes());
        System.out.println("Uploaded by:  " + uploader.getFirstName() + " " + uploader.getLastName() + " (" + uploader.getName().getName() + ")");
        System.out.println("Uploaded at:  " + attachment.getTimeUploaded());
        if (StringUtils.hasText(attachment.getDescription())) {
            System.out.println("Description:\n" + attachment.getDescription());
        }
    }

}
