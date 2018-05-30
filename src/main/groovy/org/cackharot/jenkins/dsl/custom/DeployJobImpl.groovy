package org.cackharot.jenkins.dsl.custom

import groovy.json.JsonOutput
import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import org.cackharot.jenkins.dsl.model.DeployJobModel
import org.cackharot.jenkins.dsl.model.JobType

import static org.cackharot.jenkins.dsl.util.JobUtils.addDefaults

class DeployJobImpl implements JobImpl {
  private DeployJobModel model
  private String team

  DeployJobImpl(Map jobModel) {
    this.team = jobModel.team
    this.model = new DeployJobModel(
        name: jobModel.name,
        type: JobType.DEPLOY,
        description: "Deploy job for ${jobModel.name}",
        triggers: jobModel.triggers ?: [],
        environments: jobModel.environments
    )
  }

  @Override
  String getName() {
    "${team}/${model.name}"
  }

  @Override
  List<Job> build(DslFactory factory) {
    model.environments.collect { env ->
      def teamEnv = "${team}/${env.name}"

      factory.folder(teamEnv)
          .description("Jobs for ${team} for '${env.name}' environemnt")

      def job = factory.job("${teamEnv}/${model.name}")
      addDefaults(job)
      job.with {
        steps {
          shell("echo Deploy job step of $model.name")
        }
      }
      job
    }
  }
}
