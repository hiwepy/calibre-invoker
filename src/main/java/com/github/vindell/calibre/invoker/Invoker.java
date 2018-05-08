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
package com.github.vindell.calibre.invoker;

import java.io.File;

import com.github.vindell.calibre.invoker.exception.CalibreInvocationException;
import com.github.vindell.calibre.invoker.request.InvocationRequest;

/**
 * Provides a facade to invoke Calibre.
 */
public interface Invoker {

	/**
	 * The role name used to register implementations of this interface within
	 * Plexus.
	 */
	String ROLE = Invoker.class.getName();

	/**
	 * Executes Calibre using the parameters specified by the given invocation
	 * request. Parameters not specified by the invocation request will be derived
	 * from the state of this invoker instance. In case both the invoker instance
	 * and the invocation request provide a value for a particular option, the value
	 * from the invocation request dominates.
	 * 
	 * @param request
	 *            The invocation request to execute, must not be <code>null</code>.
	 * @return The result of the Calibre invocation, never <code>null</code>.
	 * @throws CalibreInvocationException
	 */
	InvocationResult execute(InvocationRequest request) throws CalibreInvocationException;

	/**
	 * Gets the working directory for the Calibre invocation.
	 * 
	 * @return The working directory for the Calibre invocation or <code>null</code>
	 *         if the working directory is derived from the base directory of the
	 *         processed POM.
	 */
	File getWorkingDirectory();

	/**
	 * Gets the logger used by this invoker to output diagnostic messages.
	 * 
	 * @return The logger used by this invoker to output diagnostic messages, never
	 *         <code>null</code>.
	 */
	InvokerLogger getLogger();

	/**
	 * Gets the path to the base directory of the Calibre installation used to
	 * invoke Calibre.
	 * 
	 * @return The path to the base directory of the Calibre installation or
	 *         <code>null</code> if using the default Calibre installation.
	 */
	File getCalibreHome();

	/**
	 * Sets the path to the base directory of the Calibre installation used to
	 * invoke Calibre. This parameter may be left unspecified to use the default
	 * Calibre installation which will be discovered by evaluating the system
	 * property <code>maven.home</code> and the environment variable
	 * <code>M2_HOME</code>.
	 * 
	 * @param calibreHome
	 *            The path to the base directory of the Calibre installation, may be
	 *            <code>null</code> to use the default Calibre installation.
	 * @return This invoker instance.
	 */
	Invoker setCalibreHome(File calibreHome);

	/**
	 * Sets the logger used by this invoker to output diagnostic messages.
	 * 
	 * @param logger
	 *            The logger used by this invoker to output diagnostic messages, may
	 *            be <code>null</code> to use a default logger.
	 * @return This invoker instance.
	 */
	Invoker setLogger(InvokerLogger logger);

	/**
	 * Sets the working directory for the Calibre invocation.
	 * 
	 * @param workingDirectory
	 *            The working directory for the Calibre invocation, may be
	 *            <code>null</code> to derive the working directory from the base
	 *            directory of the processed POM.
	 * @return This invoker instance.
	 */
	Invoker setWorkingDirectory(File workingDirectory);
	

    /**
     * Gets the path to the base directory of the Ebook repository to use for the Calibre invocation.
     * 
     * @return The path to the base directory of the Ebook repository or <code>null</code> to use the location from
     *         the <code></code>.
     */
    File getEbookRepositoryDirectory();

    /**
     * Sets the path to the base directory of the local repository to use for the Calibre invocation.
     * 
     * @param ebookRepositoryDirectory The path to the base directory of the local repository or <code>null</code> to
     *            use the location from the <code>settings.xml</code>.
     * @return This invoker instance.
     */
    Invoker setEbookRepositoryDirectory( File ebookRepositoryDirectory );

	/**
	 * Sets the handler used to capture the standard output from the Calibre build.
	 * 
	 * @param outputHandler
	 *            The output handler, may be <code>null</code> if the output is not
	 *            of interest.
	 * @return This invoker instance.
	 */
	Invoker setOutputHandler(InvocationOutputHandler outputHandler);

	/**
	 * Sets the handler used to capture the error output from the Calibre build.
	 * 
	 * @param errorHandler
	 *            The error handler, may be <code>null</code> if the output is not
	 *            of interest.
	 * @return This invoker instance.
	 */
	Invoker setErrorHandler(InvocationOutputHandler errorHandler);
}
