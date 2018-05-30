package org.cackharot.jenkins.dsl.custom

import groovy.io.FileType
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import org.yaml.snakeyaml.Yaml

@Builder(builderStrategy = SimpleStrategy, prefix = '')
class JobDslParser {
  List<JobImpl> model = []
  Set<String> teams = []

  def load(final String path) {
    def yaml = new Yaml()

    println("Searching custom job yamls in $path")
    new File(path).traverse(type: FileType.FILES, nameFilter: ~/.*\.(yaml|yml)$/) { file ->
      def content = (Map) yaml.load(file.newInputStream())
      model.addAll(content.jobs?.collect { job ->
        teams << content.name
        job.team = content.name
        JobTypeFactory.getImpl(job)
      })
    }
    this
  }

  List<Job> build(final DslFactory dslFactory) {
    createFolders(dslFactory, teams)
    createJobs(dslFactory, model)
  }

  static List<Job> createJobs(final DslFactory dslFactory, final List<JobImpl> jobImpls) {
    jobImpls.collectMany { jobImpl ->
      def jobs = jobImpl.build(dslFactory)
      addExtraSteps(jobs, jobImpl)
      jobs
    }
  }

  private static List<Job> addExtraSteps(List<Job> jobs, jobImpl) {
    jobs.each { job ->
      job.with {
        steps {
          shell("echo Last step for ${jobImpl.getName()}")
        }
      }
    }
  }

  static void createFolders(final DslFactory dslFactory, final Set<String> teams) {
    teams.each { team ->
      dslFactory.folder(team)
          .description("Contains $team jobs")
    }
  }
}
