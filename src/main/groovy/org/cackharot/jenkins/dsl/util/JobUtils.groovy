package org.cackharot.jenkins.dsl.util

import javaposse.jobdsl.dsl.Job

class JobUtils {
  static void addDefaults(Job job) {
    addDescription(job)
    addLogRotator(job)
    addTimestampsWrapper(job)
    addBuildTimeout(job)
  }

  static void addBuildTimeout(final Job job, Integer minutes = 10) {
    job.with {
      wrappers {
        timeout {
          absolute(minutes)
        }
      }
    }
  }

  static void addDescription(Job job) {
    job.with {
      description('<em style="color: red;">GENERATED JOB - MANUAL CHANGES WILL BE OVERWRITTEN</em>')
      wrappers {
        colorizeOutput()
      }
    }
  }

  static void addLogRotator(Job job, Integer num = 30) {
    job.with {
      logRotator {
        numToKeep(num)
      }
    }
  }

  static void addScmTrigger(Job job, String triggerExpression = 'H/1 * * * *') {
    job.with {
      triggers {
        scm(triggerExpression)
      }
    }
  }

  static void addDownstreamJobTrigger(Job job, List<String> projects) {
    if (projects?.isEmpty() || projects?.size() == 0) {
      return
    }

    job.with {
      steps {
        downstreamParameterized {
          trigger(projects) {
            parameters {
              predefinedProp("APP_VERSION", "\${APP_VERSION}")
            }
          }
        }
      }
    }
  }

  static void addTimestampsWrapper(Job job) {
    job.with {
      wrappers {
        timestamps()
      }
    }
  }
}
