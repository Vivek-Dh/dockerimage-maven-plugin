package vivek.plugin.dockerimage_maven_plugin;

import java.io.FileWriter;
import java.io.IOException;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
 
/**
 * Says "Hi" to the user.
 *
 */
@Mojo( name = "sayhi")
public class MyMojo extends AbstractMojo
{
    public void execute() throws MojoExecutionException
    {
    	try {
    	String dockerfile = "FROM maven:3.6-jdk-8-alpine AS maven" + "\n" +
    			"RUN mkdir code" +"\n" + 
    			"COPY . code/" + "\n" +
    			"WORKDIR code" + "\n" +
    			"RUN mvn clean compile" + "\n" +
    			"RUN mvn -DoutputDirectory=target/dependencies dependency:copy-dependencies" + "\n" + "\n" +

    			"FROM gcr.io/distroless/java" + "\n" +
    			"COPY --from=maven /code/target/dependencies /target/dependencies" + "\n" +
    			"COPY --from=maven /code/target/classes /target/classes" + "\n" +
    			"ENTRYPOINT [\"java\",\"-cp\",\"target/dependencies/*:target/classes\",\"com.vivek.Application\"]";


        
			FileWriter fw = new FileWriter("Dockerfile");
			fw.write(dockerfile);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
