package org.cackharot.jenkins.dsl

import javaposse.jobdsl.dsl.DslScriptLoader

import java.util.logging.Logger

class RestApiJobUploader {
  static void main(String[] args) {
    String pattern = System.getProperty('pattern')
    String baseUrl = System.getProperty('baseUrl')
    String username = System.getProperty('username')
    String password = System.getProperty('password') // password or token

    if (!pattern || !baseUrl) {
      println 'usage: -Dpattern=<pattern> -DbaseUrl=<baseUrl> [-Dusername=<username>] [-Dpassword=<password>]'
      System.exit 1
    }

    final Logger logger = Logger.getLogger(this.getClass().getName());

    RestApiJobManagement jm = new RestApiJobManagement(baseUrl)
    if (username && password) {
      jm.setCredentials username, password
    }

    DslScriptLoader scriptLoader = new DslScriptLoader(jm)

    new FileNameFinder().getFileNames('.', pattern).each {
      String fileName ->
        logger.println "\nprocessing file: $fileName"
        File file = new File(fileName)
        scriptLoader.runScript(file.text)
    }
  }
}