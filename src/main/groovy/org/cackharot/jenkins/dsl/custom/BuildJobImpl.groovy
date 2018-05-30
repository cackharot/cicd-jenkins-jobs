package org.cackharot.jenkins.dsl.custom

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import org.cackharot.jenkins.dsl.model.BuildJobModel
import org.cackharot.jenkins.dsl.model.JobType

import static org.cackharot.jenkins.dsl.util.JobUtils.*

class BuildJobImpl implements JobImpl {
  private BuildJobModel model

  BuildJobImpl(Map jobModel) {
    this.model = new BuildJobModel(
        name: "${jobModel.team}/${jobModel.name}",
        type: JobType.BUILD,
        description: "Build job for ${jobModel.name}",
        triggers: jobModel.triggers ?: [],
        repoName: jobModel.repoName,
        repoBaseUrl: jobModel.repoBaseUrl
    )
  }

  @Override
  String getName() {
    model.name
  }

  @Override
  List<Job> build(DslFactory factory) {
    def job = factory.job(model.name)
    addScmTrigger(job)
    addDefaults(job)
    job.with {
      steps {
        shell("echo First step of $model.name")
      }
      steps {
        environmentVariables {
          env("APP_VERSION", "1.0.0")
        }
        shell("echo App version = \${APP_VERSION}")
      }
    }
    addDownstreamJobTrigger(job, model.triggers)
    Arrays.asList(job)
  }
}
