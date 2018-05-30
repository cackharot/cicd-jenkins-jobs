package org.cackharot.jenkins.dsl.custom

class UnknownJobTypeException extends RuntimeException {
  UnknownJobTypeException(String jobName, String jobType) {
    super("${jobName} has set a unknown type ${jobType}")
  }
}
