/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package org.jboss.tools.ssp.api.schema;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import cz.habarta.typescript.generator.Input;
import cz.habarta.typescript.generator.JsonLibrary;
import cz.habarta.typescript.generator.Output;
import cz.habarta.typescript.generator.Settings;
import cz.habarta.typescript.generator.TypeScriptGenerator;
import cz.habarta.typescript.generator.TypeScriptOutputKind;

public class TypescriptUtility {
	public static void cleanFolder() {
		Path folder = getDaoTypescriptFolder();
		File[] ts = folder.toFile().listFiles();
		for( int i = 0; i < ts.length; i++ ) {
			ts[i].delete();
		}
	}
	
	public static void writeTypescriptSchemas(Class[] daoClasses) throws Exception {
		File daoFolder = getDaoTypescriptFolder().toFile();
		if (!daoFolder.exists()) {
			daoFolder.mkdirs();
		}

		for (int i = 0; i < daoClasses.length; i++) {
			Class c = daoClasses[i];
			Path p = getDaoTypescriptFile(c.getSimpleName());
			File output = p.toFile();
			List<String> classes = Arrays.asList(new String[] { c.getName() });
			final Settings settings = new Settings();
			settings.outputKind = TypeScriptOutputKind.module;
			settings.jsonLibrary = JsonLibrary.jackson2;

			new TypeScriptGenerator(settings).generateTypeScript(Input.fromClassNamesAndJaxrsApplication(classes, null,
					null, false, null, (URLClassLoader) c.getClassLoader(), true), Output.to(output));
			
			// It loads the files with stupid autogenerated garbage
			String contents = safeReadFile(p);
			String trimmed = trimFirstLines(contents);
			Files.write(p, trimmed.getBytes());
		}
		
		StringBuffer sb = new StringBuffer();
		File[] generated = daoFolder.listFiles();
		for( int i = 0; i < generated.length; i++ ) {
			String contents = safeReadFile(generated[i].toPath());
			sb.append(contents);
			sb.append("\n\n");
		}
		
		Files.write(getDaoTypescriptFolder().resolve("protocol.unified.d.ts"), sb.toString().getBytes());
	}

	private static String trimFirstLines(String contents) {
		int beginning = -1;
		for( int i = 0; i < 3; i++ ) {
			beginning = contents.indexOf("\n", beginning+1);
			if( beginning == -1 ) {
				return "";
			}
		}
		return contents.substring(beginning).trim();
	}

	public static Path getDaoTypescriptFile(String simpleClassName) {
		Path folder = getDaoTypescriptFolder();
		Path out = folder.resolve(simpleClassName + ".d.ts");
		return out;
	}

	public static Path getDaoTypescriptFolder() {
		return new File(".").toPath().resolve("src").resolve("main").resolve("resources").resolve("schema")
				.resolve("typescript");
	}
	
	private static String safeReadFile(Path p) {
		if( p.toFile().exists()) {
			try {
				String content = new String(Files.readAllBytes(p));
				return content;
			} catch(IOException ioe) {
			}
		}
		return "";
	}

}
