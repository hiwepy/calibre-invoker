/**
 * Copyright (c) 2018, vindell (https://github.com/vindell).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.vindell.calibre.invoker.command;

import java.io.File;
import java.io.IOException;

import org.codehaus.plexus.util.Os;
import org.codehaus.plexus.util.cli.Commandline;

import com.github.vindell.calibre.invoker.exception.CommandLineConfigurationException;
import com.github.vindell.calibre.invoker.request.InvocationRequest;
import com.github.vindell.calibre.invoker.request.LrfviewerInvocationRequest;

/**
 * Read the LRF e-book book.lrf
 * https://manual.calibre-ebook.com/generated/en/lrfviewer.html
 */
public class LrfviewerCommandLineBuilder extends AbstractCommandLineBuilder {

	@Override
	protected void doCommandInternal(InvocationRequest request, Commandline cli)
			throws CommandLineConfigurationException {
		
		if(request instanceof LrfviewerInvocationRequest) {

			LrfviewerInvocationRequest lrfviewerfRequest = ( LrfviewerInvocationRequest) request;
			
			setDisableHyphenation(lrfviewerfRequest, cli);
			setProfile(lrfviewerfRequest, cli);
			setVisualDebug(lrfviewerfRequest, cli);
			setWhiteBackground(lrfviewerfRequest, cli);
			
			// LRS file path. file.lrs
			cli.createArg().setValue(lrfviewerfRequest.getLrsFile().getAbsolutePath());
			
		}
		
	}

	@Override
	protected File findCalibreExecutable() throws CommandLineConfigurationException, IOException {
		
		if (calibreHome == null) {
			findCalibreHome();
		}

		logger.debug("Using ${calibre.home} of: \'" + calibreHome + "\'.");

		if (calibreExecutable == null || !calibreExecutable.isAbsolute()) {
			String executable;
			if (calibreExecutable != null) {
				executable = calibreExecutable.getPath();
			} else if (Os.isFamily("windows")) {
				executable = "lrs2lrf.exe";
			} else {
				executable = "lrs2lrf";
			}

			calibreExecutable = new File(calibreHome, executable);

			try {
				File canonicalMvn = calibreExecutable.getCanonicalFile();
				calibreExecutable = canonicalMvn;
			} catch (IOException e) {
				logger.debug("Failed to canonicalize maven executable: " + calibreExecutable + ". Using as-is.", e);
			}

			if (!calibreExecutable.isFile()) {
				throw new CommandLineConfigurationException("Calibre executable not found at: " + calibreExecutable);
			}
		}

		return calibreExecutable;
	}
	
	protected void setDisableHyphenation(LrfviewerInvocationRequest request, Commandline cli) {
		if(request.isDisableHyphenation()) {
			cli.createArg().setValue("--disable-hyphenation");
		}
	}
	
	protected void setProfile(LrfviewerInvocationRequest request, Commandline cli) {
		if(request.isProfile()) {
			cli.createArg().setValue("--profile");
		}
	}
	
	protected void setVisualDebug(LrfviewerInvocationRequest request, Commandline cli) {
		if(request.isVisualDebug()) {
			cli.createArg().setValue("--visual-debug");
		}
	}
	
	protected void setWhiteBackground(LrfviewerInvocationRequest request, Commandline cli) {
		if(request.isWhiteBackground()) {
			cli.createArg().setValue("--white-background");
		}
	}

}
